/*
 * @ (#) .java   1.0    05/04/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */

package dao;/*
 * @description:
 * @author:     Hoang Le
 * @date:       05/04/2024
 * @version:    1.0
 */

import models.ProductType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ProductTypeDao extends Remote {
    List<ProductType> getAllProductType() throws RemoteException;

    ProductType getProductTypeById(String id) throws RemoteException;

    boolean addProductType(ProductType productType) throws RemoteException;

    boolean updateProductType(ProductType productType) throws RemoteException;

    boolean deleteProductType(String id) throws RemoteException;

    List<String> getLastestProductType() throws RemoteException;

    boolean checkProductTypeExist(String name) throws RemoteException;
}
