package com.example.starter.service;

import com.example.starter.entity.ProjectUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ProjectUserService extends BaseService {

    public static boolean isProjectMember( Long projectId, Long userId) {
        boolean result = false;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<ProjectUser> query = em.createQuery("select t from ProjectUser t where t.projectId = ?1 and t.userId = ?2", ProjectUser.class);
            query.setParameter(1, projectId);
            query.setParameter(2, userId);
            result = query.getResultList().size() == 1;
        } finally {
            em.close();
        }
        return result;
    }
}
