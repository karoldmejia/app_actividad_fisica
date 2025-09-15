package com.example.demo.service.impl;

import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.model.RolePermission;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;

    // Crear o actualizar rol
    public Role save(Role role) {
        if (role.getRolePermissions() == null || role.getRolePermissions().isEmpty()) {
            throw new RuntimeException("El rol debe tener al menos un permiso");
        }
        return roleRepository.save(role);
    }

    // Obtener todos los roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Obtener rol por ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Obtener rol por nombre
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    // Eliminar rol
    public void deleteById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        role.getRolePermissions().clear(); // limpiar permisos antes de borrar
        roleRepository.delete(role);
    }

    // Agregar un permiso a un rol
    public Role addPermissionToRole(Long roleId, Permission permission) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole(role);
        rolePermission.setPermission(permission);
        role.getRolePermissions().add(rolePermission);

        return roleRepository.save(role);
    }

    // Remover un permiso de un rol
    public Role removePermissionFromRole(Long roleId, Permission permission) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        // Verificar si es el último permiso
        if (role.getRolePermissions().size() <= 1 &&
                role.getRolePermissions().stream()
                        .anyMatch(rp -> rp.getPermission().getId()==(permission.getId()))) {
            throw new RuntimeException(
                    "No se puede eliminar el último permiso del rol. Asigne otro permiso antes o elimine el rol."
            );
        }
        role.getRolePermissions().removeIf(rp -> rp.getPermission().getId()==(permission.getId()));
        return roleRepository.save(role);
    }

}
