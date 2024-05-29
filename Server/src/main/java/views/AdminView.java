package views;

import controller.MainController;
import controller.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminView extends JFrame {
    private JScrollPane jScrollPane1;
    private JPanel menus;
    private JPanel panelBody;
    private JPanel panelHeader;
    private JPanel panelMenu;
    private JPanel paneCu;
    private MainController mainController;

    public AdminView() {
        initComponents();
        setTitle("Quản trị");
        setSize(new Dimension(871, 473));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        paneCu = new HomeView(); // Bắt đầu với HomeView
        panelBody.add(paneCu);
        execute();

        menus.setBackground(new Color(153, 225, 225));


    }

    private void execute() {


        ImageIcon iconNV = new ImageIcon(getClass().getResource("/icons/NV.png"));
        ImageIcon iconDX = new ImageIcon(getClass().getResource("/icons/DX.png"));

        MenuItem QL = new MenuItem(iconNV, "THÔNG TIN QUẢN LÝ", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                switchToPanel(new ManagerAdminView(mainController));
            }
        });
        MenuItem DangXuat = new MenuItem(iconDX, "Đăng xuất", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                int hoiNhac = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn đăng xuất",
                        "Cảnh báo", JOptionPane.YES_NO_OPTION);
                if (hoiNhac == JOptionPane.YES_OPTION) {
                    LoginView view = new LoginView(mainController);
                    view.setVisible(true);
                    dispose();
                }

            }
        });
        addMenu(QL, DangXuat);
        QL.setBackground(new Color(153, 225, 225));
        DangXuat.setBackground(new Color(153, 225, 225));
    }

    private void switchToPanel(JPanel newPanel) {
        panelBody.remove(paneCu); // Loại bỏ panel hiện tại
        paneCu = newPanel; // Cập nhật panel hiện tại
        panelBody.add(paneCu); // Thêm panel mới vào panelBody
        panelBody.repaint();
        panelBody.revalidate();
    }

    private void addMenu(MenuItem... menu) {
        for (int i = 0; i < menu.length; i++) {
            menus.add(menu[i]);

            ArrayList<MenuItem> subMenu = menu[i].getSubMenu();
            for (MenuItem m : subMenu) {
                addMenu(m);
            }
        }
        menus.revalidate();
    }

    private void initComponents() {
//		panelHeader = new JPanel();
        panelMenu = new JPanel();
        jScrollPane1 = new JScrollPane();
        menus = new JPanel();
        panelBody = new JPanel();

//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//		panelHeader.setBackground(new Color(225,223,223));
//		panelHeader.setPreferredSize(new Dimension(561, 50));

//		GroupLayout panelHeaderLayout = new GroupLayout(panelHeader);
//		panelHeader.setLayout(panelHeaderLayout);
//		panelHeaderLayout.setHorizontalGroup(
//				panelHeaderLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 855, Short.MAX_VALUE));
//		panelHeaderLayout.setVerticalGroup(
//				panelHeaderLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 50, Short.MAX_VALUE));
//
//		getContentPane().add(panelHeader, BorderLayout.PAGE_START);

        jScrollPane1.setBorder(null);


        menus.setLayout(new BoxLayout(menus, BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(menus);

        GroupLayout panelMenuLayout = new GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(panelMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE));
        panelMenuLayout.setVerticalGroup(panelMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE));

        getContentPane().add(panelMenu, BorderLayout.LINE_START);


        panelBody.setLayout(new BorderLayout());
        getContentPane().add(panelBody, BorderLayout.CENTER);
    }
}
