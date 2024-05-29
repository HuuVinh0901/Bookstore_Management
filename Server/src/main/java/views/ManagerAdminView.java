package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;
import controller.MainController;
import controller.MainControllerInterface;
import lombok.SneakyThrows;
import models.Account;
import models.Employee;
import models.ProductType;
import models.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import util.DialogUtils;
import util.GeneratorIDAuto;


public class ManagerAdminView extends JPanel implements KeyListener, MouseListener, ActionListener, DocumentListener {
    private static final int ROLE = 1;
    private JDateChooser chooserNgaySinh;
    private JTextField txtTenNV;
    private JTextField txtsdt;
    private JTextField txtEmail;
    private JTextField txtDiaChi;
    private JTextField txtTimKiem;
    private JTextField txtID;
    private JLabel lbTenNV;
    private JLabel lbsdt;
    private JLabel lbEmail;
    private JLabel lbDiaChi;
    private JLabel lbNgaySinh;
    private JLabel lbGioiTinh;
    private JLabel lbID;
    private JLabel lbTimKiem;
    private JRadioButton rbNam;
    private JRadioButton rbNu;
    private JComboBox<String> cbChucVu, cbTimKiem;
    private JComboBox<Object> cbTrangThai;
    private JLabel lbChucVu;
    private JLabel lbTrangThai;
    private JTable tableNhanVien;
    private DefaultTableModel modelNhanVien;
    private JButton btnaddEmployee;
    private JButton btnCapNhatNV;
    private JButton btnXoaNV;
    private JButton btnLamMoi;
    private JButton btnXemTatCa;
    private SimpleDateFormat dfNgaySinh;
    private GeneratorIDAuto autoID;
    private static MainControllerInterface mainController;

