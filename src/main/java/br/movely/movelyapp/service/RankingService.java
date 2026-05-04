package br.movely.movelyapp.service;

import br.movely.movelyapp.model.Ranking;
import br.movely.movelyapp.model.RankingEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RankingService {

    public Ranking generateRanking(
            UUID groupId,
            UUID challengeId,
            List<Long> users,
            Map<Long, Double> scoreByUser) {

        Ranking ranking = new Ranking();
        ranking.setGroupId(groupId);
        ranking.setChallengeId(challengeId);
        ranking.setUpdatedAt(LocalDateTime.now());

        List<RankingEntry> entries = new ArrayList<>();

        for (Long userId : users) {

            double score = scoreByUser.getOrDefault(userId, 0.0);

            RankingEntry entry = new RankingEntry();
            entry.setUserId(userId);
            entry.setScore(score);

            entries.add(entry);
        }

        // sort descending
        entries.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

        // position with tie handling
        int position = 1;
        for (int i = 0; i < entries.size(); i++) {

            if (i > 0 && entries.get(i).getScore().equals(entries.get(i - 1).getScore())) {
                entries.get(i).setPosition(entries.get(i - 1).getPosition());
            } else {
                entries.get(i).setPosition(position);
            }

            position++;
        }

        ranking.setEntries(entries);

        return ranking;
    }
}
