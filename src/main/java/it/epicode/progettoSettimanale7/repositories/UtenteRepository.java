package it.epicode.progettoSettimanale7.repositories;

import it.epicode.progettoSettimanale7.Models.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer>, PagingAndSortingRepository<Utente, Integer> {
    public Optional<Utente> getByUsername(String username);
}
