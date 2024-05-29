package dao.impl;

import dao.ProductDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import models.Employee;
import models.Product;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ProductDaoImpl extends UnicastRemoteObject implements ProductDao {
    private static final long serialVersionUID = 3511774649497201508L;
    private EntityManager em;

    public ProductDaoImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    @Override
    public Product getProductById(String id) throws RemoteException {
        return em.find(Product.class, id);
    }

    @Override
    public List<Product> getProductByText(String text) throws RemoteException {
        return em.createQuery("SELECT p FROM Product p WHERE p.productId LIKE :text OR p.productName LIKE :text ", Product.class)
                .setParameter("text", "%" + text + "%") // %text% for similarity
                .getResultList();
    }

    @Override
    public boolean updateProduct(String id, int quantity) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.createQuery("UPDATE Product p SET p.quantity = :quantity WHERE p.id = :productId")
                    .setParameter("productId", id)
                    .setParameter("quantity", quantity)
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
