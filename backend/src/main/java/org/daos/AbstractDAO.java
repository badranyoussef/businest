package org.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public abstract class AbstractDAO<T> implements InterfaceDAO<T> {

    public EntityManagerFactory emf;
    private Class<T> entityClass;

    public AbstractDAO(EntityManagerFactory emf, Class<T> entityClass) {
        this.emf = emf;
        this.entityClass = entityClass;
    }

    @Override
    public T create(T entity) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public int update(T entity) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            return 1;
        }
    }

    @Override
    public int delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            T entityToDelete = em.find(entityClass, id);
            if (entityToDelete != null) {
                em.remove(entityToDelete);
                em.getTransaction().commit();
                return 1;
            }
            return 0;
        }
    }

    @Override
    public T getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(entityClass, id);
        }
    }
}