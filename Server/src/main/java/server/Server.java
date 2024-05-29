package server;

import controller.MainController;
import controller.MainControllerInterface;
import dao.CustomerDao;
import dao.impl.CustomerDaoImpl;
import service.*;
import views.LoginView;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.awt.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    private static final String SERVER_IP = "192.168.89.111";
    private static final int PORT = 6969;
    private static final String URL = "rmi://" + SERVER_IP + ":" + PORT + "/";

    public static void main(String[] args) throws NamingException, RemoteException {

        Context context = new InitialContext();
        MainControllerInterface mainController = new MainController(
                new AuthorDaoImplService(),
                new CategoryDaoImplService(),
                new ProductTypeDaoImplService(),
                new SupplierDaoImplService(),
                new BookDaoImplService(),
                new MerchandiseDaoImplService(),
                new CustomerDaoImplService(),
                new EmployeeDaoImplService(),
                new AccountDaoImplService(),
                new RoleDaoImplService(),
                new PromotionDaoImplService(),
                new ProductSaleDaoImplService(),
                new ProductDaoImplService(),
                new SaleManagementDaoService()
        );

        LocateRegistry.createRegistry(PORT);
        context.rebind(URL + "mainController", mainController);
        System.out.println("Server is ready.");
        System.out.println("Waiting for clients...");
        EventQueue.invokeLater(() -> {
            try {
                LoginView loginView = new LoginView(mainController);
                loginView.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
