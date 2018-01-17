package net.ziemers.swxercise.lg.model.user;

import javax.persistence.*;

import net.ziemers.swxercise.db.BaseEntity;

/**
 *  Stellt die Stammdaten eines Benutzers zur Verfügung. Nutzt hierbei verschiedene Subklassen.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Trade.findById", query = "SELECT u FROM Trade u WHERE u.id = :id"),
        @NamedQuery(name = "Trade.findByTitle", query = "SELECT u FROM Trade u WHERE lower(u.title) = lower(:tradetitle)")})
public class Trade extends BaseEntity {

    private String title;

    private String description;

    private User creator;

    public Trade() {
        super();
    }

    public Trade(String title, String description, User creator) {
        this.title = title;
        this.description = description;
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Trade withCreator(final User user) {
        setCreator(user);
        return this;
    }


}
