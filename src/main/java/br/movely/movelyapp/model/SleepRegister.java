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
@Table(name = "sleep_registers")
public class SleepRegister extends Register {

    @Column(nullable = false)
    private Double hours;

    @Column(nullable = false)
    private Integer quality;
}