package views;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.MainController;
import controller.MainControllerInterface;
import lombok.SneakyThrows;
import models.Account;
import models.Employee;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class SetPasswordView extends Dialog implements ActionListener, MouseListener {
    private JLabel lblTieuDe;
    private JLabel lblPasswordOld;
    private JLabel lblMatKhauMoi;
    private JLabel lblXacNhanMatKhau;
    private JLabel lblTenDangNhap;
    private JLabel lblTenNhanVien;
    private JPasswordField txtMatKhauMoi;
    private JPasswordField txtXacNhanMatKhau;
    private JPasswordField passwordOld;
    private JTextField txtTenDangNhap;
    private JTextField txtTenNhanVien;
    private JButton btnGuiLaiMa;
    private JButton btnXacNhan;

    private JPanel pnContainer;
    private JPanel pnMain;
    private JPanel pnHeading;
    private JPanel pnCenter;
    private JPanel pnSounth;
    private Employee employee;
    private static MainControllerInterface mainController;

    static {
        try {
            mainController = new MainController();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public SetPasswordView(Employee e, MainControllerInterface mainController) {
        super((JDialog) null, "Đặt lại mật khẩu", true);
        this.employee = e;
        this.mainController = mainController;
        setSize(300, 430);
        setTitle("Đặt lại mật khẩu");
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/logo.png"));
        setIconImage(icon.getImage());
        setLocationRelativeTo(null);

        setModal(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(SetPasswordView.this, "Bạn có chắc chắn muốn đóng cửa sổ?",
                        "Xác nhận đóng cửa sổ", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        lblTieuDe = new JLabel("Đặt lại mật khẩu của bạn");
        lblPasswordOld = new JLabel("Mật khẩu cũ");
        lblMatKhauMoi = new JLabel("Mật khẩu mới");
        lblXacNhanMatKhau = new JLabel("Xác nhận mật khẩu");
        lblTenDangNhap = new JLabel("Mã Nhân viên");
        lblTenNhanVien = new JLabel("Tên nhân viên");

        passwordOld = new JPasswordField(20);
        txtMatKhauMoi = new JPasswordField(20);
        txtXacNhanMatKhau = new JPasswordField(20);
        txtTenDangNhap = new JTextField(20);
        txtTenNhanVien = new JTextField(20);

        txtTenDangNhap.setText(employee.getIdEmployee());
        txtTenNhanVien.setText(employee.getName());
        txtTenDangNhap.setEditable(false);
        txtTenNhanVien.setEditable(false);

        btnGuiLaiMa = new JButton("Hủy bỏ");
        btnXacNhan = new JButton("Xác nhận");

        pnContainer = new JPanel(new BorderLayout());
        pnMain = new JPanel(new BorderLayout());
        pnHeading = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 20));
        pnHeading.add(lblTieuDe);

        pnCenter = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridy++;
        pnCenter.add(lblTenDangNhap, gbc);
        gbc.gridy++;
        pnCenter.add(txtTenDangNhap, gbc);

        gbc.gridy++;
        pnCenter.add(lblTenNhanVien, gbc);
        gbc.gridy++;
        pnCenter.add(txtTenNhanVien, gbc);

        gbc.gridy++;
        pnCenter.add(lblPasswordOld, gbc);
        gbc.gridy++;
        pnCenter.add(passwordOld, gbc);
        gbc.gridy++;
        pnCenter.add(lblMatKhauMoi, gbc);
        gbc.gridy++;
        pnCenter.add(txtMatKhauMoi, gbc);
        gbc.gridy++;
        pnCenter.add(lblXacNhanMatKhau, gbc);
        gbc.gridy++;
        pnCenter.add(txtXacNhanMatKhau, gbc);

        pnSounth = new JPanel();
        pnSounth.add(btnGuiLaiMa);
        pnSounth.add(btnXacNhan);

        pnMain.add(pnHeading, BorderLayout.NORTH);
        pnMain.add(pnCenter, BorderLayout.CENTER);
        pnMain.add(pnSounth, BorderLayout.SOUTH);

        pnContainer.add(pnMain, BorderLayout.NORTH);
        add(pnContainer);
        btnGuiLaiMa.addActionListener(this);
        btnXacNhan.addActionListener(this);
        init();
    }

    private void init() {

//		changeAccount(nhanVien);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnGuiLaiMa)) {
            xacNhanHuy();
        } else if (o.equals(btnXacNhan)) {
            capNhatMatKhau();
            lamMoi();
        }

    }

    private void lamMoi() {
        txtMatKhauMoi.setText("");
        txtXacNhanMatKhau.setText("");
        passwordOld.setText("");
        txtMatKhauMoi.requestFocus();

    }

    private boolean kiemTraMatKhauCu(String idTaiKhoan, char[] matKhauCu) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
            String plainPassword = new String(matKhauCu);

            String hashedMatKhauDB = mainController.findPasswordByEmployeeId(idTaiKhoan);

            boolean passwordMatch = passwordEncoder.matches(plainPassword, hashedMatKhauDB);

            return passwordMatch;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            Arrays.fill(matKhauCu, ' ');
        }
    }


    private boolean kiemTraMatKhau(char[] matKhauMoi, char[] xacNhanMatKhau) {
        return Arrays.equals(matKhauMoi, xacNhanMatKhau);
    }

    @SneakyThrows
    private void capNhatMatKhau() {
        char[] matKhauMoi = txtMatKhauMoi.getPassword();
        char[] xacNhanMatKhau = txtXacNhanMatKhau.getPassword();
        char[] matKhauCu = passwordOld.getPassword();
        Account account = mainController.getAccountById(employee.getIdEmployee());
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
            String hashedMatKhauDB = account.getPassword();

            if (!passwordEncoder.matches(new String(matKhauCu), hashedMatKhauDB)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (matKhauMoi.length == 0 || xacNhanMatKhau.length == 0) {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới không được để trống", "Cảnh báo",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!kiemTraMatKhau(matKhauMoi, xacNhanMatKhau)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu và xác nhận mật khẩu không trùng khớp", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String hashedPassword = passwordEncoder.encode(new String(xacNhanMatKhau));

//            boolean updateSuccess = mainController.updatePassword(employee.getIdEmployee(), hashedPassword);
//
//            if (updateSuccess) {
//                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
//                lamMoi();
//                this.dispose();
//            } else {
//                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thất bại", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//            }
        } finally {
            Arrays.fill(matKhauCu, ' ');
            Arrays.fill(matKhauMoi, ' ');
            Arrays.fill(xacNhanMatKhau, ' ');
        }
    }


    private void xacNhanHuy() {
        int hoiNhac = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy", "Cảnh báo",
                JOptionPane.YES_NO_OPTION);
        if (hoiNhac == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
}