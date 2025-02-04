package com.example.userservice.service.impl;

import com.example.userservice.dto.UserDetailDto;
import com.example.userservice.rest.userServiceRest;
import com.example.userservice.entity.User;
import com.example.userservice.kafka.UserKafka;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private userServiceRest userServiceRest;

    private UserKafka userKafka;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUserLock(Long id, boolean lock) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setLocked(lock);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));

    }

    @Override
    public void deleteUserById(Long id) {
        userKafka.sendDeleteUserEvent(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetailDto getUserDetailById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetailDto dto = new UserDetailDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setMembershipType(user.getMembershipType());
        dto.setLocked(user.isLocked());

        dto.setBorrowings(userServiceRest.getBorrowingByUserId(user.getId()));

        return dto;
    }
}
