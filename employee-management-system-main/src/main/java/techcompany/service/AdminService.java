package techcompany.service;

import techcompany.model.Admin;
import techcompany.bdd.HibernateUtil;
import techcompany.bdd.SessionData;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AdminService {

    /**
     * Authenticates the user by username and password.
     * Stores the session information in SessionData if successful.
     * 
     * @param username The username of the admin.
     * @param password The password of the admin.
     * @return The authenticated Admin object, or null if authentication fails.
     */
    public Admin login(@NotBlank @Pattern(regexp = "^[a-zA-Z0-9_]+$") String username, @NotBlank String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Username or password is invalid.");
        }

        Session session = HibernateUtil.getInstance().getSession();
        Transaction transaction = null;
        Admin admin = null;

        try {
            transaction = session.beginTransaction();
            admin = session.createQuery("FROM Admin WHERE username = :username AND password = :password", Admin.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();

            if (admin != null) {
                // Store session information
                SessionData.getInstance().setUsername(admin.getUsername());
                SessionData.getInstance().setUserRole(admin.getRole());
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return admin;
    }
}
