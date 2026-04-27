package com.score.garrys.Visitante.service;

import com.score.garrys.Player.model.Estatistica;
import com.score.garrys.Player.model.Jogador;
import com.score.garrys.Player.repository.EstatisticaRepository;
import com.score.garrys.Player.repository.JogadorRepository;
import com.score.garrys.Visitante.dto.playtime.PlaytimeRequestDTO;
import com.score.garrys.Visitante.dto.playtime.PlaytimeResponseDTO;
import com.score.garrys.Visitante.model.LogPlaytime;
import com.score.garrys.Visitante.model.PlaytimeCheckpoint;
import com.score.garrys.Visitante.repository.LogPlaytimeRepository;
import com.score.garrys.Visitante.repository.PlaytimeCheckpointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PlaytimeService {

    private final JogadorRepository jogadorRepository;
    private final EstatisticaRepository estatisticaRepository;
    private final PlaytimeCheckpointRepository playtimeCheckpointRepository;
    private final LogPlaytimeRepository logPlaytimeRepository;

    public PlaytimeResponseDTO registrar(PlaytimeRequestDTO dto) {
        validarSteamId64(dto.getSteamId());

        if (dto.getPlayTimeDelta() < 0) {
            registrarLog(dto.getSteamId(), dto.getMatchId(), "ERROR", "playTimeDelta negativo");
            throw new RuntimeException("playTimeDelta inválido");
        }

        if (dto.getPlayTimeDelta() > 7200) {
            registrarLog(dto.getSteamId(), dto.getMatchId(), "ALERT", "Delta inconsistente detectado");
        }

        Jogador jogador = jogadorRepository.findBySteamId(dto.getSteamId())
                .orElseThrow(() -> new RuntimeException("Nenhum dado encontrado"));

        Estatistica estatistica = estatisticaRepository.findByJogadorId(jogador.getId())
                .orElseThrow(() -> new RuntimeException("Estatísticas não encontradas"));

        int novoTempo = (estatistica.getTempoJogado() == null ? 0 : estatistica.getTempoJogado()) + dto.getPlayTimeDelta();
        estatistica.setTempoJogado(novoTempo);
        estatisticaRepository.save(estatistica);

        if ("disconnect".equalsIgnoreCase(dto.getMotivo())) {
            playtimeCheckpointRepository.save(
                    PlaytimeCheckpoint.builder()
                            .steamId(dto.getSteamId())
                            .matchId(dto.getMatchId())
                            .tempoAcumulado(novoTempo)
                            .motivo("disconnect")
                            .criadoEm(LocalDateTime.now())
                            .build()
            );
        }

        registrarLog(dto.getSteamId(), dto.getMatchId(), "SUCCESS", "Tempo de jogo atualizado");

        return PlaytimeResponseDTO.builder()
                .steamId(dto.getSteamId())
                .matchId(dto.getMatchId())
                .tempoAcumulado(novoTempo)
                .status("UPDATED")
                .mensagem("Tempo de jogo atualizado com sucesso")
                .build();
    }

    private void validarSteamId64(String steamId) {
        if (steamId == null || !steamId.matches("\\d{17}")) {
            throw new RuntimeException("steamId deve estar no formato SteamID64");
        }
    }

    private void registrarLog(String steamId, Long matchId, String status, String mensagem) {
        logPlaytimeRepository.save(
                LogPlaytime.builder()
                        .steamId(steamId)
                        .matchId(matchId)
                        .status(status)
                        .mensagem(mensagem)
                        .criadoEm(LocalDateTime.now())
                        .build()
        );
    }
}