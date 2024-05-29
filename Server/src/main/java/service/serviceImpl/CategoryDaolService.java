package service.serviceImpl;

import models.Category;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CategoryDaolService extends Remote {
    List<Category> getAllCategory() throws RemoteException;

    boolean addCategory(Category category) throws RemoteException;

    boolean updateCategory(Category category) throws RemoteException;

    boolean deleteCategory(String idCategory) throws RemoteException;

    boolean checkIdCategory(String idCategory) throws RemoteException;

    List<String> getLatestCategoryID() throws RemoteException;

    boolean decreaseNumberOfCategory(String idCategory) throws RemoteException;

    boolean increaseNumberOfCategory(String idCategory) throws RemoteException;

}
