package com.example.demo.controller;

import com.example.demo.model.Permission;
import com.example.demo.service.impl.PermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionServiceImpl permissionService;

    // Obtener todos
    @GetMapping
    public List<Permission> getAllPermissions() {
        return permissionService.findAll();
    }

    // Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable int id) {
        return permissionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear permiso
    @PostMapping
    public Permission createPermission(@RequestBody Permission permission) {
        return permissionService.save(permission);
    }

    // Actualizar permiso
    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable int id, @RequestBody Permission permission) {
        try {
            return ResponseEntity.ok(permissionService.update(id, permission));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Borrar permiso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable int id) {
        permissionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
