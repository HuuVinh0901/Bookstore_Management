package service;

import dao.impl.CategoryDaoImpl;
import models.Category;
import models.Merchandise;
import service.serviceImpl.BookDaoService;
import service.serviceImpl.CategoryDaolService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class CategoryDaoImplService extends UnicastRemoteObject implements CategoryDaolService {
    private final CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();

    public CategoryDaoImplService() throws RemoteException {
    }

    public List<Category> getAllCategory() throws RemoteException {
        return categoryDaoImpl.getAllCategory();
    }

    public boolean addCategory(Category category) throws RemoteException {
        return categoryDaoImpl.addCategory(category);
    }

    public boolean updateCategory(Category category) throws RemoteException {
        return categoryDaoImpl.updateCategory(category);
    }

    public boolean deleteCategory(String idCategory) throws RemoteException {
        return categoryDaoImpl.deleteCategory(idCategory);
    }

    public boolean checkIdCategory(String idCategory) throws RemoteException {
        return categoryDaoImpl.checkIdCategory(idCategory);
    }

    public List<String> getLatestCategoryID() throws RemoteException {
        return categoryDaoImpl.getLatestCategoryID();
    }

    public boolean decreaseNumberOfCategory(String idCategory) throws RemoteException {
        return categoryDaoImpl.decreaseNumberOfCategory(idCategory);
    }

    public boolean increaseNumberOfCategory(String idCategory) throws RemoteException {
        return categoryDaoImpl.increaseNumberOfCategory(idCategory);
    }
}
