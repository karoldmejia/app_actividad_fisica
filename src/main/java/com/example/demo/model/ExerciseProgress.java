package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "EXERCISE_PROGRESS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int progressId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime progressDate;
    private Integer setsCompleted;
    private Integer repsCompleted;
    private Integer timeCompleted;
    private Integer effortLevel;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "routine_id", referencedColumnName = "routineId"),
            @JoinColumn(name = "exercise_id", referencedColumnName = "exerciseId")
    })
    private RoutineExercise routineExercise;
}
