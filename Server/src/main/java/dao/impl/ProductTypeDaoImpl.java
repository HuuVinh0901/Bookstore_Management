/*
 * @ (#) .java   1.0    05/04/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */

package dao.impl;/*
 * @description:
 * @author:     Hoang Le
 * @date:       05/04/2024
 * @version:    1.0
 */

import dao.ProductTypeDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import models.ProductType;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;

public class ProductTypeDaoImpl extends UnicastRemoteObject implements ProductTypeDao {
    private static final long serialVersionUID = -8536969646252070294L;
    private EntityManager em;

    public ProductTypeDaoImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    @Override
    public List<ProductType> getAllProductType() throws RemoteException {
        try {
            if (!em.isOpen()) {
                throw new IllegalStateException("EntityManager is closed");
            }

            String hql = "FROM ProductType";
            TypedQuery<ProductType> query = em.createQuery(hql, ProductType.class);
            return query.getResultList();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    @Override
    public ProductType getProductTypeById(String id) throws RemoteException {
        try {
            if (!em.isOpen()) {
                throw new IllegalStateException("EntityManager is closed");
            }
            return em.find(ProductType.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addProductType(ProductType productType) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            if (!tr.isActive()) {
                tr.begin();
            }
            if (!em.contains(productType)) {
                productType = em.merge(productType);
            }
            em.persist(productType);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProductType(ProductType productType) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            tr.begin();
            ProductType p = em.find(ProductType.class, productType.getProductTypeId());
            if (p == null) {
                return false;
            }
            p.setProductTypeName(productType.getProductTypeName());
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean deleteProductType(String id) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            tr.begin();
            ProductType p = em.find(ProductType.class, id);
            if (p == null) {
                return false;
            }
            em.remove(p);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getLastestProductType() throws RemoteException {
        List<ProductType> productTypes = null;
        try {
            if (!em.isOpen()) {
                throw new IllegalStateException("EntityManager is closed");
            }
            String hql = "SELECT p.productTypeId FROM ProductType p ORDER BY p.productTypeId DESC";
            TypedQuery<String> query = em.createQuery(hql, String.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkProductTypeExist(String id) throws RemoteException {
        ProductType productType = em.find(ProductType.class, id);
        if (productType != null) {
            return true;
        }
        return false;
    }
}
