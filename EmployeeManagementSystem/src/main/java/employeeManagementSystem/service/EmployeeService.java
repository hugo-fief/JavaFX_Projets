package employeeManagementSystem.service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import employeeManagementSystem.bdd.HibernateUtil;
import employeeManagementSystem.model.Employee;

/**
 * Service class for Employee-related operations.
 */
public class EmployeeService {

    public List<Employee> getAllEmployees() {
        try (Session session = HibernateUtil.getInstance().getSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        }
    }

    public void saveOrUpdateEmployee(Employee employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int employeeID) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getInstance().getSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, employeeID);
            if (employee != null) {
                session.delete(employee);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Employee findEmployeeById(int employeeID) {
        try (Session session = HibernateUtil.getInstance().getSession()) {
            return session.get(Employee.class, employeeID);
        }
    }

    public List<Employee> getEmployeesByDepartmentName(String departmentName) {
        try (Session session = HibernateUtil.getInstance().getSession()) {
            return session.createQuery(
                    "FROM Employee e WHERE e.departmentID = " +
                    "(SELECT d.departmentID FROM Department d WHERE d.departmentName = :deptName)", Employee.class)
                .setParameter("deptName", departmentName)
                .list();
        }
    }

    public List<Employee> searchEmployeesByName(String name) {
        try (Session session = HibernateUtil.getInstance().getSession()) {
            return session.createQuery("FROM Employee e WHERE e.name LIKE :name", Employee.class)
                .setParameter("name", "%" + name + "%")
                .list();
        }
    }
}
