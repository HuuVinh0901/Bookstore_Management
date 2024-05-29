package views;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import controller.MainController;

import controller.MainControllerInterface;
import lombok.SneakyThrows;
import models.Category;
import util.DialogUtils;
import util.GeneratorIDAuto;


public class CategoryView extends JPanel implements ActionListener, KeyListener, MouseListener {

    private JPanel pnMain;
    private JPanel pnHeading;
    private JPanel pnContainer;
    private JPanel pnThongTinCha;
    private JPanel pnThongTin;
    private JPanel pnThongTinCT1;
    private JPanel pnThongTinCT2;
    private JPanel pnThongTinChucNang;
    private JPanel pnSounth;
    private JPanel pnSounthNorth;
    private JPanel pnSounthSouth;

    private JLabel lblTitle;
    private JLabel lblIdTheLoai;
    private JLabel lblTenTheLoai;
    private JLabel lblSoLuongSach;
    private JLabel lblMoTa;
    private JLabel lblTuKhoa;

    private JTextField txtIdtheLoai;
    private JTextField txtTentheLoai;
    private JTextField txtSoLuongSach;
    private JTextField txtMoTa;
    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnCapNhat;
    private JButton btnXoa;
    private JButton btnLamMoi;
    private JButton btnXemTatCa;

    private JTable table;
    private DefaultTableModel model;
    private static MainControllerInterface mainController;

