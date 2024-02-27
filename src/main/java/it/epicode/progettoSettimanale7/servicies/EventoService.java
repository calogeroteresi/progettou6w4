package it.epicode.progettoSettimanale7.servicies;

import it.epicode.progettoSettimanale7.Models.DTO.EventoDTO;
import it.epicode.progettoSettimanale7.Models.entities.Evento;
import it.epicode.progettoSettimanale7.Models.entities.Prenotazione;
import it.epicode.progettoSettimanale7.Models.entities.Utente;
import it.epicode.progettoSettimanale7.exceptions.ConflictException;
import it.epicode.progettoSettimanale7.exceptions.NotFoundException;
import it.epicode.progettoSettimanale7.repositories.EventoRepository;
import it.epicode.progettoSettimanale7.repositories.PrenotazioneRepository;
import it.epicode.progettoSettimanale7.security.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private AuthService authService;


    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private JwtTools jwtTools;

    public Page<Evento> getAll(Pageable pageable){
        return eventoRepository.findAll(pageable);
    }

    public Evento getById(int id){
        return eventoRepository.findById(id).orElseThrow(() -> new NotFoundException("Evento con id " + id + " non trovato"));
    }

    public Evento save(EventoDTO eventoDTO){
        Evento evento = new Evento();
        evento.setTitolo(eventoDTO.getTitolo());
        evento.setDescrizione(eventoDTO.getDescrizione());
        evento.setData(eventoDTO.getData());
        evento.setLuogo(eventoDTO.getLuogo());
        evento.setPostiTotali(eventoDTO.getNumeroPostiDisponibili());
        return eventoRepository.save(evento);
    }

    public Evento update(int id, EventoDTO eventoDTO){
        Evento evento = getById(id);
        if (evento.getPrenotazioni().size() > eventoDTO.getNumeroPostiDisponibili()) throw new ConflictException("Il numero di posti disponibili non può essere minore del numero di posti già prenotati dagli utenti");
        evento.setPostiTotali(eventoDTO.getNumeroPostiDisponibili());
        evento.setTitolo(eventoDTO.getTitolo());
        evento.setDescrizione(eventoDTO.getDescrizione());
        evento.setData(eventoDTO.getData());
        evento.setLuogo(eventoDTO.getLuogo());
        return eventoRepository.save(evento);
    }

    public void delete(int id){
        Evento evento = getById(id);
        eventoRepository.delete(evento);
    }


    public Prenotazione prenota(int id, String username) {
        Evento evento = getById(id);
        if (evento.getPrenotazioni().size() == evento.getPostiTotali()) {
            throw new ConflictException("Il numero massimo di posti è già stato prenotato per l'evento con ID: " + id);
        }
        Utente utente = authService.getByUsername(username);
        if (prenotazioneRepository.existsPrenotazioneByIdEventoAndIdUtente(id, utente.getId())) {
            throw new ConflictException("Hai già prenotato questo evento");
        }
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setEvento(evento);
        prenotazione.setUtente(utente);
        prenotazione.setDataPrenotazione(LocalDate.now());
        return prenotazioneRepository.save(prenotazione);
    }


    public void annullaPrenotazione(int idEvento, String username) {
        Utente utente = authService.getByUsername(username);
        Prenotazione prenotazione = prenotazioneRepository.findByEventoIdAndUtenteId(idEvento, utente.getId());
        if (prenotazione == null) {
            throw new ConflictException("Nessuna prenotazione di questo utente per l'evento con ID: " + idEvento);
        }
        prenotazioneRepository.delete(prenotazione);
    }

    public List<Prenotazione> getPrenotazioniByIdEvento(int idEvento) {
        return prenotazioneRepository.findByEventoId(idEvento);
    }

}