package dao.impl;

import dao.AccountDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import models.Account;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AccountDaoImpl extends UnicastRemoteObject implements AccountDao {
    private static final long serialVersionUID = -6583095858843327021L;
    private EntityManager em;

    public AccountDaoImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    @Override
    public boolean addAccount(Account account) throws RemoteException {
        EntityTransaction entityTransaction = em.getTransaction();
        try {
            entityTransaction.begin();
            em.persist(account);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAccount(String acountDelete) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Account account = em.find(Account.class, acountDelete);
            em.remove(account);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAccount(Account account) throws RemoteException {
        EntityTransaction entityTransaction = em.getTransaction();
        ;
        try {
            entityTransaction.begin();
            em.merge(account);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Account> getAccounts() throws RemoteException {
        List<Account> accounts = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String hql = "FROM Account ";
            TypedQuery<Account> query = em.createQuery(hql, Account.class);
            accounts = query.getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account getAccountById(String id) throws RemoteException {
        Account account = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String hql = "SELECT a " +
                    "FROM Account a " +
                    "JOIN FETCH a.employee e " +
                    "JOIN FETCH e.role r " +
                    "WHERE a.employee.idEmployee = :id";
            TypedQuery<Account> query = em.createQuery(hql, Account.class);
            query.setParameter("id", id);
            account = query.getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public String findPasswordByEmployeeId(String employeeId) throws RemoteException {
        String password = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String hql = "SELECT a.password " +
                    "FROM Account a " +
                    "WHERE a.employee.idEmployee = :id";
            TypedQuery<String> query = em.createQuery(hql, String.class);
            query.setParameter("id", employeeId);
            password = query.getSingleResult();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return password;
    }

    @Override
    public boolean updatePassword(String id, String passNew) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.createQuery("UPDATE Account p SET p.password = :passNew WHERE p.employee.id = :id")
                    .setParameter("id", id)
                    .setParameter("passNew", passNew)
                    .executeUpdate();
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

}
