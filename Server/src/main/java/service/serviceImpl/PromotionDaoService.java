package service.serviceImpl;

import models.Promotion;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PromotionDaoService extends Remote {

    List<Promotion> getAllPromotion() throws RemoteException;

    Promotion findPromotionById(String id) throws RemoteException;

    boolean addPromotion(Promotion promotion) throws RemoteException;

    boolean deletePromotion(String id) throws RemoteException;

    boolean updatePromotion(Promotion promotion) throws RemoteException;

    List<Promotion> findPromotionByText(String text) throws RemoteException;
}
