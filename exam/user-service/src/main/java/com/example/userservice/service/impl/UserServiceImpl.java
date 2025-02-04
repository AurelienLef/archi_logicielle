package com.example.userservice.service.impl;

import com.example.userservice.dto.UserDetailDto;
import com.example.userservice.entity.User;
import com.example.userservice.kafka.UserKafka;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private UserService userService;

    private UserKafka userKafka;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUserLock(Long id, boolean lock) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setLocked(lock);  // Met à jour l'état verrouillé
                    return userRepository.save(user); // Sauvegarde en base
                })
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id)); // Gère le cas où l'utilisateur n'existe pas

    }

    @Override
    public void deleteUserById(Long id) {
        userKafka.sendDeleteUserEvent(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetailDto getUserDetailById(Long id) {
        return null;
    }
}
