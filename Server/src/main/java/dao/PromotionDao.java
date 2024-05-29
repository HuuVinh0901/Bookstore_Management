package dao;

import models.Promotion;
import models.Role;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PromotionDao extends Remote {
    boolean addPromotion(Promotion promotion) throws RemoteException;

    boolean updatePromotion(Promotion promotion) throws RemoteException;

    boolean deletePromotion(String id) throws RemoteException;

    List<Promotion> getAllPromotion() throws RemoteException;

    List<Promotion> findPromotionByText(String text) throws RemoteException;

    Promotion getPromotionById(String id) throws RemoteException;

}
