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

import models.Supplier;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface SupplierDao extends Remote {
    List<Supplier> getAllSuppliers() throws RemoteException;

    boolean addSupplier(Supplier supplier) throws RemoteException;

    boolean updateSupplier(Supplier supplier) throws RemoteException;

    boolean deleteSupplier(String id) throws RemoteException;

    boolean checkSupplierId(String id) throws RemoteException;

    List<Supplier> getLatestSupplierId() throws RemoteException;


}
