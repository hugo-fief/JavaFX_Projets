package employeeManagementSystem.service;

import org.hibernate.Session;

import java.util.List;

import employeeManagementSystem.bdd.HibernateUtil;
import employeeManagementSystem.model.Designation;

public class DesignationService {

    public List<String> getDesignationNames() {
        try (Session session = HibernateUtil.getInstance().getSession()) {
            return session.createQuery("SELECT designationName FROM Designation", String.class).list();
        }
    }

    public int getDesignationID(String designationName) {
        try (Session session = HibernateUtil.getInstance().getSession()) {
            Designation designation = session.createQuery("FROM Designation WHERE designationName = :name", Designation.class)
                    .setParameter("name", designationName)
                    .uniqueResult();
            return designation != null ? designation.getDesignationID() : -1;
        }
    }
}
