package com.score.garrys.Player.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ranking_global")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingGlobal {

    @Id
    @Column(name = "jogador_id")
    private Long jogadorId;  // ← É a PK e também a FK, getJogadorId() gerado pelo Lombok

    @Builder.Default
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer pontos = 0;

    @Column
    private Integer posicao;
}