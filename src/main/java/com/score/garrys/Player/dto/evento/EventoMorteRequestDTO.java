package com.score.garrys.Player.dto.evento;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventoMorteRequestDTO {

    @NotNull(message = "matchId é obrigatório")
    private Long matchId;

    @NotBlank(message = "killerId é obrigatório")
    private String killerId;

    @NotBlank(message = "victimId é obrigatório")
    private String victimId;

    @NotBlank(message = "weapon é obrigatório")
    private String weapon;

    @NotBlank(message = "timestamp é obrigatório")
    private String timestamp;
}