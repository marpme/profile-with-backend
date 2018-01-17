package net.ziemers.swxercise.db.dao;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import net.ziemers.swxercise.db.BaseEntity;

/**
 * Stellt generische Persistenz-Funktionalität zur Verfügung. Wird normalerweise von den
 * spazialisierten Data Access Objects geerbt.
 */
@Stateless
// diese Bean ist nur die Alternative zur Ermittlung des Entity Managers
// Quelle: https://stackoverflow.com/questions/10185976/cdi-ambiguous-dependencies
@Alternative
public class GenericDao {

    /*
     * Der Entity Manager wird per CDI injiziert, damit er auch im Testkontext mit dem CDI-Runner funktioniert.
     * Im JEE-Container "zieht" dann die Produktion des Entity Managers in der Klasse "EntityMangerProducer".
     */
    @Inject
    protected EntityManager entityManager;

    /**
     * Speichert die gegebene Entität.
     *
     * @see EntityManager#persist(Object)
     * @param entity die zu speichernde Entität
     * @param <T> Generic für den Entity-Typ.
     * @return die Id der persistierten Entität
     */
    public <T extends BaseEntity> Long save(final T entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    public <T extends BaseEntity> T saveOrUpdate(final T entity) {
        return entityManager.merge(entity);
    }

    public <T extends BaseEntity> T remove(Class<T> entityType, Long primaryKey) {
        final T entity = findById(entityType, primaryKey);

        entityManager.remove(entity);
        return entity;
    }

    /**
     * @param entityType Entity-Typ.
     * @param primaryKey Id
     * @param <T> Generic für Entity-Typ
     * @return die Entität mit gegebener Id oder <code>null</code>, wenn keine gefunden
     */
    public <T extends BaseEntity> T findById(Class<T> entityType, Long primaryKey) {
        final T entity = entityManager.find(entityType, primaryKey);
        return entity;
    }

    /**
     * @param entityType Entity-Typ.
     * @param <T> Generic für den Entity-Typ.
     * @return alle Entitäten vom gegebenen Typ
     */
    public <T extends BaseEntity> Collection<T> findAll(Class<T> entityType) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityType);
        Root<T> root = cq.from(entityType);
        CriteriaQuery<T> selectAll = cq.select(root);
        TypedQuery<T> query = entityManager.createQuery(selectAll);
        return query.getResultList();
    }

}
