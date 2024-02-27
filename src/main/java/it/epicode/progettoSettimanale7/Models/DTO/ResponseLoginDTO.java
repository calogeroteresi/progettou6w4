package it.epicode.progettoSettimanale7.Models.DTO;

import it.epicode.progettoSettimanale7.Models.entities.Utente;
import lombok.Data;

@Data
public class ResponseLoginDTO{
    private String jwt;
    private Utente utente;

    public ResponseLoginDTO(String jwt, Utente utente){
        this.jwt = jwt;
        this.utente = utente;
    }
}
