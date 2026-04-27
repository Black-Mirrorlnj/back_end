package com.score.garrys.Player.controller;

import com.score.garrys.Player.dto.CriarMatchRequestDTO;
import com.score.garrys.Player.dto.CriarMatchResponseDTO;
import com.score.garrys.Player.model.Match;
import com.score.garrys.Player.service.MatchLifecycleService;
import com.score.garrys.shared.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchLifecycleController {

    private final MatchLifecycleService matchLifecycleService;

    @PostMapping
    public ApiResponse<CriarMatchResponseDTO> criar(@Valid @RequestBody CriarMatchRequestDTO dto) {
        Match match = matchLifecycleService.criarMatch(dto);

        CriarMatchResponseDTO response = CriarMatchResponseDTO.builder()
                .matchId(match.getId())
                .serverId(match.getServerId())
                .status(match.getStatus())
                .map(match.getMapa())
                .mode(match.getModo())
                .startTimestamp(match.getStartTimestamp().toString())
                .build();

        return ApiResponse.<CriarMatchResponseDTO>builder()
                .success(true)
                .message("Partida criada com sucesso")
                .data(response)
                .build();
    }
}