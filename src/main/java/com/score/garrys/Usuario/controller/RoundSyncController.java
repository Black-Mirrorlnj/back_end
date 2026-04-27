package com.score.garrys.Usuario.controller;

import com.score.garrys.Usuario.dto.sync.RoundSyncRequestDTO;
import com.score.garrys.Usuario.dto.sync.RoundSyncResponseDTO;
import com.score.garrys.Usuario.service.RoundSyncService;
import com.score.garrys.Player.service.AutenticacaoAddonService;
import com.score.garrys.shared.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios/rounds/sync")
@RequiredArgsConstructor
public class RoundSyncController {

    private final RoundSyncService roundSyncService;
    private final AutenticacaoAddonService autenticacaoAddonService;

    @PostMapping
    public ApiResponse<RoundSyncResponseDTO> sincronizar(
            @Valid @RequestBody RoundSyncRequestDTO dto,
            @RequestHeader("Authorization") String authorization
    ) {
        autenticacaoAddonService.autenticar(authorization, "/usuarios/rounds/sync");

        RoundSyncResponseDTO response = roundSyncService.sincronizar(dto);

        return ApiResponse.<RoundSyncResponseDTO>builder()
                .success(true)
                .message("Sincronização de round concluída")
                .data(response)
                .build();
    }
}