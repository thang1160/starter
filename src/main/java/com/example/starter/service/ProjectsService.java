package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.Projects;
import com.example.starter.model.PagingParams;
import com.example.starter.model.PagingResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class ProjectsService extends BaseService {

    public static PagingResponse<Projects> findAll(PagingParams params) {
        PagingResponse<Projects> response = new PagingResponse<>();
        List<Projects> list = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder qb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = qb.createQuery(Long.class);
            cq.select(qb.count(cq.from(Projects.class)));
            Long length = em.createQuery(cq).getSingleResult();
            TypedQuery<Projects> query = em.createQuery("select NEW com.example.starter.entity.Projects(p.projectId, p.projectName, count(m), count(t)) from Projects p left join p.projectMilestoness m left join p.projectTestRuns t group by p.projectId, p.projectName", Projects.class);
            query.setFirstResult(params.getStartPosition());
            query.setMaxResults(params.getPageSize());
            list = query.getResultList();
            response = new PagingResponse<>(length, list);
        } finally {
            em.close();
        }
        return response;
    }
}
