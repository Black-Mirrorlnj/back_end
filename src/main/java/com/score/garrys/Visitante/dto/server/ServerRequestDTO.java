package com.score.garrys.Visitante.dto.server;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerRequestDTO {

    @NotBlank(message = "serverName é obrigatório")
    private String serverName;

    @NotBlank(message = "ip é obrigatório")
    private String ip;

    @NotNull(message = "port é obrigatório")
    private Integer port;

    private String rconKey;

    @NotBlank(message = "steamId é obrigatório")
    private String steamId;
}