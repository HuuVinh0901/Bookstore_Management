package dao.impl;

import dao.ProductSaleDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import models.ProductSale;
import util.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ProductSaleDaoImpl extends UnicastRemoteObject implements ProductSaleDao {
    private static final long serialVersionUID = -7661189018618086279L;
    private EntityManager em;

    public ProductSaleDaoImpl() throws RemoteException {
        em = HibernateUtil.getInstance().getEntityManager();
    }

    @Override
    public List<ProductSale> getProductSale() throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            if (!tr.isActive()) {
                tr.begin();
            }
            List<ProductSale> productSales = em.createQuery("FROM ProductSale ", ProductSale.class).getResultList();
            tr.commit();
            return productSales;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addProductSale(ProductSale productSale) throws RemoteException {
        EntityTransaction entityTransaction = em.getTransaction();
        try {
            entityTransaction.begin();
            em.persist(productSale);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProductSale(String id) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.createQuery("DELETE FROM ProductSale ps WHERE ps.product.id= :id")
                    .setParameter("id", id).executeUpdate();
            ;
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProductSaleByPromotionId(String id) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            em.createQuery("DELETE FROM ProductSale ps WHERE ps.promotion.id= :id")
                    .setParameter("id", id)
                    .executeUpdate();
            tr.commit();
            return true;
        } catch (Exception e) {
            if (tr.isActive()) {
                tr.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ProductSale getProductSaleById(String id) throws RemoteException {
        EntityTransaction tr = em.getTransaction();
        try {
            return em.createQuery("SELECT ps FROM ProductSale ps WHERE ps.product.id =:id ", ProductSale.class)
                    .setParameter("id", id) // %text% for similarity
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ProductSale> getProductSaleByPromotionId(String id) throws RemoteException {
        return em.createQuery("SELECT d FROM ProductSale d WHERE d.promotion.id=:id", ProductSale.class)
                .setParameter("id", id) // %text% for similarity
                .getResultList();
    }

    @Override
    public List<ProductSale> findProductSaleByText(String text) throws RemoteException {
        return em.createQuery("SELECT p from ProductSale p where p.promotion.id LIKE:text OR p.product.id LIKE :text OR p.product.productName LIKE :text", ProductSale.class)
                .setParameter("text", "%" + text + "%") // %text% for similarity
                .getResultList();
    }
}
