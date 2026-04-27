package com.score.garrys.Player.repository;

import com.score.garrys.Player.model.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServidorRepository extends JpaRepository<Servidor, Long> {
    Optional<Servidor> findByIdAndAtivoTrue(Long id);
}