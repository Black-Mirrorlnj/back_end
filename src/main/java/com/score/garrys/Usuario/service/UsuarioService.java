package com.score.garrys.Usuario.service;

import com.score.garrys.Usuario.dto.UsuarioLoginRequestDTO;
import com.score.garrys.Usuario.dto.UsuarioRequestDTO;
import com.score.garrys.Usuario.mapper.UsuarioMapper;
import com.score.garrys.Usuario.model.Usuario;
import com.score.garrys.Usuario.repository.UsuarioRepository;
import com.score.garrys.Player.repository.JogadorRepository;
import com.score.garrys.Player.repository.EstatisticasRepository;
import com.score.garrys.Player.repository.RankingGlobalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JogadorRepository jogadorRepository;
    private final EstatisticasRepository estatisticasRepository;
    private final RankingGlobalRepository rankingGlobalRepository;

    public Usuario salvar(Usuario usuario) {
        if (usuarioRepository.existsByLogin(usuario.getLogin())) {
            throw new RuntimeException("Login já cadastrado");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }
        // Criptografa a senha antes de salvar no banco
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        // Salva o usuário e obtém o objeto salvo
        usuario = usuarioRepository.save(usuario);
        // Cria jogador vinculado ao usuário
        com.score.garrys.Player.model.Jogador novoJogador = com.score.garrys.Player.model.Jogador.builder()
                .usuarioId(usuario.getId())
                .nome(usuario.getNome())
                .steamId("PENDENTE_" + usuario.getId())
                .build();
        jogadorRepository.save(novoJogador);
        // Cria estatísticas zeradas para o jogador
        com.score.garrys.Player.model.Estatistica stats = com.score.garrys.Player.model.Estatistica.builder()
                .jogadorId(novoJogador.getId())
                .kills(0)
                .deaths(0)
                .dinheiro(0)
                .nivel(1)
                .experiencia(0)
                .tempoJogado(0)
                .build();
        estatisticasRepository.save(stats);
        // Cria entrada zerada no ranking global
        com.score.garrys.Player.model.RankingGlobal ranking = com.score.garrys.Player.model.RankingGlobal.builder()
                .jogadorId(novoJogador.getId())
                .pontos(0)
                .posicao(null)
                .build();
        rankingGlobalRepository.save(ranking);
        return usuario;
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
        com.score.garrys.Usuario.mapper.UsuarioMapper.updateEntity(usuario, dto);

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

    // Novo metodo: retorna response DTO de login já contendo jogadorId
    public com.score.garrys.Usuario.dto.UsuarioResponseDTO loginComJogador(UsuarioLoginRequestDTO dto) {
        Usuario usuario = login(dto);
        Long jogadorId = jogadorRepository.findByUsuarioId(usuario.getId())
                .map(com.score.garrys.Player.model.Jogador::getId)
                .orElse(null);
        com.score.garrys.Usuario.dto.UsuarioResponseDTO response = com.score.garrys.Usuario.mapper.UsuarioMapper.toResponseDTO(usuario);
        response.setJogadorId(jogadorId);
        return response;
    }

    // Novo metodo: ao criar usuario, retorna response DTO já com jogadorId
    public com.score.garrys.Usuario.dto.UsuarioResponseDTO criarComJogador(UsuarioRequestDTO dto) {
        Usuario usuario = criar(dto);
        Long jogadorId = jogadorRepository.findByUsuarioId(usuario.getId())
                .map(com.score.garrys.Player.model.Jogador::getId)
                .orElse(null);
        com.score.garrys.Usuario.dto.UsuarioResponseDTO response = com.score.garrys.Usuario.mapper.UsuarioMapper.toResponseDTO(usuario);
        response.setJogadorId(jogadorId);
        return response;
    }
}