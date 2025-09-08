package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "USER_ROUTINE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoutine {
    @EmbeddedId
    private UserRoutineId id;

    private LocalDate assignmentDate;
    private String status;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("routineId")
    @JoinColumn(name = "routine_id")
    private Routine routine;
}
