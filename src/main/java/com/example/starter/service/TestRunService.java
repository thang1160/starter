package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.TestRun;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class TestRunService extends BaseService {

    public static List<TestRun> findAllByProjectId(int projectId) {
        List<TestRun> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<TestRun> query = em.createQuery("select new com.example.starter.entity.TestRun(t, u.fullname) from TestRun t join t.user u where t.projectId = ?1", TestRun.class);
            query.setParameter(1, projectId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }
}
