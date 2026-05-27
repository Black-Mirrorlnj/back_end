package com.score.garrys.Player.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "jogadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "steam_id", nullable = false, unique = true, length = 50)
    private String steamId;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    //Relacionamento com Estatistica
    @OneToOne(mappedBy = "jogador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Estatistica estatistica;

    //Relacionamento com Ranking
    @OneToOne(mappedBy = "jogador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RankingGlobal rankingGlobal;

    //Relacionamento com Pontuacao
    @OneToMany(mappedBy = "jogador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pontuacao> pontuacoes;

    //Definir horario
    @PrePersist
    protected void onCreate() {
        this.criadoEm = LocalDateTime.now();
    }

    //Atualizar ultimo login
    @PreUpdate
    protected void onUpdate() {
        this.ultimoLogin = LocalDateTime.now();
    }
}