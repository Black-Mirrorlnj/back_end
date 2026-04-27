package com.score.garrys.Visitante.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs_auditoria_servidor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogAuditoriaServidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "server_id", nullable = false)
    private Long serverId;

    @Column(name = "usuario_responsavel", nullable = false, length = 100)
    private String usuarioResponsavel;

    @Column(nullable = false, length = 255)
    private String mensagem;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}