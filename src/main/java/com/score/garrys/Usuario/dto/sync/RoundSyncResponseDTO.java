package com.score.garrys.Usuario.dto.sync;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RoundSyncResponseDTO {
    private Long matchId;
    private Integer totalProcessados;
    private Integer totalSincronizados;
    private Integer totalNaoEncontrados;
    private List<RoundSyncItemResponseDTO> resultados;
}