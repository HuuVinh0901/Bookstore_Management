package dao.impl;

import dao.RoleDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import models.Customer;
import models.Role;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl extends UnicastRemoteObject implements RoleDao {
    private static final long serialVersionUID = -5636520417145561561L;
    private EntityManager em;

    public RoleDaoImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    @Override
    public boolean addRole(Role role) throws RemoteException {
        EntityTransaction entityTransaction = em.getTransaction();
        try {
            entityTransaction.begin();

            Query query = em.createQuery("SELECT r FROM Role r WHERE LOWER(r.roleName) = LOWER(:roleName)");
            query.setParameter("roleName", role.getRoleName());
            List<Role> existingRoles = query.getResultList();
            if (existingRoles.isEmpty()) {
                if (!em.contains(role)) {
                    role = em.merge(role);
                }
                em.persist(role);
                entityTransaction.commit();
                return true;
            } else {
                System.out.println("Role with name '" + role.getRoleName() + "' already exists.");
            }
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean deleteRole(String roleId) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Role role = em.find(Role.class, roleId);
            em.remove(role);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Role> getAllRole() throws RemoteException {
        List<Role> roles = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String hql = "FROM Role";
            TypedQuery<Role> query = em.createQuery(hql, Role.class);
            roles = query.getResultList();
            tr.commit();
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return roles;
    }

    public Role findRoleByText(String text) throws RemoteException {
        return em.createQuery("SELECT r from Role r where r.roleName =:text", Role.class)
                .setParameter("text", text) // %text% for similarity
                .getSingleResult();
    }

    @Override
    public boolean updateRole(String id) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Role role = em.find(Role.class, id);
            em.merge(role);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Role> getRolesByRoleCode(int roleCode) throws RemoteException {
        List<Role> roles = new ArrayList<>();
        EntityTransaction tr = em.getTransaction();
        try {
            if (!tr.isActive()) {
                tr.begin();
            }

            String hql = "FROM Role r WHERE r.roleCode = :roleCode";
            TypedQuery<Role> query = em.createQuery(hql, Role.class);
            query.setParameter("roleCode", roleCode);
            roles = query.getResultList();

            if (tr.isActive()) {
                tr.commit();
            }
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return roles;
    }

}
