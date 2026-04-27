package com.score.garrys.Player.service;

import com.score.garrys.Player.exception.UnauthorizedException;
import com.score.garrys.Player.model.AddonRegistrado;
import com.score.garrys.Player.model.LogAutenticacaoAddon;
import com.score.garrys.Player.repository.AddonRegistradoRepository;
import com.score.garrys.Player.repository.LogAutenticacaoAddonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AutenticacaoAddonService {

    private final AddonRegistradoRepository addonRegistradoRepository;
    private final LogAutenticacaoAddonRepository logAutenticacaoAddonRepository;

    public AddonRegistrado autenticar(String authorizationHeader, String endpoint) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            registrarLog(null, endpoint, false, "Header Authorization ausente");
            throw new UnauthorizedException("Authorization é obrigatório");
        }

        String apiKey = extrairApiKey(authorizationHeader);

        AddonRegistrado addon = addonRegistradoRepository.findByApiKeyAndAtivoTrue(apiKey)
                .orElseThrow(() -> {
                    registrarLog(apiKey, endpoint, false, "Token inválido ou addon inativo");
                    return new RuntimeException("Unauthorized");
                });

        registrarLog(apiKey, endpoint, true, "Autenticado com sucesso");
        return addon;
    }

    private String extrairApiKey(String authorizationHeader) {
        String prefix = "Bearer ";
        if (authorizationHeader.startsWith(prefix)) {
            return authorizationHeader.substring(prefix.length()).trim();
        }
        return authorizationHeader.trim();
    }

    private void registrarLog(String apiKey, String endpoint, boolean autorizado, String mensagem) {
        LogAutenticacaoAddon log = LogAutenticacaoAddon.builder()
                .apiKeyInformada(apiKey)
                .endpoint(endpoint)
                .autorizado(autorizado)
                .mensagem(mensagem)
                .criadoEm(LocalDateTime.now())
                .build();

        logAutenticacaoAddonRepository.save(log);
    }
}