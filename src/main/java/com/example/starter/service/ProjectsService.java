package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.Projects;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ProjectsService extends BaseService {

    public static List<Projects> findAll() {
        List<Projects> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Projects> allQuery = em.createQuery("select NEW com.example.starter.entity.Projects(p.projectId, p.projectName, count(m), count(t)) from Projects p left join p.projectMilestoness m left join p.projectTestRuns t group by p.projectId, p.projectName", Projects.class);
            result = allQuery.getResultList();
        } finally {
            em.close();
        }
        return result;
    }
}
