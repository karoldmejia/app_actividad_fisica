package com.example.demo.service;

import com.example.demo.model.Permission;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {
    List<Permission> findAll();
    Optional<Permission> findById(int id);
    Permission save(Permission permission);
    Permission update(int id, Permission permission);
    void deleteById(int id);
}
