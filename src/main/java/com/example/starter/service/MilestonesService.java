package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.Milestones;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class MilestonesService extends BaseService {

    public static List<Milestones> findAllByProjectId(int projectId) {
        List<Milestones> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Milestones> query = em.createQuery("select new com.example.starter.entity.Milestones(m, sum(case when t.isCompleted = 0 then 1 else 0 end))\n" +
                    "from Milestones m left join m.milestoneTestRuns t where m.projectId = ?1\n" +
                    "group by m.milestoneId, m.milestoneName, m.description, m.startDate, m.endDate, m.isStarted, m.projectId, m.completedOn, m.status", Milestones.class);
            query.setParameter(1, projectId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }
}
