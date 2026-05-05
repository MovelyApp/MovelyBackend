package br.movely.movelyapp.controller;

import br.movely.movelyapp.model.Ranking;
import br.movely.movelyapp.service.RankingService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/{groupId}/{challengeId}")
    public Ranking getRanking(
            @PathVariable UUID groupId,
            @PathVariable UUID challengeId) {

        List<Long> users = Arrays.asList(1L, 2L, 3L);

        Map<Long, Double> scores = new HashMap<>();
        scores.put(1L, 100.0);
        scores.put(2L, 200.0);
        scores.put(3L, 150.0);

        return rankingService.generateRanking(groupId, challengeId, users, scores);
    }
}
