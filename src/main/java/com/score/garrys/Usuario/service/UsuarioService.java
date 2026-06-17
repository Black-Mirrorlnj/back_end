package com.score.garrys.Usuario.service;

import com.score.garrys.Usuario.dto.UsuarioLoginRequestDTO;
import com.score.garrys.Usuario.dto.UsuarioRequestDTO;
import com.score.garrys.Usuario.mapper.UsuarioMapper;
import com.score.garrys.Usuario.model.Usuario;
import com.score.garrys.Usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // ← injetado do SecurityConfig

    public Usuario salvar(Usuario usuario) {
        if (usuarioRepository.existsByLogin(usuario.getLogin())) {
            throw new RuntimeException("Login já cadastrado");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }
        // Criptografa a senha antes de salvar no banco
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario criar(UsuarioRequestDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);
        return salvar(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = buscarPorId(id);
        if (!usuario.getLogin().equals(dto.getLogin())
                && usuarioRepository.existsByLogin(dto.getLogin())) {
            throw new RuntimeException("Login já cadastrado");
        }
        if (!usuario.getEmail().equals(dto.getEmail())
                && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }
        UsuarioMapper.updateEntity(usuario, dto);

        // Recriptografa a senha se foi alterada
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        usuarioRepository.delete(buscarPorId(id));
    }

    public Usuario login(UsuarioLoginRequestDTO dto) {
        // Busca por login OU email
        Usuario usuario = usuarioRepository.findByLogin(dto.getLogin())
                .or(() -> usuarioRepository.findByEmail(dto.getLogin()))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // BCrypt compara a senha digitada com o hash salvo no banco
        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        if (Boolean.FALSE.equals(usuario.getAtivo())) {
            throw new RuntimeException("Usuário inativo");
        }

        return usuario;
    }
}