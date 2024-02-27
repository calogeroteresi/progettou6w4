package it.epicode.progettoSettimanale7.Models.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomResponse {
    private String message;
    private LocalDateTime dataRisposta;
    private Object response;

    public CustomResponse(String message, Object response){
        this.message = message;
        dataRisposta = LocalDateTime.now();
        this.response = response;
    }
}
