package br.movely.movelyapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "goal")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "goal_type", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter @NoArgsConstructor
public abstract class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "challenge_id", nullable = false)
    private UUID challengeId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public abstract double calculateProgress(Map<String, Double> values);

    public abstract double calculatePoints(Map<String, Double> values);

}