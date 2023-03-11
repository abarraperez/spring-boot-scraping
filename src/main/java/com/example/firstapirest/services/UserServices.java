package com.example.firstapirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.firstapirest.models.User;
import com.example.firstapirest.models.repository.UserRepository;

@Service
public class UserServices {
    
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    } 

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        return userRepository.save(existingUser);
    }

    public String deleteUser(int id) {
        userRepository.deleteById(id);
        return "User removed !! " + id;
    }


    
}
