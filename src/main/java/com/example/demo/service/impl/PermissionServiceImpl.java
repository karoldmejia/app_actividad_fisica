package com.example.demo.service.impl;

import com.example.demo.model.Permission;
import com.example.demo.repository.IPermissionRepository;
import com.example.demo.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private IPermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> findById(int id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(int id, Permission permission) {
        Optional<Permission> existing = permissionRepository.findById(id);
        if (existing.isPresent()) {
            Permission p = existing.get();
            p.setName(permission.getName());
            p.setDescription(permission.getDescription());
            return permissionRepository.save(p);
        } else {
            throw new RuntimeException("Permission not found with id " + id);
        }
    }

    @Override
    public void deleteById(int id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public List<Permission> getPermissionsByIds(List<Integer> ids) {
        return permissionRepository.findAllById(ids);
    }
}
