package com.score.garrys.Usuario.mapper;

import com.score.garrys.Usuario.dto.UsuarioRequestDTO;
import com.score.garrys.Usuario.dto.UsuarioResponseDTO;
import com.score.garrys.Usuario.model.Usuario;

import java.time.LocalDateTime;

public class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .login(dto.getLogin())
                .senha(dto.getSenha())
                .perfil(dto.getPerfil())
                .ativo(dto.getAtivo() != null ? dto.getAtivo() : true)
                .criadoEm(LocalDateTime.now())
                .build();
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario entity) {
        if (entity == null) {
            return null;
        }

        return UsuarioResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .login(entity.getLogin())
                .perfil(entity.getPerfil())
                .ativo(entity.getAtivo())
                .criadoEm(entity.getCriadoEm())
                .build();
    }

    public static void updateEntity(Usuario entity, UsuarioRequestDTO dto) {
        if (entity == null || dto == null) {
            return;
        }

        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setLogin(dto.getLogin());
        entity.setSenha(dto.getSenha());
        entity.setPerfil(dto.getPerfil());
        entity.setAtivo(dto.getAtivo());
    }
}