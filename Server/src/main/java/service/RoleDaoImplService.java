package service;

import dao.RoleDao;
import dao.impl.RoleDaoImpl;
import models.Role;
import service.serviceImpl.PromotionDaoService;
import service.serviceImpl.RoleDaoService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImplService extends UnicastRemoteObject implements RoleDaoService {
    RoleDaoImpl roleDao = new RoleDaoImpl();

    public RoleDaoImplService() throws RemoteException {
    }

    public List<Role> getAllRole() throws RemoteException {
        return roleDao.getAllRole();
    }

    public boolean addRole(Role role) throws RemoteException {
        return roleDao.addRole(role);
    }

    public boolean deleteRole(String id) throws RemoteException {
        return roleDao.deleteRole(id);
    }

    public Role findRoleByText(String text) throws RemoteException {
        return roleDao.findRoleByText(text);
    }

    public boolean updateRole(String id) throws RemoteException {
        return roleDao.updateRole(id);
    }

    public List<Role> getRolesByRoleCode(int roleCode) throws RemoteException {
        return roleDao.getRolesByRoleCode(roleCode);
    }
}
