package dao.impl;

import dao.EmployeeDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import models.Customer;
import models.Employee;
import models.Role;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;

public class EmployeeDaoImpl extends UnicastRemoteObject implements EmployeeDao {
    private static final long serialVersionUID = -7025849940149050174L;
    private EntityManager em;

    public EmployeeDaoImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    @Override
    public List<Employee> getAllEmployees() throws RemoteException {
        try {
            String hql = "FROM Employee";
            TypedQuery<Employee> query = em.createQuery(hql, Employee.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();

    }

    @Override
    public boolean addEmployee(Employee employee) throws RemoteException {
        EntityTransaction entityTransaction = em.getTransaction();
        try {
            entityTransaction.begin();
            em.persist(employee);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEmployee(Employee employee) throws RemoteException {
        EntityTransaction entityTransaction = em.getTransaction();
        ;
        try {
            entityTransaction.begin();
            em.merge(employee);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteEmployee(String empId) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Employee employee = em.find(Employee.class, empId);
            em.remove(employee);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Employee> findEmployeeByText(String text) throws RemoteException {
        return em.createQuery("SELECT e FROM Employee e WHERE e.name LIKE :text OR e.phone LIKE :text OR e.email LIKE :text OR e.address LIKE :text OR e.idEmployee LIKE :text", Employee.class)
                .setParameter("text", "%" + text + "%") // %text% for similarity
                .getResultList();
    }

    @Override
    public List<Employee> findEmployeeByRoleCode(int roleCode) throws RemoteException {
        try {
            String hql = "SELECT e " +
                    "FROM Employee e " +
                    "JOIN e.role r " +
                    "WHERE r.roleCode = :roleCode";
            TypedQuery<Employee> query = em.createQuery(hql, Employee.class);
            query.setParameter("roleCode", roleCode);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Employee findEmployeeById(String empId) throws RemoteException {
        try {
            return em.find(Employee.class, empId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
