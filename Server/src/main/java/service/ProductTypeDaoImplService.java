package service;

import dao.impl.ProductTypeDaoImpl;
import models.ProductType;
import service.serviceImpl.ProductDaoService;
import service.serviceImpl.ProductTypeDaoService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ProductTypeDaoImplService extends UnicastRemoteObject implements ProductTypeDaoService {
    private final ProductTypeDaoImpl productTypeDaoImpl = new ProductTypeDaoImpl();

    public ProductTypeDaoImplService() throws RemoteException {
    }

    public List<ProductType> getAllProductType() throws RemoteException {
        return productTypeDaoImpl.getAllProductType();
    }

    public ProductType getProductTypeById(String id) throws RemoteException {
        return productTypeDaoImpl.getProductTypeById(id);
    }

    public boolean addProductType(ProductType productType) throws RemoteException {
        return productTypeDaoImpl.addProductType(productType);
    }

    public boolean updateProductType(ProductType productType) throws RemoteException {
        return productTypeDaoImpl.updateProductType(productType);
    }

    public boolean deleteProductType(String id) throws RemoteException {
        return productTypeDaoImpl.deleteProductType(id);
    }

    public List<String> getLastestProductType() throws RemoteException {
        return productTypeDaoImpl.getLastestProductType();
    }

    public boolean checkProductTypeExist(String id) throws RemoteException {
        return productTypeDaoImpl.checkProductTypeExist(id);
    }
}
