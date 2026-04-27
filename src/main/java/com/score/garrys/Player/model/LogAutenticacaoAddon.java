package com.score.garrys.Player.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs_autenticacao_addon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogAutenticacaoAddon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_key_informada", length = 255)
    private String apiKeyInformada;

    @Column(length = 255)
    private String endpoint;

    @Column(nullable = false)
    private Boolean autorizado;

    @Column(length = 255)
    private String mensagem;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}