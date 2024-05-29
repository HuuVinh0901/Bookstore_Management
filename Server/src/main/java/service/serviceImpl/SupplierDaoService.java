package service.serviceImpl;

import models.Supplier;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SupplierDaoService extends Remote {

    List<Supplier> getAllSuppliers() throws RemoteException;

    boolean addSupplier(Supplier supplier) throws RemoteException;

    boolean updateSupplier(Supplier supplier) throws RemoteException;

    boolean deleteSupplier(String id) throws RemoteException;

    boolean checkSupplierId(String id) throws RemoteException;

    List<Supplier> getLatestSupplierId() throws RemoteException;
}
