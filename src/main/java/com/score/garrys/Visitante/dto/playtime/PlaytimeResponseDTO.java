package com.score.garrys.Visitante.dto.playtime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaytimeResponseDTO {
    private String steamId;
    private Long matchId;
    private Integer tempoAcumulado;
    private String status;
    private String mensagem;
}