package br.movely.movelyapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankingEntry {

    private Integer id;
    private Integer rankingId;

    private Integer userId;

    private Double score;
    private Integer position;
}