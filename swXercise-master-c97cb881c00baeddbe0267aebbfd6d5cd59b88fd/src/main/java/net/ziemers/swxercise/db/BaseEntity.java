package net.ziemers.swxercise.db;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Diese Basisklasse wird von allen Entitäten verwendet.
 */
@MappedSuperclass
public class BaseEntity {

    protected Long id;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}