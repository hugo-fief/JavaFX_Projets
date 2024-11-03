package employeeManagementSystem.service;

import org.hibernate.Session;

import java.util.List;

import employeeManagementSystem.bdd.HibernateUtil;
import employeeManagementSystem.model.Department;

public class DepartmentService {

    public List<String> getDepartmentNames() {
        try (Session session = HibernateUtil.getInstance().getSession()) {
            return session.createQuery("SELECT departmentName FROM Department", String.class).list();
        }
    }

    public int getDepartmentID(String departmentName) {
        try (Session session = HibernateUtil.getInstance().getSession()) {
            Department department = session.createQuery("FROM Department WHERE departmentName = :name", Department.class)
                    .setParameter("name", departmentName)
                    .uniqueResult();
            return department != null ? department.getDepartmentID() : -1;
        }
    }
}
