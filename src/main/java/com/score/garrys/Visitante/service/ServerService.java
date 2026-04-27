package com.score.garrys.Visitante.service;

import com.score.garrys.Visitante.dto.server.ServerRequestDTO;
import com.score.garrys.Visitante.model.LogAuditoriaServidor;
import com.score.garrys.Visitante.model.Server;
import com.score.garrys.Visitante.repository.LogAuditoriaServidorRepository;
import com.score.garrys.Visitante.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;
    private final LogAuditoriaServidorRepository logAuditoriaServidorRepository;

    public Server cadastrar(ServerRequestDTO dto, String usuarioResponsavel) {
        if (serverRepository.existsByIpAndPort(dto.getIp(), dto.getPort())) {
            throw new RuntimeException("IP/porta já cadastrados");
        }

        Server server = Server.builder()
                .serverName(dto.getServerName())
                .ip(dto.getIp())
                .port(dto.getPort())
                .rconKey(dto.getRconKey())
                .steamId(dto.getSteamId())
                .ativo(true)
                .criadoEm(LocalDateTime.now())
                .build();

        Server salvo = serverRepository.save(server);

        logAuditoriaServidorRepository.save(
                LogAuditoriaServidor.builder()
                        .serverId(salvo.getId())
                        .usuarioResponsavel(usuarioResponsavel)
                        .mensagem("Servidor cadastrado com sucesso")
                        .criadoEm(LocalDateTime.now())
                        .build()
        );

        return salvo;
    }
}