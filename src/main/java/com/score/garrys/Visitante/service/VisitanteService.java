package com.score.garrys.Visitante.service;

import com.score.garrys.Visitante.model.Visitante;
import com.score.garrys.Visitante.repository.VisitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitanteService {

    private final VisitanteRepository visitanteRepository;

    public List<Visitante> listar() {
        return visitanteRepository.listar();
    }

    public List<Visitante> ranking() {
        return visitanteRepository.ranking();
    }

    public void registrarEntrada(String nome) {
        visitanteRepository.registrarEntrada(nome);
    }

    public void registrarSaida(int id, int kills) {
        visitanteRepository.registrarSaida(id, kills);
    }

    public void remover(int id) {
        visitanteRepository.remover(id);
    }
}