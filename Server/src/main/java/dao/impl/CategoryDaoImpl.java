/*
 * @ (#) .java   1.0    03/04/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */

package dao.impl;/*
 * @description:
 * @author:     Hoang Le
 * @date:       03/04/2024
 * @version:    1.0
 */

import dao.CategoryDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import models.Category;

import util.HibernateUtil;
import util.log.Log;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class CategoryDaoImpl extends UnicastRemoteObject implements CategoryDao {
    private static final long serialVersionUID = -884030185328911315L;
    private EntityManager em;

    public CategoryDaoImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    @Override
    public List<Category> getAllCategory() throws RemoteException {
        List<Category> categories = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String hql = "FROM Category";
            TypedQuery<Category> query = em.createQuery(hql, Category.class);
            categories = query.getResultList();
            tr.commit();
            Log.info("Retrieved all categories successfully");
        } catch (Exception e) {
            tr.rollback();
            Log.error("Error retrieving all categories");
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public boolean addCategory(Category category) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            if (!tr.isActive()) {
                tr.begin();
            }
            if (!em.contains(category)) {
                category = em.merge(category);
            }
            em.persist(category);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();

            tr.begin();
            em.persist(category);
            tr.commit();
            return true;
        }
    }

    @Override
    public boolean updateCategory(Category category) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            tr.begin();
            Category c = em.find(Category.class, category.getIdCategory());
            if (c == null) {
                Log.error("Category not found");
                return false;
            } else {
                c.setCategoryName(category.getCategoryName());
                c.setBookQuantity(category.getBookQuantity());
                c.setDescription(category.getDescription());
                em.merge(category);
                tr.commit();
                Log.info("Updated category successfully");
                return true;
            }
        } catch (Exception e) {
            tr.rollback();
            Log.error("Error updating category");
            return false;
        }
    }

    @Override
    public boolean deleteCategory(String idCategory) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            tr.begin();
            Category category = em.find(Category.class, idCategory);
            if (category == null) {
                Log.error("Category not found");
                return false;
            } else {
                em.remove(category);
                tr.commit();
                Log.info("Deleted category successfully");
                return true;
            }
        } catch (Exception e) {
            tr.rollback();
            Log.error("Error deleting category");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkIdCategory(String idCategory) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {

            if (!tr.isActive()) {
                tr.begin();
            }
            Category category = em.find(Category.class, idCategory);
            if (category == null) {
                Log.error("Category not found");
                return false;
            } else {
                Log.info("Category found");
                return true;
            }
        } catch (Exception e) {
            tr.rollback();
            Log.error("Error checking category");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getLatestCategoryID() throws RemoteException {
        String categoryID = null;
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String hql = "SELECT c.idCategory FROM Category c ORDER BY c.idCategory DESC";
            TypedQuery<String> query = em.createQuery(hql, String.class);
//            query.setMaxResults(1);
            List<String> categoryIDs = query.getResultList();
            tr.commit();
            Log.info("Retrieved latest category ID successfully");
            return categoryIDs;
        } catch (Exception e) {
            tr.rollback();
            Log.error("Error retrieving latest category ID");
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean decreaseNumberOfCategory(String idCategory) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            if (!tr.isActive()) {
                tr.begin();
            }
            Category category = em.find(Category.class, idCategory);
            if (category != null) {
                int bookQuantity = category.getBookQuantity();
                if (bookQuantity > 0) {
                    category.setBookQuantity(bookQuantity - 1);
                    tr.commit();
                    return true;
                }
            }
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean increaseNumberOfCategory(String idCategory) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            if (!tr.isActive()) {
                tr.begin();
            }
            Category category = em.find(Category.class, idCategory);
            category.setBookQuantity(category.getBookQuantity() + 1);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }


}
