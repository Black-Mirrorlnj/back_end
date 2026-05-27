package com.score.garrys.Player.repository;

import com.score.garrys.Player.model.Pontuacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PontuacaoRepository extends JpaRepository<Pontuacao, Long> {

    // Busca todas as pontuações de um jogador
    List<Pontuacao> findByJogadorId(Long jogadorId);

    // Busca todas as pontuações de uma partida
    List<Pontuacao> findByPartidaId(Long partidaId);

    // Busca pontuação específica de um jogador em uma partida
    // findByJogadorIdAndPartidaId → SELECT * FROM pontuacoes WHERE jogador_id = ? AND partida_id = ?
    Optional<Pontuacao> findByJogadorIdAndPartidaId(Long jogadorId, Long partidaId);
}
