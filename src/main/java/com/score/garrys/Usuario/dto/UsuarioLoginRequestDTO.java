package com.score.garrys.Usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginRequestDTO {
    private String login;
    private String senha;
}