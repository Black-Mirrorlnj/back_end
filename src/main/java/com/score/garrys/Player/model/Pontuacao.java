package com.score.garrys.Player.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pontuacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pontuacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jogador_id")
    private Long jogadorId;  // ← Long simples, getJogadorId() gerado pelo Lombok

    @Column(name = "partida_id")
    private Long partidaId;  // ← Long simples, getPartidaId() gerado pelo Lombok

    @Builder.Default
    @Column(name = "score_inicial", columnDefinition = "INT DEFAULT 0")
    private Integer scoreInicial = 0;

    @Builder.Default
    @Column(name = "score_final", columnDefinition = "INT DEFAULT 0")
    private Integer scoreFinal = 0;
}

