package com.example.starter.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.example.starter.entity.Result;
import com.example.starter.entity.TestCase;
import com.example.starter.entity.TestRun;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class TestRunService extends BaseService {
    private static final int BATCH_SIZE = 100;

    public static List<TestRun> findAllByProjectId(int projectId) {
        List<TestRun> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<TestRun> query = em.createQuery("select new com.example.starter.entity.TestRun(t, u.fullname\n" +
                    ", sum(case when r.status = 'Passed' then 1 else 0 end)\n" +
                    ", sum(case when r.status = 'Blocked' then 1 else 0 end)\n" +
                    ", sum(case when r.status = 'Retest' then 1 else 0 end)\n" +
                    ", sum(case when r.status = 'Failed' then 1 else 0 end)\n" +
                    ", sum(case when r.status = 'Untested' then 1 else 0 end)\n" +
                    ") from TestRun t join t.user u left join t.testRunResults r where t.projectId = ?1\n" +
                    "group by t.runId, t.runName, t.description, t.createdOn, t.milestoneId, t.userId, t.projectId, t.planId, t.isCompleted, t.completedOn, t.includeAll, t.assignedToId, u.fullname", TestRun.class);
            query.setParameter(1, projectId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static void create(TestRun testRun, List<TestCase> testCases) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(testRun);
            for (int i = 0; i < testCases.size(); i++) {
                Result result = new Result();
                result.setCaseId(testCases.get(i).getCaseId());
                result.setRunId(testRun.getRunId());
                if (i > 0 && i % BATCH_SIZE == 0) {
                    em.flush();
                    em.clear();
                }
                em.persist(result);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static void create(TestRun testRun, Set<Result> results) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(testRun);
            int i = 0;
            for (Result result : results) {
                result.setRunId(testRun.getRunId());
                if (i > 0 && i % BATCH_SIZE == 0) {
                    em.flush();
                    em.clear();
                }
                em.persist(result);
                i++;
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static void update(TestRun input) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            TestRun testRun = em.find(TestRun.class, input.getRunId());
            if (testRun.getIsCompleted()) {
                throw new IllegalStateException("This test run is completed. You can no longer modify this test run or add new test results.");
            }
            if (input.getIsCompleted()) {
                testRun.setIsCompleted(true);
                testRun.setCompletedOn(LocalDate.now());
            } else {
                testRun.setRunName(input.getRunName());
                testRun.setMilestoneId(input.getMilestoneId());
                testRun.setAssignedToId(input.getAssignedToId());
                testRun.setDescription(input.getDescription());
                testRun.setIncludeAll(input.getIncludeAll());
            }
            em.merge(testRun);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
