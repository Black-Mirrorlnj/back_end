package com.score.garrys.Player.repository;

import com.score.garrys.Player.model.Jogador;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    
    //Buscar jogador por steamId
    Optional<Jogador> findBySteamId(String steamId);

    //Verificar se jogador existe por steamId
    boolean existsBySteamId(String steamId);

    //Buscar jogadores por nome (contendo parte do nome)
    List<Jogador> findByNomeContainingIgnoreCase(String nome);

    //Buscar jogador com suas estatisticas ja carregadas
    @Query ("SELECT j FROM Jogador j LEFT JOIN FETCH j.estatistica WHERE j.id = :id")
    Optional<Jogador> findByIdWithEstatistica(Long id);

    //Buscar jogador com ranking global ja carregado
    @Query ("SELECT j FROM Jogador j LEFT JOIN FETCH j.rankingGlobal WHERE j.id = :id")
    Optional<Jogador> findByIdWithRankingGlobal(Long id);
}