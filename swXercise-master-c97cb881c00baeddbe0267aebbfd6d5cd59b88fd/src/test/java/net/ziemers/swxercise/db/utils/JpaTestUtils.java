package net.ziemers.swxercise.db.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IMetadataHandler;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.internal.SessionImpl;
import org.jglue.cdiunit.ProducesAlternative;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stellt nützliche Funktionalität für JUnit-Tests zur Verfügung.
 */
public class JpaTestUtils {

    private static final Logger logger = LoggerFactory.getLogger(JpaTestUtils.class.getName());

    private static final String PERSISTENCE_UNIT_TEST = "swXerciseTestPU";

    private static final String NET_ZIEMERS_SWXERCISE_PKG = "net/ziemers/swxercise/testdata/";

    private static EntityManagerFactory emf = null;

    private static EntityManager em = null;

    private EntityTransaction tx;

    /**
     * Produziert eine EntityManagerFactory und einen EntityManager und speichert sie als static (Singleton),
     * damit sie nicht immer neu erstellt werden für jeden Test.
     *
     * @return EntityManager der erzeugt wurde
     */
    @Produces
    @ProducesAlternative
    public EntityManager getEm() {
        if (emf == null) {
            synchronized (EntityManagerFactory.class) {
                if (emf == null) {
                    emf = createEntityManagerFactory();
                }
            }
        }
        if (emf != null && em == null) {
            synchronized (EntityManager.class) {
                if (emf != null && em == null) {
                    em = emf.createEntityManager();
                }
            }
        }
        return em;
    }

    @SuppressWarnings("unused")
    protected void clearEm() {
        em = null;
    }

