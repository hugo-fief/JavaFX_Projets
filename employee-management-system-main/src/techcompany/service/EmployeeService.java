package techcompany;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class EmployeeService {
	
    // Get all employees
    public List<Employee> getAllEmployees() {
        Session session = HibernateUtil.getInstance().getSessionFactory();
        List<Employee> employees = session.createQuery("FROM Employee", Employee.class).list();
        session.close();
        return employees;
    }

    // Save or update an employee
    public void saveOrUpdateEmployee(Employee employee) {
        Session session = HibernateUtil.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(employee);
        transaction.commit();
        session.close();
    }

    // Delete an employee by ID
    public void deleteEmployee(int employeeID) {
        Session session = HibernateUtil.getInstance().getSessionFactory();
        Transaction transaction = session.beginTransaction();
        Employee employee = session.get(Employee.class, employeeID);
        if (employee != null) {
            session.delete(employee);
        }
        transaction.commit();
        session.close();
    }

    // Find an employee by ID
    public Employee findEmployeeById(int employeeID) {
        Session session = HibernateUtil.getInstance().getSessionFactory();
        Employee employee = session.get(Employee.class, employeeID);
        session.close();
        return employee;
    }

    // Get employees by department name
    public List<Employee> getEmployeesByDepartmentName(String departmentName) {
        Session session = HibernateUtil.getInstance().getSessionFactory();
        List<Employee> employees = session.createQuery("FROM Employee e WHERE e.departmentID = " +
            "(SELECT d.departmentID FROM Department d WHERE d.departmentName = :deptName)", Employee.class)
            .setParameter("deptName", departmentName)
            .list();
        session.close();
        return employees;
    }

    // Search employees by name
    public List<Employee> searchEmployeesByName(String name) {
        Session session = HibernateUtil.getInstance().getSessionFactory();
        List<Employee> employees = session.createQuery("FROM Employee e WHERE e.name LIKE :name", Employee.class)
            .setParameter("name", "%" + name + "%")
            .list();
        session.close();
        return employees;
    }
}
