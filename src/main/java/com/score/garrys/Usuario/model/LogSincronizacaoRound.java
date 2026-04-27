package com.score.garrys.Usuario.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs_sincronizacao_round")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogSincronizacaoRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_id", nullable = false)
    private Long matchId;

    @Column(name = "steam_id", length = 17)
    private String steamId;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(length = 255)
    private String mensagem;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}