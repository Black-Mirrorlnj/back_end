package com.score.garrys.Visitante.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class VisitanteDTO {

    private int idVisitante;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Min(value = 0, message = "Kills não pode ser negativo")
    private int kills;

    @NotBlank(message = "Email é obrigatório")
    private String email;

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
        this.nome = nome;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
