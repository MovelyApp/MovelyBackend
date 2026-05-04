package br.movely.movelyapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankingEntry {

    private Long id;
    private Long rankingId;

    private Long userId;

    private Double score;
    private Integer position;
}
