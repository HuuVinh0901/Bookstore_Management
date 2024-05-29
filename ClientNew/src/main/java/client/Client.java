package client;

import controller.MainControllerInterface;
import views.CustomerManagementView;
import views.LoginView;

import java.rmi.Naming;

public class Client {
    private static final String SERVER_IP = "192.168.89.111";
    private static final int PORT = 6969;
    private static final String URL = "rmi://" + SERVER_IP + ":" + PORT + "/";

    public static void main(String[] args) {
        try {
            MainControllerInterface mainController = (MainControllerInterface) Naming.lookup(URL + "mainController");
//            CustomerManagementView customerManagementView = new CustomerManagementView();
//            customerManagementView.setVisible(true);
            LoginView view = new LoginView(mainController);
            view.setVisible(true);
            System.out.println("Client connected successfully.");
            mainController.notifyServer("Client connected successfully to server.");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
