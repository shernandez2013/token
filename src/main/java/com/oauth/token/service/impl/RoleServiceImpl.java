package com.oauth.token.service.impl;

import com.oauth.token.models.entities.Role;
import com.oauth.token.models.request.RoleRequest;
import com.oauth.token.repository.RoleRepository;
import com.oauth.token.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role createRole(RoleRequest roleRequest) {
        Role role = Role.builder()
                .roleName(roleRequest.getRoleName())
                .description(roleRequest.getDescription())
                .build();
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> updateRole(Long id, RoleRequest roleRequest) {
        return roleRepository.findById(id).map(role -> {
            role.setRoleName(roleRequest.getRoleName());
            role.setDescription(roleRequest.getDescription());
            return roleRepository.save(role);
        });
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}

