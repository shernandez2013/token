package com.oauth.token.service;

import com.oauth.token.models.entities.Role;
import com.oauth.token.models.request.RoleRequest;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role createRole(RoleRequest roleRequest);
    Optional<Role> updateRole(Long id, RoleRequest roleRequest);
    Optional<Role> getRoleById(Long id);
    List<Role> getAllRoles();
    void deleteRole(Long id);
}

