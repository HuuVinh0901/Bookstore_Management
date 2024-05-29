package dao;

import models.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CustomerDao extends Remote {
    boolean addCustomer(Customer customer) throws RemoteException;

    boolean updateCustomer(Customer customer) throws RemoteException;

    boolean deleteCustomer(String customerID) throws RemoteException;

    List<Customer> getAllCustomers() throws RemoteException;

    List<Customer> findCustomerByText(String text) throws RemoteException;

    Customer getCustomerByID(String customerID) throws RemoteException;

    Customer getCustomerByName(String customerID) throws RemoteException;
}
