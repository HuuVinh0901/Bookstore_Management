package service;

import dao.impl.AuthorDaoImpl;
import models.Author;
import service.serviceImpl.AccountDaoService;
import service.serviceImpl.AuthorDaoService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AuthorDaoImplService extends UnicastRemoteObject implements AuthorDaoService {
    private final AuthorDaoImpl authorDaoImpl = new AuthorDaoImpl();

    public AuthorDaoImplService() throws RemoteException {
    }


    public List<Author> getAllAuthor() throws RemoteException {
        return authorDaoImpl.getAllAuthor();
    }

    public boolean addAuthor(Author author) throws RemoteException {
        return authorDaoImpl.addAuthor(author);
    }

    public boolean updateAuthor(Author author) throws RemoteException {
        return authorDaoImpl.updateAuthor(author);
    }

    public boolean deleteAuthor(String idAuthor) throws RemoteException {
        return authorDaoImpl.deleteAuthor(idAuthor);
    }

    public boolean checkIdAuthor(String idAuthor) throws RemoteException {
        return authorDaoImpl.checkIdAuthor(idAuthor);
    }

    public List<Author> getLatestAuthorID() throws RemoteException {
        return authorDaoImpl.getLatestAuthorID();
    }

    public boolean decreaseNumberOfBooks(String idAuthor) throws RemoteException {
        return authorDaoImpl.decreaseNumberOfBooks(idAuthor);
    }

    public boolean increaseNumberOfBooks(String idAuthor) throws RemoteException {
        return authorDaoImpl.increaseNumberOfBooks(idAuthor);
    }
}
