package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "USER_TRAINER_ASSIGNMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTrainerAssignment {
    @EmbeddedId
    private UserTrainerAssignmentId id;

    private LocalDate assignmentDate;
    private String status;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("trainerId")
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}