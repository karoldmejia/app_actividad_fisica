package com.example.demo.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Crear o actualizar usuario
    public User save(User user) {
        if (user.getRole() == null) {
            throw new RuntimeException("Usuario debe tener un rol asignado");
        }
        // Codificar contraseña si no está codificada
        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user); // sirve para crear o actualizar
    }

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener usuario por ID
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // Obtener usuario por email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByInstitutionalEmail(email);
    }

    // Eliminar usuario
    public void deleteById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void initializedUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (!user.getPassword().startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
            }
        }
    }

}

