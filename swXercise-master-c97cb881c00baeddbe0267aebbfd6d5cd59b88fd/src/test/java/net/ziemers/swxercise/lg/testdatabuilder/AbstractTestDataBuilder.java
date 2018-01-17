package net.ziemers.swxercise.lg.testdatabuilder;

import javax.persistence.EntityManager;

public abstract class AbstractTestDataBuilder<T> {

    private static int count = 0;

    private EntityManager entityManager;

    /**
     * Creates an new TestdataBuilder with persistence.
     *
     * @param entityManager
     */
    public AbstractTestDataBuilder(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Creates an new TestdataBuilder without persistence.
     */
    public AbstractTestDataBuilder() {
    }

    public abstract T build();

    /**
     * Returns the EntityManager or null.
     *
     * @return {@link EntityManager} or null
     */
    protected final EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Ensure the TestdataBuilder is constructed with a
     * {@link EntityManager}
     *
     * @throws IllegalStateException
     *             if the TestdataBuilder is constructed without a
     *             {@link EntityManager}
     */
    protected final void ensureEntityManager() {
        if (entityManager == null) {
            throw new IllegalStateException("Cannot persist w/o entity manager");
        }
    }

    /**
     * {@inheritDoc} Executed within a new transaction.
     *
     * @throws IllegalStateException
     *             if the TestdataBuilder is constructed without a
     *             {@link EntityManager}
     */
    public final T buildAndSave() {
        ensureEntityManager();
        try {
            final T obj = build();
            entityManager.persist(obj);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns an integer value from an static counter.
     *
     * @return value of the static counter.
     */
    protected final int getId() {
        return count++;
    }

}

