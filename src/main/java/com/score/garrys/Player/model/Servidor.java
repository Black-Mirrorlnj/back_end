package com.score.garrys.Player.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "servidores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "server_key", nullable = false, unique = true, length = 255)
    private String serverKey;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
}