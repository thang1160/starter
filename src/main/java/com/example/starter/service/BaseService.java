package com.example.starter.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;

public class BaseService {

    private static final Logger logger = Logger.getLogger(BaseService.class.getName());

    protected BaseService() {}

    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("pu");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "initialize BaseService failed: ", e);
        }
    }

    public static EntityManager getEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("pu");
        }
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

    public static <T> T findById(Class<T> clazz, Long id) {
        EntityManager em = getEntityManager();

        T entity = null;
        try {
            entity = em.find(clazz, id);
            if (entity == null) {
                throw new EntityNotFoundException("Can't find " + clazz.getSimpleName() + " for ID " + id);
            }
        } finally {
            em.close();
        }
        return entity;
    }
}
