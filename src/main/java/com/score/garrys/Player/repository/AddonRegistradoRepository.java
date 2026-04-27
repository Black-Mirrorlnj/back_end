package com.score.garrys.Player.repository;

import com.score.garrys.Player.model.AddonRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddonRegistradoRepository extends JpaRepository<AddonRegistrado, Long> {
    Optional<AddonRegistrado> findByApiKeyAndAtivoTrue(String apiKey);
}