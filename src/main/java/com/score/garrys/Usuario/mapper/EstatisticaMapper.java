package com.score.garrys.Usuario.mapper;

import com.score.garrys.Usuario.dto.estatistica.EstatisticaRequestDTO;
import com.score.garrys.Usuario.dto.estatistica.EstatisticaResponseDTO;
import com.score.garrys.Player.model.Estatistica;

public class EstatisticaMapper {

    private EstatisticaMapper() {
    }

    public static Estatistica toEntity(EstatisticaRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return Estatistica.builder()
                .jogadorId(dto.getJogadorId())
                .kills(dto.getKills())
                .deaths(dto.getDeaths())
                .dinheiro(dto.getDinheiro())
                .nivel(dto.getNivel())
                .experiencia(dto.getExperiencia())
                .tempoJogado(dto.getTempoJogado())
                .build();
    }

    public static EstatisticaResponseDTO toResponseDTO(Estatistica entity) {
        if (entity == null) {
            return null;
        }

        return EstatisticaResponseDTO.builder()
                .id(entity.getId())
                .jogadorId(entity.getJogadorId())
                .kills(entity.getKills())
                .deaths(entity.getDeaths())
                .dinheiro(entity.getDinheiro())
                .nivel(entity.getNivel())
                .experiencia(entity.getExperiencia())
                .tempoJogado(entity.getTempoJogado())
                .build();
    }
}