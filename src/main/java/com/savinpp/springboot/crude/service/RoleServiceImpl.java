package com.savinpp.springboot.crude.service;


import com.savinpp.springboot.crude.entity.Role;
import com.savinpp.springboot.crude.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByRole(name);
    }

}
