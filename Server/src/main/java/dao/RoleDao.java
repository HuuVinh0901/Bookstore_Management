package dao;

import models.Role;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RoleDao extends Remote {
    boolean addRole(Role role) throws RemoteException;

    boolean deleteRole(String roleId) throws RemoteException;

    List<Role> getAllRole() throws RemoteException;

    Role findRoleByText(String text) throws RemoteException;

    boolean updateRole(String id) throws RemoteException;

    List<Role> getRolesByRoleCode(int roleCode) throws RemoteException;

}
