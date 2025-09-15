package com.example.demo;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    private Role role;
    private User user;
    private User savedUser;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setName("Prueba");
        role.setDescription("Rol de prueba");

        // Usuario nuevo (sin id, para crear)
        user = new User();
        user.setUserId(null);
        user.setName("Karol");
        user.setInstitutionalEmail("karol@icesi.edu.co");
        user.setPassword("12345");
        user.setRole(role);

        // Usuario guardado (con id, simula DB)
        savedUser = new User();
        savedUser.setUserId(1);
        savedUser.setName("Karol");
        savedUser.setInstitutionalEmail("karol@icesi.edu.co");
        savedUser.setPassword("$2a$hashedpassword");
        savedUser.setRole(role);
    }

    @Test
    void saveUser_success() {
        when(passwordEncoder.encode(any())).thenReturn("hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.save(user);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals("Karol", result.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void saveUser_withoutRole_throwsException() {
        user.setRole(null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.save(user));
        assertEquals("Usuario debe tener un rol asignado", exception.getMessage());
    }

    @Test
    void getAllUsers_success() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(savedUser));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(savedUser));

        Optional<User> userOptional = userService.getUserById(1);

        assertTrue(userOptional.isPresent());
        assertEquals("Karol", userOptional.get().getName());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void deleteById_success() {
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1);

        userService.deleteById(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteById_userNotFound_throwsException() {
        when(userRepository.existsById(1)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteById(1));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void findByEmail_success() {
        when(userRepository.findByInstitutionalEmail("karol@icesi.edu.co"))
                .thenReturn(Optional.of(savedUser));

        Optional<User> userOptional = userService.findByEmail("karol@icesi.edu.co");

        assertTrue(userOptional.isPresent());
        assertEquals("Karol", userOptional.get().getName());
        verify(userRepository, times(1)).findByInstitutionalEmail("karol@icesi.edu.co");
    }

    @Test
    void changeUserRole_success() {
        Role newRole = new Role();
        newRole.setName("NuevoRol");
        newRole.setDescription("Rol actualizado");
        when(userRepository.findById(1)).thenReturn(Optional.of(savedUser));
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        User updatedUser = userService.changeUserRole(1, newRole);

        // Verificamos que el rol haya cambiado
        assertNotNull(updatedUser);
        assertEquals("NuevoRol", updatedUser.getRole().getName());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(savedUser);
    }

    @Test
    void changeUserRole_userNotFound_throwsException() {
        Role newRole = new Role();
        newRole.setName("NuevoRol");
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.changeUserRole(1, newRole));

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, never()).save(any(User.class));
    }

}
