package com.score.garrys.Visitante.dto.server;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ServerResponseDTO {
    private Long serverId;
    private String serverName;
    private String ip;
    private Integer port;
    private String steamId;
    private Boolean ativo;
}