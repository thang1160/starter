package com.example.starter.service;

import java.util.List;
import com.example.starter.entity.Projects;
import com.example.starter.model.PagingParams;
import com.example.starter.model.PagingResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ProjectsService extends BaseService {

    public static PagingResponse<Projects> findAll(PagingParams params, String projectName, Long userId, boolean isViewAllProject) {
        PagingResponse<Projects> response = new PagingResponse<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Projects> query1 = em.createQuery("select distinct p from Projects p left join p.projectProjectUsers pu where lower(p.projectName) like lower(concat('%', ?1,'%')) and (pu.userId = ?2 or 1 = ?3)", Projects.class);
            query1.setParameter(1, projectName);
            query1.setParameter(2, userId);
            query1.setParameter(3, isViewAllProject ? 1 : 0);
            int length = query1.getResultList().size();

            TypedQuery<Projects> query2 = em.createQuery("select NEW com.example.starter.entity.Projects(p.projectId, p.projectName, p.startDate, p.endDate, count(distinct m), count(distinct t)) from Projects p left join p.projectProjectUsers pu left join p.projectMilestoness m left join p.projectTestRuns t where lower(p.projectName) like lower(concat('%', ?1,'%')) and (pu.userId = ?2 or 1 = ?3) group by p.projectId, p.projectName, p.startDate, p.endDate", Projects.class);
            query2.setFirstResult(params.getStartPosition());
            query2.setMaxResults(params.getPageSize());
            query2.setParameter(1, projectName);
            query2.setParameter(2, userId);
            query2.setParameter(3, isViewAllProject ? 1 : 0);
            List<Projects> list = query2.getResultList();
            response = new PagingResponse<>((long) length, list);
        } finally {
            em.close();
        }
        return response;
    }
}
