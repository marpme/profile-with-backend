package net.ziemers.swxercise.db.dao;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Der Producer stellt einen Entity Manager zur Verfügung, sofern die Anwendung im JEE-Container
 * des WildFly Servers läuft. Läuft sie im Testkontext in ihrem Weld-Server, funktioniert dies
 * naturgegeben nicht, und es wird der alternative Producer verwendet, der sich in der Klasse
 * "JpaTestUtils" im Paket "test.java.net.ziemers.swxercise.db.utils befindet".
 */
public class EntityManagerProducer {

    @Produces
    @PersistenceContext
    private EntityManager entityManager;

}
