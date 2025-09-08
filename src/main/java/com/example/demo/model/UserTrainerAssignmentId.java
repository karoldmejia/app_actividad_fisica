package com.example.demo.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainerAssignmentId implements Serializable {
    private Integer userId;
    private Integer trainerId;
}