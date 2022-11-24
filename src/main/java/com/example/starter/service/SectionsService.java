package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.Sections;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class SectionsService extends BaseService {

    public static List<Sections> findAllByProjectId(int projectId) {
        List<Sections> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Sections> query = em.createQuery("select t from Sections t where t.projectId = ?1", Sections.class);
            query.setParameter(1, projectId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

}
