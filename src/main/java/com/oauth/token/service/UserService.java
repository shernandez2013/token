package com.oauth.token.service;

import com.oauth.token.models.entities.User;
import com.oauth.token.models.request.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(UserRequest userRequest);

    Optional<User> updateUser(Long id, UserRequest userRequest);

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();

    void deleteUser(Long id);
}
