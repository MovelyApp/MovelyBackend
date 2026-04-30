package br.movely.movelyapp.controller;

import br.movely.movelyapp.model.Ranking;
import br.movely.movelyapp.service.RankingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/{groupId}/{challengeId}")
    public Ranking getRanking(
            @PathVariable Integer groupId,
            @PathVariable Integer challengeId) {

        List<Integer> users = List.of(1, 2, 3);

        Map<Integer, Double> scores = Map.of(
                1, 100.0,
                2, 200.0,
                3, 150.0
        );

        return rankingService.generateRanking(groupId, challengeId, users, scores);
    }
}