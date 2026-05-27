package com.score.garrys.Player.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estatisticas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estatistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(name = "jogador_id", nullable = false)
    private Long jogadorId;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer kills = 0;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer deaths = 0;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer dinheiro = 0;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer nivel = 1;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer experiencia = 0;

    @Builder.Default
    @Column(name = "tempo_jogado", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer tempoJogado = 0;

    @Transient
    public Double getKDRatio() {
        if (deaths == null || deaths == 0) return kills == 0 ? 0.0 : kills.doubleValue();
        return (double) kills / deaths;
    }

}