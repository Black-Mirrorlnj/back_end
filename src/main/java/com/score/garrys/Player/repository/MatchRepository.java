package com.score.garrys.Player.repository;

import com.score.garrys.Player.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}