package service.serviceImpl;

import models.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CustomerDaoService extends Remote {
    List<Customer> getAllCustomers() throws RemoteException;

    boolean addCustomer(Customer customer) throws RemoteException;

    boolean updateCustomer(Customer customer) throws RemoteException;

    boolean deleteCustomer(String id) throws RemoteException;

    List<Customer> findCustomerByText(String text) throws RemoteException;

    Customer findCustomerById(String id) throws RemoteException;
}
