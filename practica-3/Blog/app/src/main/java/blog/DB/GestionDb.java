package blog.DB;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by vacax on 03/06/16.
 */
public class GestionDb<T> {

    private static EntityManagerFactory emf;
    private Class<T> claseEntidad;
    private static GestionDb<?> instance;

    protected GestionDb(Class<T> claseEntidad) {
        this.claseEntidad = claseEntidad;
    }

    public static synchronized <T> GestionDb<T> getInstance(Class<T> claseEntidad) {
        if (instance == null) {
            instance = new GestionDb<>(claseEntidad);
            emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        }
        return (GestionDb<T>) instance;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public T crear(T entidad) throws IllegalArgumentException, EntityExistsException, PersistenceException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return entidad;
    }

    public T editar(T entidad) throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entidad);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return entidad;
    }

    public boolean eliminar(Object entidadId) throws PersistenceException {
        boolean ok = false;
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            T entidad = em.find(claseEntidad, entidadId);
            em.remove(entidad);
            em.getTransaction().commit();
            ok = true;
        } finally {
            em.close();
        }
        return ok;
    }

    public T find(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            return em.find(claseEntidad, id);
        } finally {
            em.close();
        }
    }

    public List<T> findAll() throws PersistenceException {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
            criteriaQuery.select(criteriaQuery.from(claseEntidad));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }
}