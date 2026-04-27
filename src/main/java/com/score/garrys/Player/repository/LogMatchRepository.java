package com.score.garrys.Player.repository;

import com.score.garrys.Player.model.LogMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogMatchRepository extends JpaRepository<LogMatch, Long> {
}