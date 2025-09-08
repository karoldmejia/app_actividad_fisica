package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROUTINE_EXERCISE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID autogenerado de la relación

    private Long routineId;  // FK hacia Routine
    private Long exerciseId; // FK hacia Exercise

    private Integer sets;
    private Integer reps;
    private Integer time;
}
