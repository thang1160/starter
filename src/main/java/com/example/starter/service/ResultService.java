package com.example.starter.service;

import com.example.starter.entity.Result;
import jakarta.persistence.EntityManager;

public class ResultService extends BaseService {

    public static void update(Result input) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Result result = em.find(Result.class, input.getResultId());
            result.setStatus(input.getStatus());
            result.setComment(input.getComment());
            em.merge(result);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
