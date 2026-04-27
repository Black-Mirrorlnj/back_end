package com.score.garrys.Visitante.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs_playtime")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogPlaytime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "steam_id", nullable = false, length = 17)
    private String steamId;

    @Column(name = "match_id")
    private Long matchId;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(length = 255)
    private String mensagem;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}