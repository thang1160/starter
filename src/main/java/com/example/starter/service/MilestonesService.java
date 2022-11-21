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
            TypedQuery<Milestones> query = em.createQuery("select m from Milestones m where m.projectId = ?1", Milestones.class);
            query.setParameter(1, projectId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }
}
