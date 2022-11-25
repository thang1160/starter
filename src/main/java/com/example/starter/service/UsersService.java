package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
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

}
