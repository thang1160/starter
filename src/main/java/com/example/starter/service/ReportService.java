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

    public static Report findByReportId(Long reportId) {
        Report result = new Report();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Report> query = em.createQuery("select new com.example.starter.entity.Report(a,u,p) from Report a join a.user u join a.project p where a.reportId = ?1", Report.class);
            query.setParameter(1, reportId);
            query.setMaxResults(1);
            result = query.getResultList().get(0);
        } finally {
            em.close();
        }
        return result;
    }

}
