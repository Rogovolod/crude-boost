package com.savinpp.springboot.crude.dao;

import com.savinpp.springboot.crude.entity.User;
import com.savinpp.springboot.crude.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleByName (String name) {
        return entityManager.createQuery("select role from Role role where role.role=:name", Role.class)
                .setParameter("name", name)
                .getSingleResult();

    }
    @Override
    public List<Role> listRoles() {
        Query query = entityManager.createQuery("from Role");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> index() {
        Query query = entityManager.createQuery("from User");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public User show(int id) {
        return (entityManager.find(User.class, id));
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(User user) { entityManager.remove(user);
    }


    @Override
    public User showUserByUsername(String username) {
        return entityManager
                .createQuery("select u from User u where u.login =?1", User.class)
                .setParameter(1, username)
                .getSingleResult();
    }

}