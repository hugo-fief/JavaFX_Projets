package techcompany;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Singleton utility class for creating and accessing the Hibernate SessionFactory.
 */
public class HibernateUtil {

    private static HibernateUtil instance;
    private SessionFactory sessionFactory;

    /**
     * Private constructor to initialize SessionFactory.
     */
    private HibernateUtil() {
        try {
            // Load the configuration from hibernate.cfg.xml
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed: " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Returns the singleton instance of HibernateUtil.
     *
     * @return the HibernateUtil instance
     */
    public static synchronized HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    /**
     * Returns the Hibernate SessionFactory.
     *
     * @return SessionFactory instance
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Shuts down the SessionFactory, releasing resources.
     */
    public void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