    static {
        try {
            mainController = new MainController();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private JButton btnAddRole;
    private JTable tbChucVu;
    private DefaultTableModel modelChucVu;

    @SneakyThrows
    public ManagerAdminView(MainControllerInterface mainController) {
        dfNgaySinh = new SimpleDateFormat("dd/MM/yyyy");
        autoID = new GeneratorIDAuto();
        this.mainController = mainController;
        tbChucVu = new JTable();
        modelChucVu = new DefaultTableModel();
        btnAddRole = new JButton("Thêm chức vụ");
        setLayout(new BorderLayout());

        JPanel pnNouth = new JPanel(new BorderLayout());
        JPanel pnSounth = new JPanel(new BorderLayout());
        JPanel pnTitle = new JPanel();
        JPanel pnInfo = new JPanel(new GridLayout(5, 1, 5, 5));
        JPanel pnChucNang = new JPanel(new GridLayout(1, 4, 10, 10));
        JPanel pnTimKiem = new JPanel(new GridLayout(1, 3, 10, 10));

//		Tiêu đề
        JLabel lblTieuDe = new JLabel("THÔNG TIN QUẢN LÝ");
        lblTieuDe.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTieuDe.setForeground(new Color(26, 102, 227));
        pnTitle.add(lblTieuDe);
        pnNouth.add(pnTitle, BorderLayout.NORTH);
        add(pnNouth, BorderLayout.NORTH);

        lbID = new JLabel("ID quản lý:");
        lbTenNV = new JLabel("Tên quản lý:");
        txtTenNV = new JTextField();
        lbsdt = new JLabel("Số điện thoại:");
        txtsdt = new JTextField();
        lbEmail = new JLabel("Email:");
        txtEmail = new JTextField();
        lbDiaChi = new JLabel("Địa chỉ:");
        txtDiaChi = new JTextField();
        lbChucVu = new JLabel("Chức vụ:");
        lbTrangThai = new JLabel("Trạng thái:");
        lbGioiTinh = new JLabel("Giới tính:");
        lbNgaySinh = new JLabel("Ngày sinh:");
        txtID = new JTextField();
        txtTenNV.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                int row = tableNhanVien.getSelectedRow();
                if (row == -1)
                    txtID.setText(autoID.autoID("NS"));
                else
                    txtID.setText(modelNhanVien.getValueAt(row, 0).toString());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        txtID.setEditable(false);
        cbChucVu = new JComboBox<>();
        cbChucVu.addItem("Chon chuc vu");
        JPanel pnCbChucVu = new JPanel(new BorderLayout());
        pnCbChucVu.add(cbChucVu, BorderLayout.CENTER);
        pnCbChucVu.add(btnAddRole, BorderLayout.EAST);
        cbTrangThai = new JComboBox<Object>(new Object[]{"Đang làm việc", "Đã nghỉ việc"});

        chooserNgaySinh = new JDateChooser();
        chooserNgaySinh.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chooserNgaySinh.setBounds(100, 310, 200, 40);
        chooserNgaySinh.setDateFormatString("dd/MM/yyyy");
        chooserNgaySinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
        chooserNgaySinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
        chooserNgaySinh.getCalendarButton().setPreferredSize(new Dimension(30, 24));
        chooserNgaySinh.getCalendarButton().setBackground(new Color(102, 0, 153));
        chooserNgaySinh.getCalendarButton().setToolTipText("Chọn ngày sinh");
        chooserNgaySinh.setDate(new java.util.Date());
        rbNam = new JRadioButton("Nam");
        rbNu = new JRadioButton("Nữ");
        // Tạo button groud giới tính
        ButtonGroup groupGT = new ButtonGroup();
        groupGT.add(rbNam);
        groupGT.add(rbNu);
        pnInfo.add(lbID);
        pnInfo.add(txtID);
        pnInfo.add(lbTenNV);
        pnInfo.add(txtTenNV);
        pnInfo.add(lbsdt);
        pnInfo.add(txtsdt);
        pnInfo.add(lbEmail);
        pnInfo.add(txtEmail);
        pnInfo.add(lbDiaChi);
        pnInfo.add(txtDiaChi);
        pnInfo.add(lbChucVu);
        pnInfo.add(pnCbChucVu);
//        pnInfo.add(cbChucVu);
//        pnInfo.add(btnAddRole);
        pnInfo.add(lbTrangThai);
        pnInfo.add(cbTrangThai);
        pnInfo.add(lbNgaySinh);
        pnInfo.add(chooserNgaySinh);
        pnInfo.add(lbGioiTinh);
        pnInfo.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));

        pnInfo.add(rbNam);
        pnInfo.add(rbNu);

        pnNouth.add(pnInfo, BorderLayout.CENTER);

        lbTimKiem = new JLabel("Tìm kiếm nhân viên:");
        txtTimKiem = new JTextField();
        pnTimKiem.add(lbTimKiem);
        pnTimKiem.add(txtTimKiem);
        ImageIcon iconThem = new ImageIcon(getClass().getResource("/icons/add.png"));
        ImageIcon iconCapNhat = new ImageIcon(getClass().getResource("/icons/capnhat.png"));
        ImageIcon iconLamMoi = new ImageIcon(getClass().getResource("/icons/lammoi.png"));
        ImageIcon iconXoa = new ImageIcon(getClass().getResource("/icons/xoa.png"));
        btnaddEmployee = new JButton("THÊM QUẢN LÝ");
        btnaddEmployee.setIcon(iconThem);
        btnCapNhatNV = new JButton("CẬP NHẬT THÔNG TIN ");
        btnCapNhatNV.setIcon(iconCapNhat);
        btnXoaNV = new JButton("XÓA QUẢN LÝ");
        btnXoaNV.setIcon(iconXoa);
        btnLamMoi = new JButton("LÀM MỚI");
        btnLamMoi.setIcon(iconLamMoi);
        btnXemTatCa = new JButton("XEM TẤT CẢ");
        pnChucNang.add(btnaddEmployee);
        pnChucNang.add(btnCapNhatNV);
        pnChucNang.add(btnXoaNV);
        pnChucNang.add(btnLamMoi);
        pnChucNang.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        pnNouth.add(pnChucNang, BorderLayout.SOUTH);
        add(pnSounth, BorderLayout.CENTER);
        pnSounth.add(pnTimKiem, BorderLayout.NORTH);
        pnTimKiem.add(btnXemTatCa);

        modelNhanVien = new DefaultTableModel();
        tableNhanVien = new JTable();
        modelNhanVien.addColumn("ID");
        modelNhanVien.addColumn("Tên");
        modelNhanVien.addColumn("Số điện thoại");
        modelNhanVien.addColumn("Email");
        modelNhanVien.addColumn("Địa chỉ");
        modelNhanVien.addColumn("Ngày sinh");
        modelNhanVien.addColumn("Giới tính");
        modelNhanVien.addColumn("Chức vụ");
        modelNhanVien.addColumn("Trạng thái");
        tableNhanVien.setModel(modelNhanVien);
        JScrollPane scrollPane = new JScrollPane(tableNhanVien);
//        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách nhân viên"));
        pnSounth.add(scrollPane, BorderLayout.CENTER);

        btnLamMoi.addActionListener(this);
        btnaddEmployee.addActionListener(this);
        btnCapNhatNV.addActionListener(this);
        btnXoaNV.addActionListener(this);
        btnXemTatCa.addActionListener(this);
        tableNhanVien.addMouseListener(this);
        txtTimKiem.addKeyListener(this);
        txtTenNV.addKeyListener(this);
        txtDiaChi.addKeyListener(this);
        txtEmail.addKeyListener(this);
        txtsdt.addKeyListener(this);
        txtID.getDocument().addDocumentListener(this);
        btnAddRole.addActionListener(this);
        loadData();
        loadRole();
        loadComboBox();
    }

