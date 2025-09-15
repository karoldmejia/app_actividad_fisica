package com.example.demo;

import com.example.demo.model.Permission;
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
        // Permisos de ejemplo
        Permission permiso1 = new Permission();
        permiso1.setId(1);
        permiso1.setName("PERMISO_1");

        Permission permiso2 = new Permission();
        permiso2.setId(2);
        permiso2.setName("PERMISO_2");

        // Rol simple con permisos
        role = new Role();
        role.setId(null);
        role.setName("PRUEBA");
        role.setDescription("Rol de prueba");

        RolePermission rp1 = new RolePermission();
        rp1.setPermission(permiso1);
        rp1.setRole(role);

        RolePermission rp2 = new RolePermission();
        rp2.setPermission(permiso2);
        rp2.setRole(role);

        role.setRolePermissions(new ArrayList<>(Arrays.asList(rp1, rp2)));

        // Rol guardado (simula base de datos)
        savedRole = new Role();
        savedRole.setId(1L);
        savedRole.setName("PRUEBA");
        savedRole.setDescription("Rol de prueba");
        savedRole.setRolePermissions(role.getRolePermissions());
    }

    @Test
    void saveRole_success() {
        when(roleRepository.save(any(Role.class))).thenReturn(savedRole);

        Role result = roleService.save(role);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("PRUEBA", result.getName());

        // Verificamos que tenga permisos
        assertNotNull(result.getRolePermissions());
        assertEquals(2, result.getRolePermissions().size());

        verify(roleRepository, times(1)).save(role);
    }

    @Test
    void saveRole_withoutPermissions_throwsException() {
        role.setRolePermissions(Collections.emptyList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> roleService.save(role));

        assertEquals("El rol debe tener al menos un permiso", exception.getMessage());
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
        when(roleRepository.findById(2L)).thenReturn(Optional.of(savedRole));
        doNothing().when(roleRepository).delete(any(Role.class));

        roleService.deleteById(2L);

        // Se deben limpiar los permisos
        assertTrue(savedRole.getRolePermissions().isEmpty());
        verify(roleRepository, times(1)).delete(savedRole);
    }

    @Test
    void deleteById_roleNotFound_throwsException() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> roleService.deleteById(1L));
        assertEquals("Rol no encontrado", exception.getMessage());
    }
}