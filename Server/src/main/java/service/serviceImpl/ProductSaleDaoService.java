package service.serviceImpl;

import models.ProductSale;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductSaleDaoService extends Remote {
    List<ProductSale> getAllAProductSale() throws RemoteException;

    boolean addProductSale(ProductSale productSale) throws RemoteException;

    boolean deleteProductSale(String productSale) throws RemoteException;

    ProductSale getProductSale(String productSaleId) throws RemoteException;

    List<ProductSale> getProductSaleByPromotionId(String productSaleId) throws RemoteException;

    List<ProductSale> findProductSaleByText(String text) throws RemoteException;

    boolean deleteProductSaleByPromotionId(String id) throws RemoteException;
}
