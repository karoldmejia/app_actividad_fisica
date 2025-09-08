package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SPACE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spaceId;

    private String name;
    private String location;
    private Integer capacity;
}
