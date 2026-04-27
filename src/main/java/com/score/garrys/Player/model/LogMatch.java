package com.score.garrys.Player.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs_match")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_id", nullable = false)
    private Long matchId;

    @Column(name = "server_id", nullable = false)
    private Long serverId;

    @Column(nullable = false, length = 255)
    private String mensagem;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}