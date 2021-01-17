package com.savinpp.springboot.crude.service;


import com.savinpp.springboot.crude.entity.User;
import com.savinpp.springboot.crude.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        User user = null;
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user;
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

}
