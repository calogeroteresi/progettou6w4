package it.epicode.progettoSettimanale7.Models.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDTO {
    @NotBlank(message = "Titolo obbligatorio")
    private String titolo;
    @NotBlank(message = "Descrizione obbligatoria")
    private String descrizione;
    @NotNull(message = "Data obbligatoria")
    private LocalDate data;
    @NotBlank(message = "Luogo obbligatorio")
    private String luogo;
    @NotNull(message = "Numero posti disponibili obbligatorio")
    @Min(value = 1, message = "Il numero di posti disponibili deve essere almeno 1")
    private Integer numeroPostiDisponibili;
}
