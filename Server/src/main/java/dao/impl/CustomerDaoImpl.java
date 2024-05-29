package dao.impl;

import dao.CustomerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import models.Author;
import models.Category;
import models.Customer;
import util.HibernateUtil;
import util.log.Log;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static java.awt.SystemColor.text;

public class CustomerDaoImpl extends UnicastRemoteObject implements CustomerDao {
    private static final long serialVersionUID = 6696905471992303317L;
    private EntityManager em;

    public CustomerDaoImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    @Override
    public boolean addCustomer(Customer customer) throws RemoteException {
        EntityTransaction entityTransaction = em.getTransaction();
        try {
            entityTransaction.begin();
            em.persist(customer);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) throws RemoteException {
        EntityTransaction entityTransaction = em.getTransaction();
        ;
        try {
            entityTransaction.begin();
            em.merge(customer);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(String customerID) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Customer customer = em.find(Customer.class, customerID);
            em.remove(customer);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Customer> getAllCustomers() throws RemoteException {
        List<Customer> customers = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String hql = "FROM Customer";
            TypedQuery<Customer> query = em.createQuery(hql, Customer.class);
            customers = query.getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public List<Customer> findCustomerByText(String text) throws RemoteException {
        return em.createQuery("SELECT c FROM Customer c WHERE c.name LIKE :text OR c.phone LIKE :text OR c.email LIKE :text OR c.address LIKE :text OR c.idCustomer LIKE :text", Customer.class)
                .setParameter("text", "%" + text + "%") // %text% for similarity
                .getResultList();

    }

    @Override
    public Customer getCustomerByID(String customerID) throws RemoteException {
        return em.find(Customer.class, customerID);
    }

    @Override
    public Customer getCustomerByName(String name) throws RemoteException {
        return em.createQuery("SELECT c FROM Customer c WHERE c.name =:text", Customer.class)
                .setParameter("text", name) // %text% for similarity
                .getSingleResult();
    }
}
