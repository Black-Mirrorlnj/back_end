package com.score.garrys.Usuario.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String login;
    private String perfil;
    private Boolean ativo;
    private LocalDateTime criadoEm;
}