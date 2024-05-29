package views;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;


public class Login extends JFrame {
    private JTextField txtUsername, txtPassword;
    private JButton btnLogin;
    private JLabel lbShowMessage;
    //    getClass().getResource("/images/homeView.jpg")
    private ImageIcon background = new ImageIcon(
            new ImageIcon(getClass().getResource("/images/homeView.jpg")).getImage().getScaledInstance(430, 465,
                    Image.SCALE_SMOOTH));


    private ImageIcon LogoIcon = new ImageIcon(getClass().getResource("/img_login/user_512.png"));
    private ImageIcon scaledLogoIcon = new ImageIcon(LogoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
    Border borderBottomFocus = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#1a66e3"));
    Border borderBottomUnFocus = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#d0e1fd"));
    Border borderBottomError = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED);
    int w1 = 110, w2 = 170, h = 20;

    public Login() {
        setTitle("Đăng nhập");
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createFormLogin();
    }

    public void createFormLogin() {
        JPanel pnMain = new JPanel();
        pnMain.setLayout(null);
        pnMain.setBackground(Color.decode("#ffffff"));

        JLabel thumb = new JLabel();
        thumb.setBounds(0, 0, 430, 465);
        thumb.setIcon(background);
        pnMain.add(thumb);

        JLabel lbUsername, lbPassword;
        lbUsername = new JLabel("Tên đăng nhập: ");
        lbUsername.setFont(new Font("Dialog", Font.BOLD, 14));
        lbUsername.setForeground(Color.decode("#1a66e3"));
        lbUsername.setBounds(469, 140, 285, 25);
        pnMain.add(lbUsername);

        lbPassword = new JLabel("Mật khẩu: ");
        lbPassword.setFont(new Font("Dialog", Font.BOLD, 14));
        lbPassword.setForeground(Color.decode("#1a66e3"));
        lbPassword.setBounds(469, 214, 285, 25);
        pnMain.add(lbPassword);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtUsername.setBounds(469, 177, 285, 25);
        txtUsername.setBorder(borderBottomFocus);
        pnMain.add(txtUsername);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Dialog", Font.PLAIN, 14));
        txtPassword.setBounds(469, 251, 285, 25);
        txtPassword.setBorder(borderBottomUnFocus);
        pnMain.add(txtPassword);

        btnLogin = new JButton("Thay đổi mật khẩu");
        btnLogin.setFont(new Font("Dialog", Font.BOLD, 14));
        btnLogin.setBorder(new LineBorder(Color.decode("#1a66e3")));
//        customUI.getInstance().setCustomBtn(btnLogin);
        btnLogin.setBounds(469, 325, 285, 30);
        pnMain.add(btnLogin);

        getContentPane().add(pnMain);

        JLabel lbWelcome = new JLabel("Đổi mật khẩu đăng nhập");
        lbWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lbWelcome.setBounds(469, 93, 285, 35);
//        customUI.getInstance().setCustomLbTitle(lbWelcome);
        pnMain.add(lbWelcome);

        lbShowMessage = new JLabel("");
        lbShowMessage.setFont(new Font("Dialog", Font.BOLD, 14));
        lbShowMessage.setBounds(469, 288, 285, 25);
        pnMain.add(lbShowMessage);

        JLabel lbLogo = new JLabel(scaledLogoIcon);
        lbLogo.setText("");
        lbLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lbLogo.setBounds(469, 12, 285, 82);
        pnMain.add(lbLogo);
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }


}