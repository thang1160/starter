package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.starter.entity.ProjectUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class ProjectUserService extends BaseService {
    private static Logger logger = Logger.getLogger(ProjectUserService.class.getName());

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

    public static int delete(Long projectUserId) {
        EntityManager em = getEntityManager();
        int deletedCount = 0;
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM ProjectUser c where c.projectUserId = ?1");
            query.setParameter(1, projectUserId);
            deletedCount = query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "delete project user {0} failed {1}", new Object[] {projectUserId, e});
        } finally {
            em.close();
        }
        return deletedCount;
    }

    public static List<ProjectUser> findAllByProjectId(int projectId) {
        List<ProjectUser> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<ProjectUser> query = em.createQuery("select new com.example.starter.entity.ProjectUser(a, u) from ProjectUser a join a.user u where a.projectId = ?1", ProjectUser.class);
            query.setParameter(1, projectId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }
}
