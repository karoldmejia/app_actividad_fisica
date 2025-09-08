package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoutineId implements Serializable {
    private Integer userId;
    private Integer routineId;
}