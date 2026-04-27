package com.score.garrys.Visitante.dto.playtime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaytimeRequestDTO {

    @NotBlank(message = "steamId é obrigatório")
    private String steamId;

    private Long matchId;

    @NotNull(message = "playTimeDelta é obrigatório")
    private Integer playTimeDelta;

    @NotBlank(message = "motivo é obrigatório")
    private String motivo;
}