package com.savinpp.springboot.crude.repository;


import com.savinpp.springboot.crude.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);
}
