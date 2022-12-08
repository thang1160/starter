package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.Util;
import com.example.starter.entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UsersService extends BaseService {

    public static List<Users> findAll() {
        List<Users> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Users> query = em.createQuery("select u from Users u", Users.class);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static void update(Users input) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Users user = em.find(Users.class, input.getUserId());
            user.setRoleId(input.getRoleId());
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static Integer getUserIdByEmailAndPassword(String email, String password) {
        Integer userId = 0;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Users> query = em.createQuery("select u from Users u where u.email = ?1", Users.class);
            query.setParameter(1, email);
            query.setMaxResults(1);
            List<Users> users = query.getResultList();
            if (!users.isEmpty()) {
                Users user = users.get(0);
                String salt = user.getSalt();
                String realPassword = user.getPassword();
                String hashedPassword = Util.hashPassword(password, salt);
                if (realPassword.equals(hashedPassword)) {
                    userId = user.getUserId();
                }
            }
        } finally {
            em.close();
        }
        return userId;
    }
}
