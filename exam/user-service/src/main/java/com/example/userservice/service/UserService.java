package com.example.userservice.service;

import com.example.userservice.dto.UserDetailDto;
import com.example.userservice.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public User getUserById(Long id);
    public User createUser(User user);
    public User updateUserLock(Long id, boolean lock);
    public void deleteUserById(Long id);
    public UserDetailDto getUserDetailById(Long id);
}
