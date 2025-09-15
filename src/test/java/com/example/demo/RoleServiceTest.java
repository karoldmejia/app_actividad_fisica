package com.example.demo;

import com.example.demo.model.Role;
import com.example.demo.model.RolePermission;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private IRoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;
    private Role savedRole;

    @BeforeEach
    void setUp() {
        // Rol simple
        role = new Role();
        role.setId(null);
        role.setName("PRUEBA");
        role.setDescription("Rol de prueba");
        role.setRolePermissions(Collections.emptyList());

        // Rol guardado (simula base de datos)
        savedRole = new Role();
        savedRole.setId(1L);
        savedRole.setName("PRUEBA");
        savedRole.setDescription("Rol de prueba");
        savedRole.setRolePermissions(Collections.emptyList());
    }

    @Test
    void saveRole_success() {
        when(roleRepository.save(any(Role.class))).thenReturn(savedRole);

        Role result = roleService.save(role);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("PRUEBA", result.getName());
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void getAllRoles_success() {
        when(roleRepository.findAll()).thenReturn(Arrays.asList(savedRole));

        List<Role> roles = roleService.getAllRoles();

        assertNotNull(roles);
        assertEquals(1, roles.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void getRoleById_success() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(savedRole));

        Optional<Role> roleOptional = roleService.getRoleById(1L);

        assertTrue(roleOptional.isPresent());
        assertEquals("PRUEBA", roleOptional.get().getName());
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    void getRoleByName_success() {
        when(roleRepository.findByName("PRUEBA")).thenReturn(Optional.of(savedRole));

        Optional<Role> roleOptional = roleService.getRoleByName("PRUEBA");

        assertTrue(roleOptional.isPresent());
        assertEquals(1L, roleOptional.get().getId());
        verify(roleRepository, times(1)).findByName("PRUEBA");
    }

    @Test
    void deleteById_success() {
        // Rol con permisos (lista modificable)
        Role roleWithPermissions = new Role();
        roleWithPermissions.setId(2L);
        roleWithPermissions.setRolePermissions(new ArrayList<>(Arrays.asList(
                new RolePermission(), new RolePermission()
        )));

        // Mockear findById correctamente
        when(roleRepository.findById(2L)).thenReturn(Optional.of(roleWithPermissions));
        doNothing().when(roleRepository).delete(any(Role.class));

        // Ejecutar
        roleService.deleteById(2L);

        // Verificaciones
        assertTrue(roleWithPermissions.getRolePermissions().isEmpty());
        verify(roleRepository, times(1)).delete(argThat(r -> r.getId().equals(2L)));
    }

    @Test
    void deleteById_roleNotFound_throwsException() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> roleService.deleteById(1L));
        assertEquals("Rol no encontrado", exception.getMessage());
    }
}