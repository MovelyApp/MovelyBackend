package br.movely.movelyapp.service;

import br.movely.movelyapp.model.Goal;
import br.movely.movelyapp.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> listAll() {
        return goalRepository.findAll();
    }

    public List<Goal> listByChallenge(UUID challengeId) {
        return goalRepository.findByChallengeId(challengeId);
    }

    public Goal findById(UUID id) {
        return goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found: " + id));
    }

    public void delete(UUID id) {
        if (!goalRepository.existsById(id)) {
            throw new RuntimeException("Goal not found: " + id);
        }
        goalRepository.deleteById(id);
    }
}