package com.example.demo.controller;

import com.example.demo.dto.RoleDTO;
import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.service.impl.PermissionServiceImpl;
import com.example.demo.service.impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceImpl roleService;
    private final PermissionServiceImpl permissionService;  // Necesario para convertir IDs a Permission

    @GetMapping
    public List<Role> getAll() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public Optional<Role> getById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping("/name/{name}")
    public Optional<Role> getByName(@PathVariable String name) {
        return roleService.getRoleByName(name);
    }

    @PostMapping
    public Role create(@RequestBody RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());

        // Convertir los IDs de permisos a objetos Permission
        List<Permission> permisos = permissionService.getPermissionsByIds(roleDTO.getPermissionIds());

        return roleService.save(role, permisos);
    }

    @PutMapping("/{id}")
    public Role update(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(id);
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());

        List<Permission> permisos = permissionService.getPermissionsByIds(roleDTO.getPermissionIds());

        return roleService.save(role, permisos);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.deleteById(id);
    }
}
