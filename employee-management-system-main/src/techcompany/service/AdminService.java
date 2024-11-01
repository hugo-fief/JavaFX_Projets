package techcompany;

import com.techcompany.model.Admin;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Service class for Admin-related operations.
 */
public class AdminService {

    private boolean isValid(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public Admin login(@NotBlank @Pattern(regexp = "^[a-zA-Z0-9_]+$") String username, @NotBlank String password) {
        if (!isValid(username) || !isValid(password)) {
            throw new IllegalArgumentException("Username or password is invalid.");
        }

        Transaction transaction = null;
        Admin admin = null;

        try (Session session = HibernateUtil.getInstance().getSession()) {
            transaction = session.beginTransaction();
            admin = session.createQuery("FROM Admin WHERE username = :username AND password = :password", Admin.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return admin;
    }
}
