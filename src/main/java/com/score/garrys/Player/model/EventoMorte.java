package com.score.garrys.Player.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "eventos_morte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoMorte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_id", nullable = false)
    private Long matchId;

    @Column(name = "killer_id", nullable = false, length = 17)
    private String killerId;

    @Column(name = "victim_id", nullable = false, length = 17)
    private String victimId;

    @Column(nullable = false, length = 100)
    private String weapon;

    @Column(nullable = false)
    private OffsetDateTime timestamp;
}