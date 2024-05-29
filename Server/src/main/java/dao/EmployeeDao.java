package dao;


import models.Employee;
import models.Role;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface EmployeeDao extends Remote {
    List<Employee> getAllEmployees() throws RemoteException;

    boolean addEmployee(Employee employee) throws RemoteException;

    boolean updateEmployee(Employee employee) throws RemoteException;

    boolean deleteEmployee(String empId) throws RemoteException;

    List<Employee> findEmployeeByText(String text) throws RemoteException;

    List<Employee> findEmployeeByRoleCode(int roleCode) throws RemoteException;

    Employee findEmployeeById(String empId) throws RemoteException;
}
