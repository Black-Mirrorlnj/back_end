package com.score.garrys.Usuario.controller;

import com.score.garrys.Usuario.dto.UsuarioLoginRequestDTO;
import com.score.garrys.Usuario.dto.UsuarioRequestDTO;
import com.score.garrys.Usuario.dto.UsuarioResponseDTO;
import com.score.garrys.Usuario.mapper.UsuarioMapper;
import com.score.garrys.Usuario.model.Usuario;
import com.score.garrys.Usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public UsuarioResponseDTO criar(@RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = usuarioService.criar(dto);
        return UsuarioMapper.toResponseDTO(usuario);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return usuarioService.listar()
                .stream()
                .map(UsuarioMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return UsuarioMapper.toResponseDTO(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = usuarioService.atualizar(id, dto);
        return UsuarioMapper.toResponseDTO(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }

    @PostMapping("/login")
    public UsuarioResponseDTO login(@RequestBody UsuarioLoginRequestDTO dto) {
        Usuario usuario = usuarioService.login(dto);
        return UsuarioMapper.toResponseDTO(usuario);
    }
}