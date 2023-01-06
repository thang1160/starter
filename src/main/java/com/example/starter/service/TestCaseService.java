package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.entity.TestCase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class TestCaseService extends BaseService {
    private static Logger logger = Logger.getLogger(TestCaseService.class.getName());
    private static final int BATCH_SIZE = 100;

    public static void main(String[] args) {
        TestCaseService.findAllByProjectId(1);
    }

    public static List<TestCase> findAllByProjectId(int projectId) {
        List<TestCase> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<TestCase> query = em.createQuery("select new com.example.starter.entity.TestCase(t, u, s) from TestCase t join t.user u join t.section s where t.projectId = ?1", TestCase.class);
            query.setParameter(1, projectId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static void update(TestCase input) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            TestCase testCase = em.find(TestCase.class, input.getCaseId());
            testCase.setCaseName(input.getCaseName());
            testCase.setSectionId(input.getSectionId());
            testCase.setPriorityId(input.getPriorityId());
            testCase.setEstimate(input.getEstimate());
            testCase.setPreconditions(input.getPreconditions());
            testCase.setSteps(input.getSteps());
            testCase.setExpectedResult(input.getExpectedResult());
            em.merge(testCase);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static int delete(Long testCaseId) {
        EntityManager em = getEntityManager();
        int deletedCount = 0;
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM TestCase c where c.caseId = ?1");
            query.setParameter(1, testCaseId);
            deletedCount = query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "delete test case {0} failed {1}", new Object[] {testCaseId, e});
        } finally {
            em.close();
        }
        return deletedCount;
    }

    public static void createBatch(List<TestCase> testCases) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            for (int i = 0; i < testCases.size(); i++) {
                TestCase testCase = testCases.get(i);
                if (i > 0 && i % BATCH_SIZE == 0) {
                    em.flush();
                    em.clear();
                }
                em.persist(testCase);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
