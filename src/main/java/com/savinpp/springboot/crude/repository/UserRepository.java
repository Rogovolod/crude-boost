package com.savinpp.springboot.crude.repository;

import com.savinpp.springboot.crude.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
