package br.movely.movelyapp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Ranking {

    private Long id;
    private UUID groupId;
    private UUID challengeId;

    private LocalDateTime updatedAt;

    private List<RankingEntry> entries = new ArrayList<>();
}
