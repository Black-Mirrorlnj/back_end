package com.score.garrys.Player.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarMatchRequestDTO {

    @NotNull(message = "serverId é obrigatório")
    private Long serverId;

    @NotBlank(message = "map é obrigatório")
    private String map;

    @NotBlank(message = "mode é obrigatório")
    private String mode;

    @NotBlank(message = "startTimestamp é obrigatório")
    private String startTimestamp;
}