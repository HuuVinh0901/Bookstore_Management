package dao;

import models.Book;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BookDao extends Remote {
    List<Book> getAllBook() throws RemoteException;

    boolean addBook(Book book) throws RemoteException;

    boolean updateBook(Book book) throws RemoteException;

    boolean deleteBook(String idBook) throws RemoteException;

    boolean checkIdBook(String idBook) throws RemoteException;

    List<String> getLatestBookID() throws RemoteException;

    List<Book> loadComboBoxByProductType(String productTypeId) throws RemoteException;

    List<Book> loadComboBoxByPublisher(String publisherId) throws RemoteException;

    List<Book> loadComboBoxByAuthor(String authorId) throws RemoteException;

    List<Book> loadComboBoxByCategory(String categoryId) throws RemoteException;

    List<Book> searchBook(String search) throws RemoteException;


    List<Book> findCategoryIdByName(String name) throws RemoteException;

    List<Book> findSupplierByName(String name) throws RemoteException;

    List<Book> findBookByProductType(String name) throws RemoteException;

    List<Book> findBookByAuthor(String name) throws RemoteException;
}