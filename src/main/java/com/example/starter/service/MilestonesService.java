package com.example.starter.service;

import java.time.LocalDate;
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
                    "group by m.milestoneId, m.milestoneName, m.description, m.startDate, m.endDate, m.isStarted, m.projectId, m.completedOn, m.status, m.userId, m.isCompleted", Milestones.class);
            query.setParameter(1, projectId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

    public static void update(Milestones input) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Milestones milestone = em.find(Milestones.class, input.getMilestoneId());
            if (milestone.getIsCompleted()) {
                throw new IllegalStateException("This milestone is completed. You can no longer modify this milestone or add new milestone.");
            }
            if (input.getIsCompleted()) {
                milestone.setCompletedOn(LocalDate.now());
            }
            milestone.setMilestoneName(input.getMilestoneName());
            milestone.setDescription(input.getDescription());
            milestone.setStartDate(input.getStartDate());
            milestone.setEndDate(input.getEndDate());
            milestone.setIsCompleted(input.getIsCompleted());
            em.merge(milestone);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
