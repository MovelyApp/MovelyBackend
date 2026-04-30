package br.movely.movelyapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "study_registers")
public class StudyRegister extends Register {

    @Column(nullable = false)
    private Double hours;
}