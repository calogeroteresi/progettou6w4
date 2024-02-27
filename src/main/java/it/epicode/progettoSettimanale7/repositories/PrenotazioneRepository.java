package it.epicode.progettoSettimanale7.repositories;

import it.epicode.progettoSettimanale7.Models.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer>, PagingAndSortingRepository<Prenotazione, Integer> {

    @Query("SELECT p FROM Prenotazione p WHERE p.evento.id = :idEvento AND p.utente.id = :idUtente")
    public Prenotazione findByEventoIdAndUtenteId(int idEvento, int idUtente);

    @Query("SELECT p FROM Prenotazione p WHERE p.evento.id = :id")
    public List<Prenotazione> findByEventoId(int id);

    @Query("SELECT COUNT(p) > 0 FROM Prenotazione p WHERE p.evento.id = :idEvento AND p.utente.id = :idUtente")
    public boolean existsPrenotazioneByIdEventoAndIdUtente(int idEvento, int idUtente);

}

