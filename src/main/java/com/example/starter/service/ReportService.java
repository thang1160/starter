package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.Report;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ReportService extends BaseService {

    public static void main(String[] args) {
        findAllByProjectId(1);
    }

    public static List<Report> findAllByProjectId(int projectId) {
        List<Report> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Report> query = em.createQuery("select a from Report a where a.projectId = ?1", Report.class);
            query.setParameter(1, projectId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

}
