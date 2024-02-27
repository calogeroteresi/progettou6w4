package it.epicode.progettoSettimanale7.Models.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
    @NotNull(message = "Username obbligatorio")
    private String username;
    @NotNull(message = "Password obbligatoria")
    private String password;
}
