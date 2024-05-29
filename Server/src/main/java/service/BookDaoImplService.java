package service;

import dao.impl.BookDaoImpl;
import models.Book;
import service.serviceImpl.AuthorDaoService;
import service.serviceImpl.BookDaoService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class BookDaoImplService extends UnicastRemoteObject implements BookDaoService {
    private final BookDaoImpl bookDaoImpl = new BookDaoImpl();

    public BookDaoImplService() throws RemoteException {
    }

    public List<Book> getAllBook() throws RemoteException {
        return bookDaoImpl.getAllBook();
    }

    public boolean addBook(Book book) throws RemoteException {
        return bookDaoImpl.addBook(book);
    }

    public boolean updateBook(Book book) throws RemoteException {
        return bookDaoImpl.updateBook(book);
    }

    public boolean deleteBook(String idBook) throws RemoteException {
        return bookDaoImpl.deleteBook(idBook);
    }

    public boolean checkIdBook(String idBook) throws RemoteException {
        return bookDaoImpl.checkIdBook(idBook);
    }

    public List<String> getLatestBookID() throws RemoteException {
        return bookDaoImpl.getLatestBookID();
    }

    public List<Book> loadComboBoxByProductType(String productTypeId) throws RemoteException {
        return bookDaoImpl.loadComboBoxByProductType(productTypeId);
    }

    public List<Book> loadComboBoxByPublisher(String publisherId) throws RemoteException {
        return bookDaoImpl.loadComboBoxByPublisher(publisherId);
    }

    public List<Book> loadComboBoxByAuthor(String authorId) throws RemoteException {
        return bookDaoImpl.loadComboBoxByAuthor(authorId);
    }

    public List<Book> loadComboBoxByCategory(String categoryId) throws RemoteException {
        return bookDaoImpl.loadComboBoxByCategory(categoryId);
    }

    public List<Book> searchBook(String search) throws RemoteException {
        return bookDaoImpl.searchBook(search);
    }

    public List<Book> findCategoryIdByName(String name) throws RemoteException {
        return bookDaoImpl.findCategoryIdByName(name);
    }

    public List<Book> findSupplierByName(String name) throws RemoteException {
        return bookDaoImpl.findSupplierByName(name);
    }

    public List<Book> findBookByProductType(String name) throws RemoteException {
        return bookDaoImpl.findBookByProductType(name);
    }

    public List<Book> findBookByAuthor(String name) throws RemoteException {
        return bookDaoImpl.findBookByAuthor(name);
    }
}
