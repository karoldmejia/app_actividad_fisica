package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.User;

public interface IUserService {
    public Optional<User> findByEmail(String email);
    public User save(User user);
    public List<User> getAllUsers();
    public Optional<User> getUserById(Integer id);
    public void initializedUsers();
}
