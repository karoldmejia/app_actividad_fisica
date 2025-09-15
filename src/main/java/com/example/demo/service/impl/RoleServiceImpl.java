package com.example.demo.service.impl;

import com.example.demo.model.Role;
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

}
