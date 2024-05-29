package service.serviceImpl;

import models.Account;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AccountDaoService extends Remote {
    List<Account> getAllAccount() throws RemoteException;

    boolean addAccount(Account account) throws RemoteException;

    boolean updateAccount(Account account) throws RemoteException;

    boolean deleteAccount(String id) throws RemoteException;

    Account getAccountById(String id) throws RemoteException;

    String findPasswordByEmployeeId(String employeeId) throws RemoteException;

    boolean updatePassword(String employeeId, String passNew) throws RemoteException;

}
