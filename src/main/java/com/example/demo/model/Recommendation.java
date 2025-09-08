package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RECOMMENDATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recommendationId;

    private LocalDateTime recommendationDate;
    private String content;
    private String status;

    @ManyToOne
    @JoinColumn(name = "progress_id")
    private ExerciseProgress progress;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}
