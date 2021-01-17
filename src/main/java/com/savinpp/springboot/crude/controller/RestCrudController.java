package com.savinpp.springboot.crude.controller;



import com.savinpp.springboot.crude.entity.User;
import com.savinpp.springboot.crude.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class RestCrudController {

    private final UserServiceImpl userService;

    public RestCrudController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> showAllUser() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
