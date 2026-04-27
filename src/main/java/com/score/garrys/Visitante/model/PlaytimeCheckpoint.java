package com.score.garrys.Visitante.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "playtime_checkpoints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaytimeCheckpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "steam_id", nullable = false, length = 17)
    private String steamId;

    @Column(name = "match_id")
    private Long matchId;

    @Column(name = "tempo_acumulado", nullable = false)
    private Integer tempoAcumulado;

    @Column(nullable = false, length = 50)
    private String motivo;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}