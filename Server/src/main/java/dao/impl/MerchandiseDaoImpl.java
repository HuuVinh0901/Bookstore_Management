package dao.impl;

import dao.MerchandiseDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import models.*;
import util.HibernateUtil;
import util.ProductStatusEnum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class MerchandiseDaoImpl extends UnicastRemoteObject implements MerchandiseDao {
    private static final long serialVersionUID = -8628992639494711232L;
    private EntityManager em;

    public MerchandiseDaoImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    @Override
    public boolean addMerchandise(Merchandise merchandise) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            if (!tr.isActive()) tr.begin();
            if (em.contains(merchandise)) em.merge(merchandise);
            else em.persist(merchandise);
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMerchandise(Merchandise merchandise) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            if (!tr.isActive()) tr.begin();
            em.merge(merchandise);
            tr.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMerchandise(String id) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            if (!tr.isActive()) tr.begin();
            Merchandise merchandise = em.find(Merchandise.class, id);
            if (merchandise != null) {
                em.remove(merchandise);
                tr.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkIdExist(String id) throws RemoteException {
        try {
            Merchandise merchandise = em.find(Merchandise.class, id);
            if (merchandise != null) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //        @Override
//    public List<Merchandise> getAllMerchandise() {
//        try {
//            String hql = "SELECT m, p.productTypeName, s.supplierName  " +
//                    "FROM Merchandise m " +
//                    "JOIN m.productTypeId p " +
//                    "JOIN m.supplierId s";
//
//            List<Object[]> resultList = em.createQuery(hql).getResultList();
//            List<Merchandise> merchandiseList = new ArrayList<>();
//
//            for (Object[] result : resultList) {
//                Merchandise merchandise = (Merchandise) result[0];
//                String productTypeName = (String) result[1];
//                String supplierName = (String) result[2];
//                merchandise.getProductTypeId().setProductTypeName(productTypeName);
//                merchandise.getSupplierId().setSupplierName(supplierName);
//                merchandiseList.add(merchandise);
//            }
//            return merchandiseList;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    @Override
    public List<Merchandise> getAllMerchandise() throws RemoteException {
        try {
            String hql = "SELECT m, p.productTypeName AS productTypeName, s.supplierName AS supplierName " +
                    "FROM Merchandise m " +
                    "JOIN FETCH m.productTypeId p " +
                    "JOIN FETCH m.supplierId s";

            TypedQuery<Object[]> query = em.createQuery(hql, Object[].class);
            List<Object[]> resultList = query.getResultList();
            List<Merchandise> merchandiseList = new ArrayList<>();

            for (Object[] result : resultList) {
                Merchandise merchandise = (Merchandise) result[0];
                String productTypeName = (String) result[1];
                String supplierName = (String) result[2];
                merchandise.getProductTypeId().setProductTypeName(productTypeName);
                merchandise.getSupplierId().setSupplierName(supplierName);
                merchandiseList.add(merchandise);
            }
            return merchandiseList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public List<Merchandise> getAllMerchandise() throws RemoteException {
//        EntityTransaction tr = null;
//        try {
//            tr = em.getTransaction();
//            tr.begin();
//            List<Merchandise> merchandiseList = em.createQuery("FROM Merchandise", Merchandise.class).getResultList();
//            tr.commit();
//            return merchandiseList;
//        } catch (Exception e) {
//            tr.rollback();
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public List<Merchandise> findSupplierByNameMerchandise(String name) throws RemoteException {
        try {
            if (!em.isOpen()) {
                throw new IllegalStateException("EntityManager is closed");
            }
            String hql = "SELECT m FROM Merchandise m WHERE m.supplierId.supplierName = :name";
            List<Merchandise> merchandiseList = em.createQuery(hql, Merchandise.class)
                    .setParameter("name", name)
                    .getResultList();
            return merchandiseList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Merchandise> findProductTypeMerchandise(String name) throws RemoteException {
        try {
            if (!em.isOpen()) {
                throw new IllegalStateException("EntityManager is closed");
            }
            String hql = "SELECT m FROM Merchandise m WHERE m.productTypeId.productTypeName = :name";
            List<Merchandise> merchandiseList = em.createQuery(hql, Merchandise.class)
                    .setParameter("name", name)
                    .getResultList();
            return merchandiseList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
