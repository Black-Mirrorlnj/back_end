package com.score.garrys.Player.repository;

import com.score.garrys.Player.model.Jogador;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    
    //Buscar jogador por steamId
    Optional<Jogador> findBySteamId(String steamId);

     //Verificar se jogador existe por steamId
     boolean existsBySteamId(String steamId);

     //Buscar jogador por usuarioId
     Optional<Jogador> findByUsuarioId(Long usuarioId);

     //Buscar jogadores por nome (contendo parte do nome)
     List<Jogador> findByNomeContainingIgnoreCase(String nome);

    
}