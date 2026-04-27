package com.score.garrys.Player.dto.evento;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventoMorteResponseDTO {
    private Long id;
    private Long matchId;
    private String killerId;
    private String victimId;
    private String weapon;
    private String timestamp;
}