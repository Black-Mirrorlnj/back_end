package com.score.garrys.Visitante.controller;

import com.score.garrys.Visitante.model.Visitante;
import com.score.garrys.Visitante.service.VisitanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/visitantes")
@RequiredArgsConstructor
public class VisitanteController {

    private final VisitanteService service;

    @GetMapping
    public List<Visitante> listar() {
        return service.listar();
    }

    @GetMapping("/ranking")
    public List<Visitante> ranking() {
        return service.ranking();
    }

    @PostMapping("/entrada")
    public Map<String, String> registrarEntrada(@RequestParam String nome) {
        service.registrarEntrada(nome);
        return Map.of("status", "Entrada registrada com sucesso");
    }

    @PutMapping("/saida/{id}")
    public Map<String, String> registrarSaida(@PathVariable int id, @RequestParam int kills) {
        service.registrarSaida(id, kills);
        return Map.of("status", "Saída registrada com sucesso");
    }

    @DeleteMapping("/{id}")
    public Map<String, String> remover(@PathVariable int id) {
        service.remover(id);
        return Map.of("status", "Removido com sucesso");
    }
}