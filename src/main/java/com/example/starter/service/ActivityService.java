package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.Activity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ActivityService extends BaseService {

    public static void main(String[] args) {
        findAllByProjectId(1);
    }

    public static List<Activity> findAllByProjectId(int projectId) {
        List<Activity> result = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Activity> query = em.createQuery("select new com.example.starter.entity.Activity(a, u.fullname) from Activity a join a.user u where a.projectId = ?1", Activity.class);
            query.setParameter(1, projectId);
            result = query.getResultList();
        } finally {
            em.close();
        }
        return result;
    }

}
