package br.movely.movelyapp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Ranking {

    private Integer id;
    private Integer groupId;
    private Integer challengeId;

    private LocalDateTime updatedAt;

    private List<RankingEntry> entries = new ArrayList<>();
}