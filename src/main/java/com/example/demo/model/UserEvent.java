package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "USER_EVENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEvent {
    @EmbeddedId
    private UserEventId id;

    private LocalDate registrationDate;
    private String attended; // CHAR(1)

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}

