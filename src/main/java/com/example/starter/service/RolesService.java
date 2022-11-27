package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.Roles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class RolesService extends BaseService {

    public static List<Roles> findAll() {
        List<Roles> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Roles> query = em.createQuery("select r from Roles r", Roles.class);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

}