    static {
        try {
            mainController = new MainController();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private GeneratorIDAuto autoID;


    @SneakyThrows
    public CategoryView(MainControllerInterface mainController) {
        setLayout(new BorderLayout(8, 6));
        this.mainController = mainController;
        autoID = new GeneratorIDAuto();
        init();
    }

    private void init() {
        pnContainer = new JPanel(new BorderLayout());
        pnMain = new JPanel(new BorderLayout());
        pnHeading = new JPanel();
        lblTitle = new JLabel("Quản Lý Thể Loại");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTitle.setForeground(new Color(26, 102, 227));
        pnHeading.add(lblTitle);

        lblIdTheLoai = new JLabel("Mã thể loại:");
        txtIdtheLoai = new JTextField(20);
        txtIdtheLoai.setEditable(false);
        lblTenTheLoai = new JLabel("Tên thể loại:");
        txtTentheLoai = new JTextField(20);
        lblSoLuongSach = new JLabel("Số lượng sách:");
        txtSoLuongSach = new JTextField(20);
        txtSoLuongSach.setEditable(false);
        lblMoTa = new JLabel("Mô tả:");
        txtMoTa = new JTextField(20);

        lblTuKhoa = new JLabel("Từ khóa:");
        txtTuKhoa = new JTextField(20);

        btnCapNhat = new JButton("Cập nhật");
        btnLamMoi = new JButton("Làm mới");
        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnXemTatCa = new JButton("Xem tất cả");

        pnThongTinCha = new JPanel(new BorderLayout());
        pnThongTinCha.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));
        pnThongTin = new JPanel(new GridLayout(2, 8, 20, 10));
        pnThongTinCT1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        pnThongTinCT2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        Insets btnInsert = new Insets(0, 70, 0, 0);
        pnThongTin.setBorder(new EmptyBorder(btnInsert));

        Dimension labelSize = new Dimension(87, 30);
        lblIdTheLoai.setPreferredSize(labelSize);
        lblTenTheLoai.setPreferredSize(labelSize);
        lblMoTa.setPreferredSize(labelSize);
        pnThongTinCT1.add(lblIdTheLoai);
        pnThongTinCT1.add(txtIdtheLoai);
        pnThongTinCT1.add(lblTenTheLoai);
        pnThongTinCT1.add(txtTentheLoai);
        pnThongTinCT2.add(lblSoLuongSach);
        pnThongTinCT2.add(txtSoLuongSach);
        pnThongTinCT2.add(lblMoTa);
        pnThongTinCT2.add(txtMoTa);
        pnThongTin.add(pnThongTinCT1);
        pnThongTin.add(pnThongTinCT2);
        pnThongTinChucNang = new JPanel(new FlowLayout(5));
        ImageIcon iconThem = new ImageIcon(getClass().getResource("/icons/add.png"));
        ImageIcon iconCapNhat = new ImageIcon(getClass().getResource("/icons/capnhat.png"));
        ImageIcon iconLamMoi = new ImageIcon(getClass().getResource("/icons/lammoi.png"));


        btnThem.setIcon(iconThem);
        btnCapNhat.setIcon(iconCapNhat);
        btnLamMoi.setIcon(iconLamMoi);

        pnThongTinChucNang.add(btnThem);
        btnThem.setIcon(iconThem);
        pnThongTinChucNang.add(btnCapNhat);
        btnCapNhat.setIcon(iconCapNhat);
//		pnThongTinChucNang.add(btnXoa);
        pnThongTinChucNang.add(btnLamMoi);
        btnLamMoi.setIcon(iconLamMoi);
        pnThongTinChucNang.setBorder(new EmptyBorder(btnInsert));

        pnSounth = new JPanel(new BorderLayout());
        pnSounthNorth = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pnSounthSouth = new JPanel(new BorderLayout());

        pnSounthNorth.add(lblTuKhoa);
        pnSounthNorth.add(txtTuKhoa);
        pnSounthNorth.add(btnXemTatCa);

        pnSounthSouth.setBorder(BorderFactory.createTitledBorder("Danh mục"));
        model = new DefaultTableModel();
        table = new JTable();
        model.addColumn("Mã thể loại");
        model.addColumn("Tên thể loại");
        model.addColumn("Số lượng tác phẩm");
        model.addColumn("Mô tả");
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        pnSounthSouth.add(scrollPane);

        pnSounth.add(pnSounthNorth, BorderLayout.NORTH);
        pnSounth.add(pnSounthSouth, BorderLayout.CENTER);

        pnThongTinCha.add(pnThongTin, BorderLayout.NORTH);
        pnThongTinCha.add(pnThongTinChucNang, BorderLayout.CENTER);

        pnContainer.add(pnHeading, BorderLayout.NORTH);
        pnMain.add(pnThongTinCha, BorderLayout.NORTH);
        pnMain.add(pnSounth, BorderLayout.CENTER);

        pnContainer.add(pnMain, BorderLayout.CENTER);
        add(pnContainer);
        loadData();

        btnThem.addActionListener(this);
        btnLamMoi.addActionListener(this);
//		btnXoa.addActionListener(this);
        btnCapNhat.addActionListener(this);
        table.addMouseListener(this);
        txtTentheLoai.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                int row = table.getSelectedRow();
                if (row == -1)
                    txtIdtheLoai.setText(autoID.autoID("TL"));
                else
                    txtIdtheLoai.setText(model.getValueAt(row, 0).toString());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            addCategory();
        } else if (o.equals(btnLamMoi)) {
            refreshForm();
        } else if (o.equals(btnXoa)) {
            deleteCategory();
        } else if (o.equals(btnCapNhat)) {
            updateCategory();
        }

    }

    private void updateIdCategory() {
        if (table.getSelectedRow() == -1) {
            Date currentTime = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String formattedTime = "Ca" + sdf.format(currentTime);
            txtIdtheLoai.setText(formattedTime);
        }
    }

    @SneakyThrows
    private void loadData() {
        model.setRowCount(0);
        java.util.List<Category> categories = mainController.getAllCategory();

        if (categories != null) {
            for (Category category : categories) {
                Object[] rowData = {category.getIdCategory(), category.getCategoryName(), category.getBookQuantity(), category.getDescription()};
                model.addRow(rowData);
            }
        } else {
            DialogUtils.showErrorMessage(this, "Lỗi tải dữ liệu");
        }
    }

    private void updateCategory() {
        int row = table.getSelectedRow();
        if (row < 0) {
            DialogUtils.showErrorMessage(this, "Vui lòng chọn một hàng để cập nhật.");
        } else {
            try {
                Category category = getCategory();
                if (mainController.updateCategory(category)) {
                    DialogUtils.showSuccessMessage(this, "Cập nhật thành công.");
                    loadData();
                    refreshForm();
                } else {
                    DialogUtils.showErrorMessage(this, "Cập nhật không thành công.");
                }
            } catch (Exception e) {
                DialogUtils.showErrorMessage(this, "Lỗi cập nhật danh mục: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void deleteCategory() {
        int row = table.getSelectedRow();
        if (row == -1) {
            DialogUtils.showErrorMessage(this, "Vui lòng chọn một hàng để xóa.");
        } else {
            int confirmDialog = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hàng này không?", "Cảnh báo",
                    JOptionPane.YES_NO_OPTION);
            if (confirmDialog == JOptionPane.YES_OPTION) {
                try {
                    String categoryId = txtIdtheLoai.getText();
                    if (mainController.deleteCategory(categoryId)) {
                        DialogUtils.showSuccessMessage(this, "Đã xoá thành công.");
                        loadData();
                        refreshForm();
                    } else {
                        DialogUtils.showErrorMessage(this, "Xóa không thành công.");
                    }
                } catch (Exception e) {
                    DialogUtils.showErrorMessage(this, "Lỗi xóa danh mục: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                DialogUtils.showErrorMessage(this, "Việc xóa đã bị hủy.");
            }
        }
    }


    private void refreshForm() {
        txtTentheLoai.setText("");
        txtSoLuongSach.setText("");
        txtMoTa.setText("");
        txtTentheLoai.requestFocus();
        table.clearSelection();
        updateIdCategory();
    }

    private Category getCategory() {
        Category category = null;
        String idTheLoai = txtIdtheLoai.getText();
        String tenTheLoai = txtTentheLoai.getText();
        int soLuongSach = Integer.parseInt(txtSoLuongSach.getText());
        String moTa = txtMoTa.getText();
        category = new Category(idTheLoai, tenTheLoai, soLuongSach, moTa);
        return category;
    }

    @SneakyThrows
    private void addCategory() {
        String idTheLoai = txtIdtheLoai.getText();
        String tenTheLoai = txtTentheLoai.getText();
        int soLuongSach = 0;
        String moTa = txtMoTa.getText();

        if (tenTheLoai.isEmpty() || moTa.isEmpty()) {
            DialogUtils.showErrorMessage(this, "Vui lòng điền vào tất cả các lĩnh vực");
            return;
        }
        Category category = new Category(idTheLoai, tenTheLoai, soLuongSach, moTa);

        if (mainController.checkIdCategory(category.getIdCategory())) {
            DialogUtils.showErrorMessage(this, "ID danh mục trùng lặp. Vui lòng chọn một ID khác.");
            return;
        }

        if (mainController.addCategory(category)) {
            DialogUtils.showSuccessMessage(this, "Đã thêm danh mục thành công");
            loadData();
            refreshForm();
        } else {
            DialogUtils.showErrorMessage(this, "Lỗi khi thêm danh mục");
        }
    }

//	private boolean validataField(JTextField textField, String regex, String errorMessage) {
//		String fieldValue = textField.getText().trim();
//		if (fieldValue.isEmpty()) {
//			showErrorDialog("Vui lòng nhập giá trị cho " + textField.getName() + "!");
//			textField.requestFocus();
//			return false;
//		}
//
//		if (!fieldValue.matches(regex)) {
//			showErrorDialog(errorMessage);
//			textField.requestFocus();
//			textField.selectAll();
//			return false;
//		}
//		return true;
//	}

//	private boolean validataFields() {
//		return validataField(txtTentheLoai, "^[\\p{L}\\s]+$",
//				"Tên thể loại không hợp lệ. Phải bắt đầu bằng chữ cái, không chấp nhận ký tự đặc biệt.")
//				&& validataField(txtMoTa, "^[^,\\p{P}-+ ]+[\\p{L}\\p{M}0-9]*(\\s[^,\\p{P}-+ ]+[\\p{L}\\p{M}0-9]*)*[^,\\p{P}-+ ]$",
//						"Mô tả không được chứa kí tự đặc biệt.");
//
//
//	}


    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtIdtheLoai.setText(model.getValueAt(row, 0).toString());
            txtTentheLoai.setText(model.getValueAt(row, 1).toString());
            txtSoLuongSach.setText(model.getValueAt(row, 2).toString());
            txtMoTa.setText(model.getValueAt(row, 3).toString());
        }
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

    }

}