    @SneakyThrows
    public void loadComboBox() {
        List<Role> roles = mainController.getRolesByRoleCode(ROLE);
        for (Role role : roles) {
            cbChucVu.addItem(role.getRoleName());
        }
    }

    private void showAddPositionDialog() {
        loadRole();
        JDialog dialog = new JDialog();
        dialog.setTitle("Thêm chức vụ");
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        JPanel pnCenter = new JPanel(new BorderLayout());

        JLabel lblTenChucVu = new JLabel("Tên chức vụ:");
        JTextField txtTenChucVu = new JTextField();
        txtTenChucVu.setColumns(15);

        JPanel pnInput = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        pnInput.add(lblTenChucVu, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        pnInput.add(txtTenChucVu, gbc);

        pnCenter.add(pnInput, BorderLayout.NORTH);

        tbChucVu = new JTable();
        modelChucVu = new DefaultTableModel();
        modelChucVu.addColumn("ID");
        modelChucVu.addColumn("Tên chức vụ");
        tbChucVu.setModel(modelChucVu);
        JScrollPane scrollTbChucVu = new JScrollPane(tbChucVu);
        pnCenter.add(scrollTbChucVu, BorderLayout.CENTER);

        JPanel pnBottom = new JPanel(new BorderLayout());

        JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnCancel = new JButton("Hủy");

        pnButtons.add(btnAdd);
//        pnButtons.add(btnEdit);
//        pnButtons.add(btnDelete);
        pnButtons.add(btnCancel);

        pnBottom.add(pnButtons, BorderLayout.SOUTH);

        btnAdd.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                String roleName = txtTenChucVu.getText();
                if (roleName.isEmpty()) {
                    DialogUtils.showErrorMessage(dialog, "Tên chức vụ không được để trống");
                    return;
                }

                Role role = new Role(roleName, ROLE);
                mainController.addRole(role);
                modelChucVu.addRow(new Object[]{role.getIdRole(), role.getRoleName()});
                loadComboBox();
//                loadRole();
                txtTenChucVu.setText("");
                DialogUtils.showSuccessMessage(dialog, "Thêm chức vụ thành công");
                dialog.dispose();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tbChucVu.getSelectedRow();
                if (row == -1) {
                    DialogUtils.showErrorMessage(dialog, "Vui lòng chọn chức vụ cần xóa");
                    return;
                }
                String idRole = modelChucVu.getValueAt(row, 0).toString();
                mainController.deleteRole(idRole);
                modelChucVu.removeRow(row);
                loadComboBox();
                loadRole();
                DialogUtils.showSuccessMessage(dialog, "Xóa chức vụ thành công");
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tbChucVu.getSelectedRow();
                if (row == -1) {
                    DialogUtils.showErrorMessage(dialog, "Vui lòng chọn chức vụ cần sửa");
                    return;
                }
                String idRole = modelChucVu.getValueAt(row, 0).toString();
                String roleName = txtTenChucVu.getText().trim();
                if (roleName.isEmpty()) {
                    DialogUtils.showErrorMessage(dialog, "Tên chức vụ không được để trống");
                    return;
                }
                Role role = new Role();
                role.setRoleName(roleName);
                mainController.updateRole(idRole);
                modelChucVu.setValueAt(roleName, row, 1);
                loadComboBox();
                loadRole();
                txtTenChucVu.setText("");
                DialogUtils.showSuccessMessage(dialog, "Sửa chức vụ thành công");
            }
        });

        dialog.add(pnCenter, BorderLayout.CENTER);
        dialog.add(pnBottom, BorderLayout.SOUTH);
        loadRole();
        dialog.setVisible(true);
    }


    @SneakyThrows
    public void loadRole() {
//        modelChucVu.setRowCount(0);
        List<Role> roles = mainController.getRolesByRoleCode(ROLE);
        for (Role role : roles) {
            modelChucVu.addRow(new Object[]{role.getIdRole(), role.getRoleName()});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        Object o = e.getSource();
        if (o.equals(btnaddEmployee)) {
            if (valiDate()) {
                addEmployee();
            }
        }
        if (o.equals(btnLamMoi)) {
            refeshForm();
        }
        if (o.equals(btnXoaNV)) {
            xoaNV();
        }
        if (o.equals(btnCapNhatNV)) {
            CapNhatNV();
        }
        if (o.equals(btnAddRole)) {
            showAddPositionDialog();
        }

    }

//    public static String hashWithSHA3_256(String input) throws NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("SHA3-256");
//        byte[] hashBytes = md.digest(input.getBytes());
//        StringBuilder sb = new StringBuilder();
//        for (byte b : hashBytes) {
//            sb.append(String.format("%02x", b));
//        }
//        return sb.toString();
//    }

    @SneakyThrows
    private void addEmployee() {
        String id = txtID.getText();
        String ten = txtTenNV.getText();
        String email = txtEmail.getText();
        String sdt = txtsdt.getText();
        String diaChi = txtDiaChi.getText();
        java.util.Date date = chooserNgaySinh.getDate();
        Date ngaySinh = new Date(date.getYear(), date.getMonth(), date.getDate());
        String gioiTinh = rbNam.isSelected() ? "Nam" : "Nữ";
        String trangThaiValue = cbTrangThai.getSelectedItem().toString();
        String trangThai = cbTrangThai.getSelectedItem().toString();
        String chucVuSelected = (String) cbChucVu.getSelectedItem();
        Date ngayLap = new Date(System.currentTimeMillis());


        Role role = null;
        for (Role item : mainController.getAllRole()) {
            if (item.getRoleName().equals(chucVuSelected)) {
                role = item;
                break;
            }
        }
        String matkhau = "1111";
        Employee e = new Employee();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        String hasdPassword = passwordEncoder.encode(matkhau);
        e.setIdEmployee(id);
        e.setName(ten);
        e.setPhone(sdt);
        e.setAddress(diaChi);
        e.setEmail(email);
        e.setBirth(ngaySinh);
        e.setGender(gioiTinh);
        e.setRole(role);
        e.setStatus(trangThai);

        Account account = new Account();
        account.setEmployee(e);
        account.setPassword(hasdPassword);
        account.setStarDate(ngayLap);
        if (valiDate()) {
            mainController.addEmployee(e);
            mainController.addAccount(account);
            loadData();
            refeshForm();
            DialogUtils.showSuccessMessage(this, "Thêm nhân viên thành công");
        }
    }

    @SneakyThrows
    private void loadData() {
//        modelNhanVien.setRowCount(0);
//        String roleName = "";
//        for (Employee employee : mainController.getAllEmployees()) {
//            if (employee.getRole() != null) {
//                roleName = employee.getRole().getRoleName();
//            } else {
//                roleName = "";
//            }
//            modelNhanVien.addRow(new Object[]{employee.getIdEmployee(), employee.getName(), employee.getPhone(), employee.getEmail(), employee.getAddress(), dfNgaySinh.format(employee.getBirth()), employee.getGender(), roleName, employee.getStatus()
//            });
//            refeshForm();
//        }
        modelNhanVien.setRowCount(0);
        for (Employee employee : mainController.findEmployeeByRoleCode(ROLE)) {
            modelNhanVien.addRow(new Object[]{employee.getIdEmployee(), employee.getName(), employee.getPhone(), employee.getEmail(), employee.getAddress(), dfNgaySinh.format(employee.getBirth()), employee.getGender(), employee.getRole().getRoleName(), employee.getStatus()});
        }
    }

    private void CapNhatNV() {
//        String id = txtID.getText();
//        String ten = txtTenNV.getText();
//        String diaChi = txtDiaChi.getText();
//        String soDienThoai = txtsdt.getText();
//        String email = txtEmail.getText();
//
//        java.util.Date date = chooserNgaySinh.getDate();
//        Date ngaySinh = new Date(date.getYear(), date.getMonth(), date.getDate());
//        Boolean gioiTinh = null;
//        if (rbNam.isSelected()) {
//            gioiTinh = true;
//        }
//        if (rbNu.isSelected()) {
//            gioiTinh = false;
//        }
//        String trangThaiValue = cbTrangThai.getSelectedItem().toString();
//        Boolean trangThai = null;
//        if (trangThaiValue.equals("Đang làm việc")) {
//            trangThai = true;
//        }
//        if (trangThaiValue.equals("Đã nghỉ việc")) {
//            trangThai = false;
//        }
//        String chucVu = cbChucVu.getSelectedItem().toString();
//        QuanLy nv = new QuanLy();
//        nv.setTen(ten);
//        nv.setSoDienThoai(soDienThoai);
//        nv.setNgaySinh(ngaySinh);
//        nv.setEmail(email);
//        nv.setDiaChi(diaChi);
//        nv.setGioiTinh(gioiTinh);
//        nv.setId(id);
//        nv.setTrangThai(trangThai);
//        nv.setChucVu(chucVu);
//        if (valiDate()) {
//            try {
//                daoQuanLy.updateNhanVien(nv);
//                ;
//                loadData();
//                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
//            } catch (Exception e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
//            }
//        }

    }

    public boolean valiDate() {

        String ten = txtTenNV.getText().trim();


        Boolean gioiTinh;
        if (rbNam.isSelected() || rbNu.isSelected()) {
            gioiTinh = true;
        } else {
            gioiTinh = false;
        }
        java.util.Date ngaySinh = chooserNgaySinh.getDate();
        java.util.Date ngayHienTai = new java.util.Date();
        String diaChi = txtDiaChi.getText().trim();
        String email = txtEmail.getText().trim();
        String soDienThoai = txtsdt.getText().trim();


        if (ten.equals("") || diaChi.equals("") || email.equals("") || soDienThoai.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin đầy đủ!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            txtTenNV.requestFocus();

            return false;
        }
        if (!(ten.length() > 0 && ten.matches("^[A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*(?:[ ][A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*)*$"))) {
            JOptionPane.showMessageDialog(this, "Tên phải viết hoa và không chứa số", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            txtTenNV.selectAll();
            txtTenNV.requestFocus();
            return false;
        }

        if (!(diaChi.length() > 0 && diaChi.matches("^[^!@#$%^&*()+-]*$"))) {
            JOptionPane.showMessageDialog(this, "Địa chỉ không được chứa kí tự đặc biệt", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            txtDiaChi.requestFocus();
            txtDiaChi.selectAll();
            return false;
        }
        if (!(email.length() > 0 && email.matches("^[A-Za-z][A-Za-z0-9@.]*$"))) {
            JOptionPane.showMessageDialog(this, "Email phải bắt đầu bằng chữ và không được chứa kí tự đặc biệt", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            txtEmail.selectAll();
            return false;
        }
        if (!(ngaySinh != null && (ngayHienTai.getYear() - ngaySinh.getYear() > 18))) {
            JOptionPane.showMessageDialog(this, "Nhân viên chưa đủ 18 tuổi", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            chooserNgaySinh.requestFocus();

            return false;
        }
        if (!(soDienThoai.length() > 0 && soDienThoai.matches("^(0|\\+84)[0-9]{9}$"))) {
            JOptionPane.showMessageDialog(this, "Số điện thoại gồm 10 số và bắt đầu bằng 0 hoặc +84", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            txtsdt.requestFocus();
            txtsdt.selectAll();
            return false;
        }
        if (gioiTinh == false) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);

            return false;
        }
        if (cbChucVu.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }


    private void xoaNV() {
        int row = tableNhanVien.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Phải chọn dòng cần xoá");
        } else {
            try {
                int HopThoai = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá dòng này không?", "Cảnh báo",
                        JOptionPane.YES_NO_OPTION);
                if (HopThoai == JOptionPane.YES_OPTION) {
                    modelNhanVien.removeRow(row);
                    String manv = txtID.getText();
                    mainController.deleteEmployee(manv);
                    JOptionPane.showMessageDialog(this, "Xoá thành công");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                JOptionPane.showMessageDialog(this, "Xoá thất bại");
            }
        }
    }

    private void refeshForm() {
        txtTenNV.setText("");
        txtDiaChi.setText("");
        txtsdt.setText("");
        txtEmail.setText("");
        rbNam.setSelected(true);
        rbNu.setSelected(false);
        cbChucVu.setSelectedItem("Quản lý");
        cbTrangThai.setSelectedItem("Đang làm việc");
        cbChucVu.setSelectedItem(0);
        if (cbChucVu.getItemCount() > 1) {
            cbChucVu.setSelectedIndex(1);
        }
        chooserNgaySinh.setDate(new java.util.Date());
        txtTenNV.requestFocus();
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        // TODO Auto-generated method stub
        int row = tableNhanVien.getSelectedRow();
        txtID.setText(modelNhanVien.getValueAt(row, 0).toString());
        txtTenNV.setText(modelNhanVien.getValueAt(row, 1).toString());
        txtsdt.setText(modelNhanVien.getValueAt(row, 2).toString());
        txtEmail.setText(modelNhanVien.getValueAt(row, 3).toString());
        txtDiaChi.setText(modelNhanVien.getValueAt(row, 4).toString());
        String ngaySinh = modelNhanVien.getValueAt(row, 5).toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date valueNgaySinh = null;
        try {
            valueNgaySinh = dateFormat.parse(ngaySinh);
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        chooserNgaySinh.setDate(valueNgaySinh);
        rbNam.setSelected(modelNhanVien.getValueAt(row, 6).toString() == "Nam");
        rbNu.setSelected(modelNhanVien.getValueAt(row, 6).toString() == "Nữ");
        cbChucVu.setSelectedItem(modelNhanVien.getValueAt(row, 7).toString());
        cbTrangThai.setSelectedItem(modelNhanVien.getValueAt(row, 8).toString());
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
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        SwingUtilities.invokeLater(() -> {
            Object o = e.getSource();
            if (o.equals(txtTimKiem)) {
                DefaultTableModel model = (DefaultTableModel) tableNhanVien.getModel();
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
                tableNhanVien.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter("(?i)" + txtTimKiem.getText().trim(), 1));
            } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                refeshForm();
            }
        });
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
//        txtID.setText(autoID.autoID("E"));
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
