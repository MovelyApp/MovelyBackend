package br.movely.movelyapp.DTO;

import br.movely.movelyapp.model.Goal;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class GoalResponseDTO {

    private UUID id;
    private UUID challengeId;
    private String name;
    private String description;
    private String type;

    public static GoalResponseDTO fromEntity(Goal goal) {
        return new GoalResponseDTO(
                goal.getId(),
                goal.getChallengeId(),
                goal.getName(),
                goal.getDescription(),
                goal.getClass().getSimpleName()
        );
    }
}