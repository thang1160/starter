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
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

public class ProjectsService extends BaseService {

    public static PagingResponse<Projects> findAll(PagingParams params, String projectName) {
        PagingResponse<Projects> response = new PagingResponse<>();
        List<Projects> list = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Projects> root = cq.from(Projects.class);
            cq.select(cb.count(root));
            Expression<String> path = root.get("projectName");
            cq.where(cb.like(cb.lower(path), "%" + projectName.toLowerCase() + "%"));
            Long length = em.createQuery(cq).getSingleResult();

            TypedQuery<Projects> query = em.createQuery("select NEW com.example.starter.entity.Projects(p.projectId, p.projectName, count(distinct m), count(distinct t)) from Projects p left join p.projectMilestoness m left join p.projectTestRuns t where lower(p.projectName) like lower(concat('%', ?1,'%')) group by p.projectId, p.projectName", Projects.class);
            query.setFirstResult(params.getStartPosition());
            query.setMaxResults(params.getPageSize());
            query.setParameter(1, projectName);
            list = query.getResultList();
            response = new PagingResponse<>(length, list);
        } finally {
            em.close();
        }
        return response;
    }
}
