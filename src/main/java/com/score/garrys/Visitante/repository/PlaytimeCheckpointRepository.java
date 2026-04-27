package com.score.garrys.Visitante.repository;


import com.score.garrys.Visitante.model.PlaytimeCheckpoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaytimeCheckpointRepository extends JpaRepository<PlaytimeCheckpoint, Long> {
}