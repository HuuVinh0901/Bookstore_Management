package dao.impl;

import dao.BookDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import models.Book;
import models.Customer;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class BookDaoImpl extends UnicastRemoteObject implements BookDao {
    private static final long serialVersionUID = 8236370266805632047L;
    private EntityManager em;

    public BookDaoImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    @Override
    public List<Book> getAllBook() throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            tr.begin();
            List<Book> books = em.createQuery("FROM Book", Book.class).getResultList();
            tr.commit();
            return books;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addBook(Book book) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            if (!tr.isActive()) {
                tr.begin();
            }
            if (!em.contains(book)) {
                book = em.merge(book);
            }
            em.persist(book);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBook(Book book) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            tr.begin();
            em.merge(book);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBook(String idBook) throws RemoteException {
        EntityTransaction tr = null;
        try {
            tr = em.getTransaction();
            tr.begin();
            Book book = em.find(Book.class, idBook);
            em.remove(book);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkIdBook(String idBook) throws RemoteException {
        try {
            Book book = em.find(Book.class, idBook);
            if (book == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getLatestBookID() throws RemoteException {
        return null;
    }

    @Override
    public List<Book> loadComboBoxByProductType(String productTypeId) throws RemoteException {
        return null;
    }

    @Override
    public List<Book> loadComboBoxByPublisher(String publisherId) throws RemoteException {
        return null;
    }

    @Override
    public List<Book> loadComboBoxByAuthor(String authorId) throws RemoteException {
        return null;
    }

    @Override
    public List<Book> loadComboBoxByCategory(String categoryId) throws RemoteException {
        return null;
    }

    @Override
    public List<Book> searchBook(String search) throws RemoteException {
        return em.createQuery("SELECT b FROM Book b WHERE b.id LIKE :text OR b.productName LIKE :text OR b.productId LIKE :text ", Book.class)
                .setParameter("text", "%" + search + "%") // %text% for similarity
                .getResultList();
    }


    @Override
    public List<Book> findCategoryIdByName(String categoryName) throws RemoteException {
        try {
            if (!em.isOpen()) {
                throw new IllegalStateException("EntityManager is closed");
            }
            List<Book> books = em.createQuery("SELECT b FROM Book b WHERE b.categoryId.categoryName = :categoryName", Book.class)
                    .setParameter("categoryName", categoryName)
                    .getResultList();
            return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> findSupplierByName(String name) throws RemoteException {
        try {
            if (!em.isOpen()) {
                throw new IllegalStateException("EntityManager is closed");
            }
            String hql = "SELECT b FROM Book b WHERE b.supplierId.supplierName = :name";
            List<Book> books = em.createQuery(hql, Book.class)
                    .setParameter("name", name)
                    .getResultList();
            return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> findBookByProductType(String name) throws RemoteException {
        try {
            if (!em.isOpen()) {
                throw new IllegalStateException("EntityManager is closed");
            }
            String hql = "SELECT b FROM Book b WHERE b.productTypeId.productTypeName = :name";
            List<Book> books = em.createQuery(hql, Book.class)
                    .setParameter("name", name)
                    .getResultList();
            return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> findBookByAuthor(String name) throws RemoteException {
        try {
            if (!em.isOpen()) {
                throw new IllegalStateException("EntityManager is closed");
            }
            String hql = "SELECT b FROM Book b WHERE b.authorId.authorName = :name";
            List<Book> books = em.createQuery(hql, Book.class)
                    .setParameter("name", name)
                    .getResultList();
            return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
