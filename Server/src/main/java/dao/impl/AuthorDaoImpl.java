/*
 * @ (#) .java   1.0    04/04/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */

package dao.impl;/*
 * @description:
 * @author:     Hoang Le
 * @date:       04/04/2024
 * @version:    1.0
 */

import dao.AuthorDao;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import models.Author;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AuthorDaoImpl extends UnicastRemoteObject implements AuthorDao {
    private EntityManager em;

    public AuthorDaoImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    @Override
    public List<Author> getAllAuthor() throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            if (!tr.isActive()) {
                tr.begin();
            }
            List<Author> authors = em.createQuery("FROM Author", Author.class).getResultList();
            tr.commit();
            return authors;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addAuthor(Author author) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            if (!tr.isActive()) {
                tr.begin();
            }
            if (!em.contains(author)) {
                author = em.merge(author);
            }

            em.persist(author);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAuthor(Author author) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            if (!tr.isActive()) {
                tr.begin();
            }
            em.merge(author);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAuthor(String idAuthor) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Author author = em.find(Author.class, idAuthor);
            em.remove(author);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkIdAuthor(String idAuthor) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            Author author = em.find(Author.class, idAuthor);
            if (author == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Author> getLatestAuthorID() throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            String hql = "FROM Author ORDER BY authorId DESC";
            List<Author> authors = em.createQuery(hql, Author.class).getResultList();
            tr.commit();
            return authors;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean decreaseNumberOfBooks(String idAuthor) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            if (!tr.isActive()) {
                tr.begin();
            }
            Author author = em.find(Author.class, idAuthor);
            if (author != null) {
                int numberOfWorks = author.getNumberOfWorks();
                if (numberOfWorks > 0) {
                    author.setNumberOfWorks(numberOfWorks - 1);
                    em.merge(author);
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
    public boolean increaseNumberOfBooks(String idAuthor) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            if (!tr.isActive()) {
                tr.begin();
            }
            Author author = em.find(Author.class, idAuthor);
            author.setNumberOfWorks(author.getNumberOfWorks() + 1);
            em.merge(author);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
        }
        return false;
    }
}
