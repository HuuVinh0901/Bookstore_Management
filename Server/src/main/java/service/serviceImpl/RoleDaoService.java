package service.serviceImpl;

import models.Role;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RoleDaoService extends Remote {
    List<Role> getAllRole() throws RemoteException;

    boolean addRole(Role role) throws RemoteException;

    boolean deleteRole(String id) throws RemoteException;

    Role findRoleByText(String text) throws RemoteException;

    boolean updateRole(String id) throws RemoteException;

    List<Role> getRolesByRoleCode(int roleCode) throws RemoteException;
}
