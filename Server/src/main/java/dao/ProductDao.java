package dao;

import models.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductDao extends Remote {
    Product getProductById(String id) throws RemoteException;

    List<Product> getProductByText(String text) throws RemoteException;

    boolean updateProduct(String id, int quantity) throws RemoteException;
}
