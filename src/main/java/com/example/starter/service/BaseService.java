package com.example.starter.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;

public class BaseService {
    protected BaseService() {}

    private static final EntityManagerFactory emf;
    private static final int BATCH_SIZE = 100;

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

    @Transactional
    public static void createBatch(Object[] entities) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            for (int i = 0; i < entities.length; i++) {
                if (i > 0 && i % BATCH_SIZE == 0) {
                    em.flush();
                    em.clear();
                }
                em.persist(entities[i]);
            }
            em.getTransaction().commit();
            em.clear();
        } finally {
            em.close();
        }
    }
}
