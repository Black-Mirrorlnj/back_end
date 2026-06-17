package com.score.garrys.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    @Value("${scoresync.api.key}")
    private String apiKey;

    // Rotas que NÃO precisam de API Key (app Android e cadastro)
    private static final String[] ROTAS_LIVRES = {
        "/usuarios", "/usuarios/login"
    };

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();

        // Libera rotas de usuário
        for (String rota : ROTAS_LIVRES) {
            if (path.startsWith(rota)) {
                chain.doFilter(req, res);
                return;
            }
        }

        // Verifica API Key nas demais rotas
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.equals("Bearer " + apiKey)) {
            chain.doFilter(req, res);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"erro\":\"API Key inválida\"}");
        }
    }
}