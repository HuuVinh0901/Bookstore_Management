package service.serviceImpl;

import models.ProductType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductTypeDaoService extends Remote {

    List<ProductType> getAllProductType() throws RemoteException;

    ProductType getProductTypeById(String id) throws RemoteException;

    boolean addProductType(ProductType productType) throws RemoteException;

    boolean updateProductType(ProductType productType) throws RemoteException;

    boolean deleteProductType(String id) throws RemoteException;

    List<String> getLastestProductType() throws RemoteException;

    boolean checkProductTypeExist(String id) throws RemoteException;
}
