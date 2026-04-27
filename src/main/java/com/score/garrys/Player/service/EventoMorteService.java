package com.score.garrys.Player.service;

import com.score.garrys.Player.dto.evento.EventoMorteRequestDTO;
import com.score.garrys.Player.model.Estatistica;
import com.score.garrys.Player.model.EventoMorte;
import com.score.garrys.Player.model.Jogador;
import com.score.garrys.Player.repository.EstatisticaRepository;
import com.score.garrys.Player.repository.EventoMorteRepository;
import com.score.garrys.Player.repository.JogadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class EventoMorteService {

    private final EventoMorteRepository eventoMorteRepository;
    private final JogadorRepository jogadorRepository;
    private final EstatisticaRepository estatisticaRepository;

    public EventoMorte registrar(EventoMorteRequestDTO dto) {
        validarSteamId64(dto.getKillerId(), "killerId");
        validarSteamId64(dto.getVictimId(), "victimId");

        if (dto.getKillerId().equals(dto.getVictimId())) {
            throw new RuntimeException("killerId e victimId não podem ser iguais");
        }

        Jogador killer = jogadorRepository.findBySteamId(dto.getKillerId())
                .orElseThrow(() -> new RuntimeException("Killer não encontrado"));

        Jogador victim = jogadorRepository.findBySteamId(dto.getVictimId())
                .orElseThrow(() -> new RuntimeException("Victim não encontrado"));

        Estatistica killerStats = estatisticaRepository.findByJogadorId(killer.getId())
                .orElseThrow(() -> new RuntimeException("Estatísticas do killer não encontradas"));

        Estatistica victimStats = estatisticaRepository.findByJogadorId(victim.getId())
                .orElseThrow(() -> new RuntimeException("Estatísticas da vítima não encontradas"));

        EventoMorte evento = EventoMorte.builder()
                .matchId(dto.getMatchId())
                .killerId(dto.getKillerId())
                .victimId(dto.getVictimId())
                .weapon(dto.getWeapon())
                .timestamp(OffsetDateTime.parse(dto.getTimestamp()))
                .build();

        EventoMorte salvo = eventoMorteRepository.save(evento);

        killerStats.setKills(killerStats.getKills() + 1);
        victimStats.setDeaths(victimStats.getDeaths() + 1);

        estatisticaRepository.save(killerStats);
        estatisticaRepository.save(victimStats);

        return salvo;
    }

    private void validarSteamId64(String steamId, String campo) {
        if (steamId == null || !steamId.matches("\\d{17}")) {
            throw new RuntimeException(campo + " deve estar no formato SteamID64");
        }
    }
}