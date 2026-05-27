package com.score.garrys.Player.repository;

import com.score.garrys.Player.model.RankingGlobal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RankingGlobalRepository extends JpaRepository<RankingGlobal, Long> {

    // Ranking ordenado por pontos decrescente
    // findAllByOrderByPontosDesc → SELECT * FROM ranking_global ORDER BY pontos DESC
    List<RankingGlobal> findAllByOrderByPontosDesc();

    // Busca ranking de um jogador específico
    Optional<RankingGlobal> findByJogadorId(Long jogadorId);
}