package com.score.garrys.Usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String perfil;
    private Boolean ativo;
}