package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    private String originType;
    private Integer originId;
    private String text;
    private LocalDateTime sentDate;
    private String readFlag;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
