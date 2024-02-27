package it.epicode.progettoSettimanale7.Models.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.progettoSettimanale7.exceptions.ErrorResponse;
import it.epicode.progettoSettimanale7.exceptions.SubscriptionException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "eventi")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String luogo;

    @Transient
    private int iscrizioni;

    @Column(nullable = false)
    private int postiTotali;

    @Column(nullable = false)
    private boolean esaurito = false;

    @OneToMany(mappedBy = "evento")
    @JsonIgnore
    private List<Prenotazione> prenotazioni;

    public Evento(String titolo, String descrizione, LocalDate data, int postiTotali, String luogo) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.postiTotali = postiTotali;
        this.luogo = luogo;
    }
}
