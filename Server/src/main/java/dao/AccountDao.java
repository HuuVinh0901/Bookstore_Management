package dao;

import models.Account;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AccountDao extends Remote {
    boolean addAccount(Account account) throws RemoteException;

    boolean deleteAccount(String accountDelete) throws RemoteException;

    boolean updateAccount(Account account) throws RemoteException;

    List<Account> getAccounts() throws RemoteException;

    Account getAccountById(String id) throws RemoteException;

//    Account updaytePassword(String id, String newPass) throws RemoteException;

    /*
     * find password by employeeId
     * */
    String findPasswordByEmployeeId(String employeeId) throws RemoteException;

    boolean updatePassword(String id, String passNew) throws RemoteException;

}
