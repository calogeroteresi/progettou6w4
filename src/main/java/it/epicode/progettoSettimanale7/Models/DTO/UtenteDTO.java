package it.epicode.progettoSettimanale7.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UtenteDTO {
    @NotBlank(message = "Nome obbligatorio")
    private String nome;
    @NotBlank(message = "Cognome obbligatorio")
    private String cognome;
    @NotBlank(message = "Username obbligatorio")
    private String username;
    @NotBlank(message = "Password obbligatoria")
    private String password;
}
