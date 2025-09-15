package com.example.demo.repository;

import com.example.demo.model.Permission;
import com.example.demo.model.Role;
import com.example.demo.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolePermissionRepository extends JpaRepository<RolePermission, Integer>  {
    Optional<RolePermission> findByRoleAndPermission(Role role, Permission permission);
    long countByRole(Role role); // para contar cuántos permisos tiene un rol
    void deleteByRoleAndPermission(Role role, Permission permission);
    void deleteAllByRole(Role role);

}
