/*
 * @ (#) .java   1.0    04/04/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */

package dao;/*
 * @description:
 * @author:     Hoang Le
 * @date:       04/04/2024
 * @version:    1.0
 */

import models.Author;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AuthorDao extends Remote {
    List<Author> getAllAuthor() throws RemoteException;

    boolean addAuthor(Author author) throws RemoteException;

    boolean updateAuthor(Author author) throws RemoteException;

    boolean deleteAuthor(String idAuthor) throws RemoteException;

    boolean checkIdAuthor(String idAuthor) throws RemoteException;

    List<Author> getLatestAuthorID() throws RemoteException;


    boolean decreaseNumberOfBooks(String idAuthor) throws RemoteException;

    boolean increaseNumberOfBooks(String idAuthor) throws RemoteException;
}
