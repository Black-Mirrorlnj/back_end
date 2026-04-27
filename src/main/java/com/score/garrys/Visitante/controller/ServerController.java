package com.score.garrys.Visitante.controller;

import com.score.garrys.Visitante.dto.server.ServerRequestDTO;
import com.score.garrys.Visitante.dto.server.ServerResponseDTO;
import com.score.garrys.Visitante.model.Server;
import com.score.garrys.Visitante.service.ServerService;
import com.score.garrys.shared.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitantes/servers")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ServerResponseDTO> cadastrar(
            @Valid @RequestBody ServerRequestDTO dto,
            @RequestHeader("X-Admin-User") String usuarioResponsavel
    ) {
        Server server = serverService.cadastrar(dto, usuarioResponsavel);

        ServerResponseDTO response = ServerResponseDTO.builder()
                .serverId(server.getId())
                .serverName(server.getServerName())
                .ip(server.getIp())
                .port(server.getPort())
                .steamId(server.getSteamId())
                .ativo(server.getAtivo())
                .build();

        return ApiResponse.<ServerResponseDTO>builder()
                .success(true)
                .message("Servidor cadastrado com sucesso")
                .data(response)
                .build();
    }
}