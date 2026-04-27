package com.score.garrys.Player.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CriarMatchResponseDTO {
    private Long matchId;
    private Long serverId;
    private String status;
    private String map;
    private String mode;
    private String startTimestamp;
}