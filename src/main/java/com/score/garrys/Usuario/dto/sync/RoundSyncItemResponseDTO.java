package com.score.garrys.Usuario.dto.sync;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoundSyncItemResponseDTO {
    private String steamId;
    private String status;
    private String mensagem;
}