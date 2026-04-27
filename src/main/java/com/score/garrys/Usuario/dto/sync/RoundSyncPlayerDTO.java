package com.score.garrys.Usuario.dto.sync;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoundSyncPlayerDTO {

    @NotBlank(message = "steamId é obrigatório")
    private String steamId;

    @NotNull(message = "kills é obrigatório")
    private Integer kills;

    @NotNull(message = "deaths é obrigatório")
    private Integer deaths;

    @NotNull(message = "tempoJogado é obrigatório")
    private Integer tempoJogado;
}