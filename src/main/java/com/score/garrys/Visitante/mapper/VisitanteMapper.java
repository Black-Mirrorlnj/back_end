package com.score.garrys.Visitante.mapper;

import com.score.garrys.Visitante.dto.VisitanteDTO;
import com.score.garrys.Visitante.model.Visitante;

import java.util.Collections;
import java.util.List;

public class VisitanteMapper {

    private VisitanteMapper() {}

    public static Visitante toEntity(VisitanteDTO dto) {
        if (dto == null) return null;

        Visitante v = new Visitante();
        v.setIdVisitante(dto.getIdVisitante());
        v.setNome(dto.getNome());
        v.setKills(dto.getKills());

        return v;
    }

    public static VisitanteDTO toDTO(Visitante entity) {
        if (entity == null) return null;

        VisitanteDTO dto = new VisitanteDTO();
        dto.setIdVisitante(entity.getIdVisitante());
        dto.setNome(entity.getNome());
        dto.setKills(entity.getKills());

        return dto;
    }

    public static List<VisitanteDTO> toDTOList(List<Visitante> lista) {
        if (lista == null || lista.isEmpty()) {
            return Collections.emptyList();
        }

        return lista.stream()
                .map(VisitanteMapper::toDTO)
                .toList();
    }
}
