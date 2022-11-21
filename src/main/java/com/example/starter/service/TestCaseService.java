package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.TestCase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class TestCaseService extends BaseService {

    public static List<TestCase> findAllByProjectId(int projectId) {
        List<TestCase> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<TestCase> query = em.createQuery("select new com.example.starter.entity.TestCase(t, u.fullname) from TestCase t join t.user u where t.projectId = ?1", TestCase.class);
            query.setParameter(1, projectId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }
}
