package com.example.userservice.controller;

import com.example.userservice.dto.UserDetailDto;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{/users}")
public class UserController {
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){return userService.getAllUsers();};

    @GetMapping("{/id}")
    public User getUserById(@PathVariable Long id){return userService.getUserById(id);};

    @PostMapping
    public User createUser(@RequestBody User user){return userService.createUser(user);};

    @PostMapping
    public User updateUserLock(Long id, boolean lock){return userService.updateUserLock(id, lock);};

    @DeleteMapping("{/id}")
    public void deleteUserById(@PathVariable Long id){userService.deleteUserById(id);};

    @GetMapping("{/id/details}")
    public UserDetailDto getUserDetailById(@PathVariable Long id){return userService.getUserDetailById(id);};
}
