package com.example.demo;

import com.example.demo.model.Permission;
import com.example.demo.repository.IPermissionRepository;
import com.example.demo.service.impl.PermissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PermissionServiceTest {

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Mock
    private IPermissionRepository permissionRepository;

    private Permission permission;
    private List<Permission> permissions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Permiso de ejemplo
        permission = new Permission();
        permission.setId(1);
        permission.setName("READ");
        permission.setDescription("Read access");

        // Lista de permisos
        permissions = new ArrayList<>();
        permissions.add(permission);
        permissions.add(new Permission(2, "WRITE", "Write access"));
    }

    @Test
    void savePermission_success() {
        when(permissionRepository.save(any(Permission.class))).thenReturn(permission);

        Permission saved = permissionService.save(permission);

        assertNotNull(saved);
        assertEquals("READ", saved.getName());
        verify(permissionRepository, times(1)).save(permission);
    }

    @Test
    void findById_success() {
        when(permissionRepository.findById(1)).thenReturn(Optional.of(permission));

        Optional<Permission> found = permissionService.findById(1);

        assertTrue(found.isPresent());
        assertEquals("READ", found.get().getName());
        verify(permissionRepository, times(1)).findById(1);
    }

    @Test
    void findById_notFound() {
        when(permissionRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Permission> found = permissionService.findById(999);

        assertTrue(found.isEmpty());
        verify(permissionRepository, times(1)).findById(999);
    }

    @Test
    void updatePermission_success() {
        Permission updateData = new Permission();
        updateData.setName("WRITE");
        updateData.setDescription("Write access");

        when(permissionRepository.findById(1)).thenReturn(Optional.of(permission));
        when(permissionRepository.save(any(Permission.class))).thenReturn(permission);

        Permission updated = permissionService.update(1, updateData);

        assertEquals("WRITE", updated.getName());
        assertEquals("Write access", updated.getDescription());
        verify(permissionRepository, times(1)).findById(1);
        verify(permissionRepository, times(1)).save(permission);
    }

    @Test
    void updatePermission_notFound() {
        Permission updateData = new Permission();
        updateData.setName("NEW");
        updateData.setDescription("New permission");

        when(permissionRepository.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            permissionService.update(999, updateData);
        });

        assertTrue(exception.getMessage().contains("Permission not found"));
        verify(permissionRepository, times(1)).findById(999);
        verify(permissionRepository, never()).save(any(Permission.class));
    }

    @Test
    void deletePermission_success() {
        doNothing().when(permissionRepository).deleteById(1);

        permissionService.deleteById(1);

        verify(permissionRepository, times(1)).deleteById(1);
    }

    @Test
    void deletePermission_notFound() {
        doThrow(new RuntimeException("Permission not found")).when(permissionRepository).deleteById(999);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            permissionService.deleteById(999);
        });

        assertEquals("Permission not found", exception.getMessage());
        verify(permissionRepository, times(1)).deleteById(999);
    }

    @Test
    void findAllPermissions_success() {
        when(permissionRepository.findAll()).thenReturn(permissions);

        List<Permission> result = permissionService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(permissionRepository, times(1)).findAll();
    }

    @Test
    void getPermissionsByIds_success() {
        List<Integer> ids = List.of(1, 2);
        when(permissionRepository.findAllById(ids)).thenReturn(permissions);

        List<Permission> result = permissionService.getPermissionsByIds(ids);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(permissionRepository, times(1)).findAllById(ids);
    }


}
