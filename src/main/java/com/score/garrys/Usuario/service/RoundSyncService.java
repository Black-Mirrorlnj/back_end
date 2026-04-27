package com.score.garrys.Usuario.service;

import com.score.garrys.Player.model.Estatistica;
import com.score.garrys.Player.model.Jogador;
import com.score.garrys.Player.repository.EstatisticaRepository;
import com.score.garrys.Player.repository.JogadorRepository;
import com.score.garrys.Usuario.dto.sync.RoundSyncItemResponseDTO;
import com.score.garrys.Usuario.dto.sync.RoundSyncPlayerDTO;
import com.score.garrys.Usuario.dto.sync.RoundSyncRequestDTO;
import com.score.garrys.Usuario.dto.sync.RoundSyncResponseDTO;
import com.score.garrys.Usuario.model.LogSincronizacaoRound;
import com.score.garrys.Usuario.repository.LogSincronizacaoRoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundSyncService {

    private final JogadorRepository jogadorRepository;
    private final EstatisticaRepository estatisticaRepository;
    private final LogSincronizacaoRoundRepository logSincronizacaoRoundRepository;

    public RoundSyncResponseDTO sincronizar(RoundSyncRequestDTO dto) {
        List<RoundSyncItemResponseDTO> resultados = new ArrayList<>();
        int sincronizados = 0;
        int naoEncontrados = 0;

        for (RoundSyncPlayerDTO item : dto.getJogadores()) {
            validarSteamId64(item.getSteamId());

            Jogador jogador = jogadorRepository.findBySteamId(item.getSteamId()).orElse(null);

            if (jogador == null) {
                naoEncontrados++;

                resultados.add(RoundSyncItemResponseDTO.builder()
                        .steamId(item.getSteamId())
                        .status("NOT_FOUND")
                        .mensagem("Nenhum dado encontrado")
                        .build());

                registrarLog(dto.getMatchId(), item.getSteamId(), "NOT_FOUND", "Nenhum dado encontrado");
                continue;
            }

            Estatistica estatistica = estatisticaRepository.findByJogadorId(jogador.getId())
                    .orElseThrow(() -> new RuntimeException("Estatísticas não encontradas para o jogador " + item.getSteamId()));

            estatistica.setKills(item.getKills());
            estatistica.setDeaths(item.getDeaths());
            estatistica.setTempoJogado(item.getTempoJogado());
            estatisticaRepository.save(estatistica);

            sincronizados++;

            resultados.add(RoundSyncItemResponseDTO.builder()
                    .steamId(item.getSteamId())
                    .status("SYNCED")
                    .mensagem("Sincronizado com sucesso")
                    .build());

            registrarLog(dto.getMatchId(), item.getSteamId(), "SYNCED", "Sincronizado com sucesso");
        }

        return RoundSyncResponseDTO.builder()
                .matchId(dto.getMatchId())
                .totalProcessados(dto.getJogadores().size())
                .totalSincronizados(sincronizados)
                .totalNaoEncontrados(naoEncontrados)
                .resultados(resultados)
                .build();
    }

    private void validarSteamId64(String steamId) {
        if (steamId == null || !steamId.matches("\\d{17}")) {
            throw new RuntimeException("steamId deve estar no formato SteamID64");
        }
    }

    private void registrarLog(Long matchId, String steamId, String status, String mensagem) {
        LogSincronizacaoRound log = LogSincronizacaoRound.builder()
                .matchId(matchId)
                .steamId(steamId)
                .status(status)
                .mensagem(mensagem)
                .criadoEm(LocalDateTime.now())
                .build();

        logSincronizacaoRoundRepository.save(log);
    }
}