package com.score.garrys.Player.controller;

import com.score.garrys.Player.dto.evento.EventoMorteRequestDTO;
import com.score.garrys.Player.dto.evento.EventoMorteResponseDTO;
import com.score.garrys.Player.model.EventoMorte;
import com.score.garrys.Player.service.EventoMorteService;
import com.score.garrys.Player.service.AutenticacaoAddonService;
import com.score.garrys.shared.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventos/morte")
@RequiredArgsConstructor
public class EventoMorteController {

    private final EventoMorteService eventoMorteService;
    private final AutenticacaoAddonService autenticacaoAddonService;

    @PostMapping
    public ApiResponse<EventoMorteResponseDTO> registrar(
            @Valid @RequestBody EventoMorteRequestDTO dto,
            @RequestHeader("Authorization") String authorization
    ) {
        autenticacaoAddonService.autenticar(authorization, "/eventos/morte");

        EventoMorte evento = eventoMorteService.registrar(dto);

        EventoMorteResponseDTO response = EventoMorteResponseDTO.builder()
                .id(evento.getId())
                .matchId(evento.getMatchId())
                .killerId(evento.getKillerId())
                .victimId(evento.getVictimId())
                .weapon(evento.getWeapon())
                .timestamp(evento.getTimestamp().toString())
                .build();

        return ApiResponse.<EventoMorteResponseDTO>builder()
                .success(true)
                .message("Evento de morte registrado com sucesso")
                .data(response)
                .build();
    }
}