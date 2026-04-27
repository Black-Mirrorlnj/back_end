package com.score.garrys.Visitante.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "servers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Server{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "server_name", nullable = false, length = 100)
    private String serverName;

    @Column(nullable = false, length = 45)
    private String ip;

    @Column(nullable = false)
    private Integer port;

    @Column(name = "rcon_key", length = 255)
    private String rconKey;

    @Column(name = "steam_id", nullable = false, length = 50)
    private String steamId;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}