package com.score.garrys.Visitante.controller;

import com.score.garrys.Visitante.dto.playtime.PlaytimeRequestDTO;
import com.score.garrys.Visitante.dto.playtime.PlaytimeResponseDTO;
import com.score.garrys.Visitante.service.PlaytimeService;
import com.score.garrys.Player.service.AutenticacaoAddonService;
import com.score.garrys.shared.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitantes/playtime")
@RequiredArgsConstructor
public class PlaytimeController {

    private final PlaytimeService playtimeService;
    private final AutenticacaoAddonService autenticacaoAddonService;

    @PostMapping
    public ApiResponse<PlaytimeResponseDTO> registrar(
            @Valid @RequestBody PlaytimeRequestDTO dto,
            @RequestHeader("Authorization") String authorization
    ) {
        autenticacaoAddonService.autenticar(authorization, "/visitantes/playtime");

        PlaytimeResponseDTO response = playtimeService.registrar(dto);

        return ApiResponse.<PlaytimeResponseDTO>builder()
                .success(true)
                .message("Playtime processado com sucesso")
                .data(response)
                .build();
    }
}