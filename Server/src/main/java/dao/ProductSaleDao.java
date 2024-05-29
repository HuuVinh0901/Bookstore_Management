package dao;

import models.ProductSale;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductSaleDao extends Remote {
    List<ProductSale> getProductSale() throws RemoteException;

    boolean addProductSale(ProductSale productSale) throws RemoteException;

    boolean deleteProductSale(String id) throws RemoteException;

    boolean deleteProductSaleByPromotionId(String id) throws RemoteException;

    ProductSale getProductSaleById(String id) throws RemoteException;

    List<ProductSale> getProductSaleByPromotionId(String id) throws RemoteException;

    List<ProductSale> findProductSaleByText(String text) throws RemoteException;
}
