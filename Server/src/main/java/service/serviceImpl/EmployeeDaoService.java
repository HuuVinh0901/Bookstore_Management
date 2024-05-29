package service.serviceImpl;

import models.Employee;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface EmployeeDaoService extends Remote {
    List<Employee> getAllEmployees() throws RemoteException;

    boolean addEmployee(Employee employee) throws RemoteException;

    boolean updateEmployee(Employee employee) throws RemoteException;

    boolean deleteEmployee(String id) throws RemoteException;

    List<Employee> findEmployeeByText(String text) throws RemoteException;

    List<Employee> findEmployeeByRoleCode(int roleCode) throws RemoteException;

    Employee findEmployeeById(String empId) throws RemoteException;
}
