package com.oauth.token.service.impl;

import com.oauth.token.models.entities.Role;
import com.oauth.token.models.entities.User;
import com.oauth.token.models.request.UserRequest;
import com.oauth.token.repository.RoleRepository;
import com.oauth.token.repository.UserRepository;
import com.oauth.token.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public User createUser(UserRequest userRequest) {

        Optional<Role> role = roleRepository.findById(userRequest.getRoleId());
        Set<Role> roles = new HashSet<>();
        roles.add(role.get());
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(encrypt(userRequest.getPassword())) // Asegúrate de cifrar la contraseña antes de guardar
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .createdAt(LocalDateTime.now())
                .roles(roles)
                .build();
        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(Long id, UserRequest userRequest) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userRequest.getUsername());
            user.setPassword(encrypt(userRequest.getPassword())); // Asegúrate de que estás encriptando la contraseña si es necesario.
            user.setEmail(userRequest.getEmail());
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            return userRepository.save(user);
        });
    }

    private String encrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private void decrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordMatch = passwordEncoder.matches(password, "storedHashedPassword");

        if (isPasswordMatch) {
            System.out.println("password match.");
        } else {
            System.out.println("Wrong password.");
        }
    }
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
