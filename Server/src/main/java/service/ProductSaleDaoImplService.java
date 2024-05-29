package service;

import dao.ProductSaleDao;
import dao.impl.ProductSaleDaoImpl;
import models.Account;
import models.ProductSale;
import service.serviceImpl.MerchandiseDaoService;
import service.serviceImpl.ProductSaleDaoService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ProductSaleDaoImplService extends UnicastRemoteObject implements ProductSaleDaoService {
    private final ProductSaleDao productSaleDao = new ProductSaleDaoImpl();

    public ProductSaleDaoImplService() throws RemoteException {
    }

    public List<ProductSale> getAllAProductSale() throws RemoteException {
        return productSaleDao.getProductSale();
    }

    public boolean addProductSale(ProductSale productSale) throws RemoteException {
        return productSaleDao.addProductSale(productSale);
    }


    public boolean deleteProductSale(String productSale) throws RemoteException {
        return productSaleDao.deleteProductSale(productSale);
    }

    public ProductSale getProductSale(String productSaleId) throws RemoteException {
        return productSaleDao.getProductSaleById(productSaleId);
    }

    public List<ProductSale> getProductSaleByPromotionId(String productSaleId) throws RemoteException {
        return productSaleDao.getProductSaleByPromotionId(productSaleId);
    }

    public List<ProductSale> findProductSaleByText(String text) throws RemoteException {
        return productSaleDao.findProductSaleByText(text);
    }

    public boolean deleteProductSaleByPromotionId(String id) throws RemoteException {
        return productSaleDao.deleteProductSaleByPromotionId(id);
    }
}
