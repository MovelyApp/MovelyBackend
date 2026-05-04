package br.movely.movelyapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID groupId;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate finishDate;

    public Challenge() {}

    public Challenge(UUID groupId, String name, String description, LocalDate startDate, LocalDate finishDate) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public void encerrarDesafio() {
        this.finishDate = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDataInicio() {
        return startDate;
    }

    public void setDataInicio(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDataFim() {
        return finishDate;
    }

    public void setDataFim(LocalDate finishDate) {
        this.finishDate = finishDate;
    }
}