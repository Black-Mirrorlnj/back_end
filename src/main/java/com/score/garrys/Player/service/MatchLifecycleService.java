package com.score.garrys.Player.service;

import com.score.garrys.Player.dto.CriarMatchRequestDTO;
import com.score.garrys.Player.model.LogMatch;
import com.score.garrys.Player.model.Match;
import com.score.garrys.Player.model.Servidor;
import com.score.garrys.Player.repository.LogMatchRepository;
import com.score.garrys.Player.repository.MatchRepository;
import com.score.garrys.Player.repository.ServidorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class MatchLifecycleService {

    private final ServidorRepository servidorRepository;
    private final MatchRepository matchRepository;
    private final LogMatchRepository logMatchRepository;

    public Match criarMatch(CriarMatchRequestDTO dto) {
        Servidor servidor = servidorRepository.findByIdAndAtivoTrue(dto.getServerId())
                .orElseThrow(() -> new RuntimeException("Servidor não encontrado ou inativo"));

        Match match = Match.builder()
                .serverId(servidor.getId())
                .mapa(dto.getMap())
                .modo(dto.getMode())
                .startTimestamp(OffsetDateTime.parse(dto.getStartTimestamp()).toLocalDateTime())
                .status("in_progress")
                .criadoEm(LocalDateTime.now())
                .build();

        Match salva = matchRepository.save(match);

        registrarLog(salva.getId(), servidor.getId(), "Partida criada com status in_progress");
        registrarLog(salva.getId(), servidor.getId(), "Vínculo entre servidor e partida registrado");

        return salva;
    }

    private void registrarLog(Long matchId, Long serverId, String mensagem) {
        LogMatch log = LogMatch.builder()
                .matchId(matchId)
                .serverId(serverId)
                .mensagem(mensagem)
                .criadoEm(LocalDateTime.now())
                .build();

        logMatchRepository.save(log);
    }
}