    /**
     * Private Methode, die eine EntityManagerFactory erzeugen kann. Verwendet eine im Home-Verzeichnis des
     * aktuellen Benutzers abgelegte Datei persistence.properties, um die Defaults aus der persistence.xml
     * zu überschreiben.
     *
     * @return neu erstellte {@link EntityManagerFactory}
     */
    private static EntityManagerFactory createEntityManagerFactory() {

        String homePath = System.getProperty("user.home");
        String swxercisePersistenceXmlPath = homePath + File.separator + ".swXercise" + File.separator + "persistence.properties";
        File swxercisePersistenceXmlFile = new File(swxercisePersistenceXmlPath);
        EntityManagerFactory emf = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(swxercisePersistenceXmlFile);
            Properties overrideProperties = new Properties();
            overrideProperties.load(fis);
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_TEST, overrideProperties);
        } catch (Exception e) {
            // logger.info("Persistence properties will not be overriden because of following error:", e);
            logger.info("Persistence properties will not be overriden");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_TEST);
        }

        return emf;
    }

    protected void txBegin() {
        tx = getEm().getTransaction();
        tx.begin();
    }

    protected void txCommit() {
        tx.commit();
    }

    @SuppressWarnings("unused")
    protected void txRollback() {
        tx.rollback();
    }

    /**
     * Hilfsmethode, um ein als XML vorliegendes Dataset zu laden.
     *
     * @param resourceName Dateiname, welches als Ressource gesucht wird
     * @return das geladene Dataset
     * @throws Exception wenn beim Behandeln der Dateien eine Exception aufgetreten ist
     */
    private static IDataSet readDataset(String resourceName) throws Exception {
        IDataSet dataSet = null;
        InputStream dtdStream = null;
        InputStream resourceAsStream = null;
        try {
            FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
            dtdStream = JpaTestUtils.class.getClassLoader().getResourceAsStream(NET_ZIEMERS_SWXERCISE_PKG + "empty/entity_versioning.dtd");
            // flatXmlDataSetBuilder.setMetaDataSetFromDtd(dtdStream);
            flatXmlDataSetBuilder.setColumnSensing(true);

            resourceAsStream = JpaTestUtils.class.getClassLoader().getResourceAsStream(NET_ZIEMERS_SWXERCISE_PKG + resourceName);
            dataSet = flatXmlDataSetBuilder.build(resourceAsStream);
        } catch (Exception e) {
            logger.warn("Exception while reading test data definitions file named \"{}\". If your tests fail, check existence of file!", resourceName);
            // throw e;
        } finally {
            if (dtdStream != null) {
                dtdStream.close();
            }
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
        }

        return dataSet;
    }

    /**
     * Initialisiert die Datenbank aus einer XML-Beschreibung heraus.
     *
     * @param resourceName der Name der XML-Datei
     * @throws Exception wenn dabei etwas schiefläuft
     */
    protected void initDbWith(String resourceName) throws Exception {
        final IDataSet dataset = readDataset(resourceName);
        if (dataset != null) {
            try {
                SessionImpl delegate = (SessionImpl) this.getEm().getDelegate();
                DatabaseConnection connection = new DatabaseConnection(delegate.connection());
                IMetadataHandler mysqlHandler = new MySqlMetadataHandler();
                IDataTypeFactory mySqlDataTypeFactory = new MySqlDataTypeFactory();
                connection.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, mysqlHandler);
                connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, mySqlDataTypeFactory);
                DatabaseOperation.INSERT.execute(connection, dataset);
            } catch (DatabaseUnitException qe) {
                logger.warn("Database entries already inserted. If your tests fail, check your sql inserts for constraint violations!");
                logger.warn(qe.getMessage());
                logger.warn(qe.getMessage(), qe);
                throw qe;
            }
        }
    }

    /**
     * Räumt die Datenbank mit DBUnit auf, um für jeden Test eine leere, saubere Datenbank zu haben.
     *
     * @throws Exception Wirft eventuelle SQL- und I/O-Exceptions
     */
    protected void cleanDb() throws Exception {
        String schema = getCurrentSchema();
        List<String> tables = getTableNamesToBeEmptied();
        getEm().getTransaction().begin();
        getEm().createNativeQuery("SET foreign_key_checks=0;").executeUpdate();
        for (String table : tables) {
            getEm().createNativeQuery(getTruncateStatement(schema, table)).executeUpdate();
        }
        getEm().createNativeQuery("SET foreign_key_checks=1;").executeUpdate();
        getEm().getTransaction().commit();
        getEm().clear();
    }

    private String getCurrentSchema() {
        String query = "SELECT DATABASE()";
        List<Object> result = executeReadQuery(query);
        if (result.isEmpty()) {
            return null;
        }
        Object object = result.get(0);
        if (object instanceof String) {
            return (String) object;
        }
        return null;
    }

    private List<String> getTableNamesToBeEmptied() throws Exception {
        return showTablesNotEmpty(getCurrentSchema());
    }

    private List<String> showTablesNotEmpty(String schema) throws Exception {
        StringBuilder query = new StringBuilder();
        query.append("SELECT table_name ");
        query.append("FROM information_schema.tables ");
        // query.append("WHERE table_rows > 0 ");
        // query.append(String.format("AND table_schema = '%s'", schema));
        query.append(String.format("WHERE table_schema = '%s'", schema));
        getSkippingTables().stream().forEach(table -> query.append(String.format("AND table_name != '%s'", table)));
        List<Object> result = executeReadQuery(query.toString());
        return result.stream().filter(o -> o instanceof String).map(o -> (String) o).collect(Collectors.toList());
    }

    /**
     * Verschiedene Tabellen sollen ignoriert werden.
     *
     * @return eine Liste der zu ignorierenden Tabellen
     */
    private List<String> getSkippingTables() {
        return Arrays.asList(
                "hibernate_sequence",
                "schema_version"); // FlywayDB Metatable
    }

    private List<Object> executeReadQuery(String queryString) {
        getEm().getTransaction().begin();
        Query query = getEm().createNativeQuery(queryString);
        @SuppressWarnings("unchecked")
        List<Object> result = query.getResultList();
        getEm().getTransaction().rollback();
        return result;
    }

    private String getTruncateStatement(String schema, String table) {
        return String.format("DELETE FROM %s.%s;", schema, table);
    }

}
