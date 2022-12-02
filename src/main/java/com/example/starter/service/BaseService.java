package com.example.starter.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class BaseService {
    protected BaseService() {}

    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("pu");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void create(Object entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
