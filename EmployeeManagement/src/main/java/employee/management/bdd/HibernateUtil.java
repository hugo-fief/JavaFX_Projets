package employee.management.bdd;

import org.hibernate.Session;
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
     * Returns a new Hibernate session.
     * 
     * @return Hibernate Session
     */
    public Session getSession() {
        return sessionFactory.openSession();
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
