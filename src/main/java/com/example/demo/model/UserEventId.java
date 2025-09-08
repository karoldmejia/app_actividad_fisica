package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEventId implements Serializable {
    private Integer eventId;
    private Integer userId;
}