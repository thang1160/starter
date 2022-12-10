package com.example.starter.service;

import java.util.ArrayList;
import java.util.List;
import com.example.starter.entity.Functionality;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class FunctionalityService extends BaseService{

    public static void main(String[] args) {
        System.out.println(findByUserId(2L));
    }

    public static List<Functionality> findByUserId(Long userId) {
        List<Functionality> functionalities = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Functionality> query = em.createQuery("select f from Users u join u.role r join r.roleFunctionalities rf join rf.functionality f where u.userId = ?1", Functionality.class);
            query.setParameter(1, userId);
            functionalities = query.getResultList();
        } finally {
            em.close();
        }
        return functionalities;
    }
}
