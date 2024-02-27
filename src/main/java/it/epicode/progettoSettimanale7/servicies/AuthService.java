package it.epicode.progettoSettimanale7.servicies;

import it.epicode.progettoSettimanale7.Models.DTO.LoginDTO;
import it.epicode.progettoSettimanale7.Models.DTO.ResponseLoginDTO;
import it.epicode.progettoSettimanale7.Models.DTO.UtenteDTO;
import it.epicode.progettoSettimanale7.Models.Enum.RuoloUtente;
import it.epicode.progettoSettimanale7.Models.entities.Utente;
import it.epicode.progettoSettimanale7.exceptions.BadRequestException;
import it.epicode.progettoSettimanale7.exceptions.LoginFaultException;
import it.epicode.progettoSettimanale7.exceptions.NotFoundException;
import it.epicode.progettoSettimanale7.repositories.UtenteRepository;
import it.epicode.progettoSettimanale7.security.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTools jwtTools;

    public Page<Utente> getAll(Pageable pageable) {
        return utenteRepository.findAll(pageable);
    }

    public Utente getById(int id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente con id " + id + " non trovato"));
    }

    public Utente getByUsername(String username) {
        return utenteRepository.getByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente con username " + username + " non trovato"));
    }

    public Utente save(UtenteDTO utenteDTO) {
        Utente utente = new Utente();
        utente.setNome(utenteDTO.getNome());
        utente.setCognome(utenteDTO.getCognome());
        utente.setUsername(utenteDTO.getUsername());
        utente.setPasswordHash(encoder.encode(utenteDTO.getPassword()));
        utente.setRuolo(RuoloUtente.UTENTE_NORMALE);
        return utenteRepository.save(utente);
    }

    public Utente update(String username, UtenteDTO utenteDTO) {
        Utente utente = getByUsername(username);
        utente.setNome(utenteDTO.getNome());
        utente.setCognome(utenteDTO.getCognome());
        utente.setUsername(utenteDTO.getUsername());
        utente.setPasswordHash(encoder.encode(utenteDTO.getPassword()));
        return utenteRepository.save(utente);
    }

    public void delete(String username) {
        Utente utente = getByUsername(username);
        utenteRepository.delete(utente);
    }

    public ResponseLoginDTO login(LoginDTO loginDTO) {
        Utente utente = getByUsername(loginDTO.getUsername());
        if (!encoder.matches(loginDTO.getPassword(), utente.getPassword())) {
            throw new LoginFaultException("Username/password errati");
        }
        return new ResponseLoginDTO(jwtTools.createToken(utente), utente);
    }

    public Utente changeRole(String username, RuoloUtente role) {
        Utente utente = getByUsername(username);
        if (utente.getRuolo() == role) {
            throw new BadRequestException("Il ruolo selezionato è lo stesso ruolo che l'utente già ha");
        }
        utente.setRuolo(role);
        return utenteRepository.save(utente);
    }
}
