package com.score.garrys.Visitante.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "visitantes")
public class Visitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVisitante;

    @Column(name = "nome_usuario")
    private String nome;

    @Column(name = "horario_entrada")
    private LocalDateTime horarioEntrada;

    @Column(name = "horario_saida")
    private LocalDateTime horarioSaida;
    private int kills;

    public int getIdVisitante() {
        return idVisitante;
    }

    public void setIdVisitante(int idVisitante) {
        this.idVisitante = idVisitante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public LocalDateTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(LocalDateTime horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(LocalDateTime horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        if (kills < 0) {
            throw new IllegalArgumentException("Kills não pode ser negativo");
        }
        this.kills = kills;
    }
}
