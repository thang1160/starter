package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.Priorities;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class PrioritiesService extends BaseService {

    public static List<Priorities> findAll() {
        List<Priorities> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Priorities> query = em.createQuery("select t from Priorities t", Priorities.class);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

}
