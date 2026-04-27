package com.score.garrys.Usuario.dto.estatistica;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstatisticaRequestDTO {
    private Long jogadorId;
    private Integer kills;
    private Integer deaths;
    private Integer dinheiro;
    private Integer nivel;
    private Integer experiencia;
    private Integer tempoJogado;
}