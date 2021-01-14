package com.savinpp.springboot.crude.service;

import com.savinpp.springboot.crude.dao.UserDao;
import com.savinpp.springboot.crude.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Role getRoleByName(String name) {
        return userDao.getRoleByName(name);
    }

    @Override
    @Transactional
    public List<Role> listRoles() {
        return userDao.listRoles();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<User> index() {
        return userDao.index();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public User show (int id) {
        return (entityManager.find(User.class,id));
    }
    @Override
    @Transactional
    public void save (User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    @Transactional
    public void update (User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }

    @Override
    @Transactional
    public void delete (User user) {
        entityManager.remove(entityManager.find(User.class,user.getId()));
    }

    @Override
    public User showUserByUsername(String login) {
        return userDao.showUserByUsername(login);
    }

    @Override
    public List<String> getAllRoles(Role role) {
        Query query = entityManager.createQuery("select role from Role");
        return query.getResultList();
    }



    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly=true)
    public UserDetails loadUserByUsername (String a) throws UsernameNotFoundException {
        User user=userDao.showUserByUsername(a);
        Set<GrantedAuthority> grantedAuthorities= new HashSet<>();
        for (Role role: user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),grantedAuthorities);
    }

}
