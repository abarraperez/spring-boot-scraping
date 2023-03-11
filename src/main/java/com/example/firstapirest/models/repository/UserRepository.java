package com.example.firstapirest.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.firstapirest.models.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    
}
