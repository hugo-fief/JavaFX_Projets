package techcompany;

import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AdminService {

    // Méthode pour valider l'entrée utilisateur (peut être adaptée selon les besoins)
    private boolean isValid(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public Admin login(@NotBlank @Pattern(regexp = "^[a-zA-Z0-9_]+$") String username, @NotBlank String password) {
        if (!isValid(username) || !isValid(password)) {
            throw new IllegalArgumentException("Username or password is invalid.");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Admin admin = null;

        try {
            transaction = session.beginTransaction();
            // Utilisation de paramètres pour éviter l'injection SQL
            admin = session.createQuery("FROM Admin WHERE username = :username AND password = :password", Admin.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
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

