package com.example.demo.service;

import com.example.demo.model.Permission;
import com.example.demo.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    Role save(Role role);
    List<Role> getAllRoles();
    Optional<Role> getRoleById(Long id);
    Optional<Role> getRoleByName(String name);
    void deleteById(Long id);
    public Role addPermissionToRole(Long roleId, Permission permission);
    public Role removePermissionFromRole(Long roleId, Permission permission);
}
