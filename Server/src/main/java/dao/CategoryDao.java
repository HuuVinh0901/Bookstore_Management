/*
 * @ (#) .java   1.0    03/04/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */

package dao;/*
 * @description:
 * @author:     Hoang Le
 * @date:       03/04/2024
 * @version:    1.0
 */

import models.Category;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CategoryDao extends Remote {
    List<Category> getAllCategory() throws RemoteException;

    boolean addCategory(Category category) throws RemoteException;

    boolean updateCategory(Category category) throws RemoteException;

    boolean deleteCategory(String idCategory) throws RemoteException;

    boolean checkIdCategory(String idCategory) throws RemoteException;

    List<String> getLatestCategoryID() throws RemoteException;

    boolean decreaseNumberOfCategory(String idCategory) throws RemoteException;

    boolean increaseNumberOfCategory(String idCategory) throws RemoteException;


}
