package com.score.garrys.Usuario.dto.sync;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoundSyncRequestDTO {

    @NotNull(message = "matchId é obrigatório")
    private Long matchId;

    @Valid
    @NotEmpty(message = "A lista de jogadores não pode estar vazia")
    private List<RoundSyncPlayerDTO> jogadores;
}