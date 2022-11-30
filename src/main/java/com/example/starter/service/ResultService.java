package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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

    public static List<Result> findAllByTestRunId(int testRunId) {
        List<Result> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Result> query = em.createQuery("select new com.example.starter.entity.Result(r, c.caseName, s.sectionName) from Result r join r.testCase c join c.section s where r.runId = ?1", Result.class);
            query.setParameter(1, testRunId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }
}
