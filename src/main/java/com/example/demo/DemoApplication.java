package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import com.example.demo.service.IUserService;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private IUserService userService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Profile("!test") // NO ejecutar durante tests
    public void init() {
        try {
            userService.initializedUsers();
        } catch (Exception e) {
            System.out.println("Error initializing users: " + e.getMessage());
        }
    }
}
