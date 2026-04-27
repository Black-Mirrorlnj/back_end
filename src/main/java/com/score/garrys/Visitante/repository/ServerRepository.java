package com.score.garrys.Visitante.repository;

import com.score.garrys.Visitante.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
    boolean existsByIpAndPort(String ip, Integer port);
}