package views;

import controller.MainController;
import controller.MainControllerInterface;
import lombok.SneakyThrows;
import models.Category;
import models.ProductType;
import util.DialogUtils;
import util.GeneratorIDAuto;

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
import javax.swing.table.TableRowSorter;


public class ProductTypeView extends JPanel implements ActionListener, KeyListener, MouseListener {
    private JPanel pnMain;
    private JPanel pnHeader, pnTitle;
    private JPanel pnCener;
    private JLabel lblTitle;
    private JPanel pnMainThongTin, pnLoaiThongTin, pnChucNang;
    private JLabel lblIdLoaiSanPham;
    private JLabel lblTenLoaiSanPham;
    private JLabel lblTuKhoa;
    private JTextField txtIdLoaiSanPham;
    private JTextField txtTenLoaiSanPham;
    private JTextField txtTimKiem;
    private JButton btnThem;
    private JButton btnCapNhat;
    private JButton btnXoa;
    private JButton btnLamMoi;
    private JButton btnTimKiem;
    private JPanel pnSouth, pnSouthNorth, pnSouthBottom, pnDanhMuc;
    private JTable tableSP;
    private DefaultTableModel modelSP;
    private JButton btnXemTatCa;
    private Timer timer;
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
    public ProductTypeView(MainControllerInterface mainController) {
        setLayout(new BorderLayout());
        this.mainController = mainController;
        autoID = new GeneratorIDAuto();
        pnMain = new JPanel(new BorderLayout(8, 6));

        pnHeader = new JPanel();
        pnHeader.add(lblTitle = new JLabel("Quản Lý Loại Sản Phẩm"));
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTitle.setForeground(new Color(26, 102, 227));

        // layout thong tin
        pnMainThongTin = new JPanel(new BorderLayout());
        pnMainThongTin.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));

        pnLoaiThongTin = new JPanel(new GridLayout(2, 2, 10, 10));

        lblIdLoaiSanPham = new JLabel("Mã loại sản phẩm:");
        txtIdLoaiSanPham = new JTextField(20);
        txtIdLoaiSanPham.setEditable(false);
        txtIdLoaiSanPham.setPreferredSize(new Dimension(150, 30));

        lblTenLoaiSanPham = new JLabel("Tên loại sản phẩm:");
        txtTenLoaiSanPham = new JTextField(20);
        txtTenLoaiSanPham.setPreferredSize(new Dimension(150, 30));

        Insets labelInsets = new Insets(0, 60, 0, 10);
        lblIdLoaiSanPham.setBorder(new EmptyBorder(labelInsets));
        lblTenLoaiSanPham.setBorder(new EmptyBorder(labelInsets));
        Dimension labelSize = new Dimension(200, 30);
        lblIdLoaiSanPham.setPreferredSize(labelSize);
        lblTenLoaiSanPham.setPreferredSize(labelSize);

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.add(lblIdLoaiSanPham);
        idPanel.add(txtIdLoaiSanPham);

        JPanel tenPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tenPanel.add(lblTenLoaiSanPham);
        tenPanel.add(txtTenLoaiSanPham);

        pnLoaiThongTin.add(idPanel);
        pnLoaiThongTin.add(tenPanel);

        pnChucNang = new JPanel(new FlowLayout(5));
        btnThem = new JButton("Thêm");
        btnCapNhat = new JButton("Cập nhật");
        btnLamMoi = new JButton("Làm mới");
        btnXoa = new JButton("Xóa");
        ImageIcon iconThem = new ImageIcon(getClass().getResource("/icons/add.png"));
        ImageIcon iconCapNhat = new ImageIcon(getClass().getResource("/icons/capnhat.png"));
        ImageIcon iconLamMoi = new ImageIcon(getClass().getResource("/icons/lammoi.png"));
        ImageIcon iconXoa = new ImageIcon(getClass().getResource("/icons/xoa.png"));
        btnCapNhat.setIcon(iconCapNhat);
        btnThem.setIcon(iconThem);
        btnLamMoi.setIcon(iconLamMoi);
        btnXoa.setIcon(iconXoa);


        pnChucNang.add(btnThem);
        pnChucNang.add(btnCapNhat);
//		pnChucNang.add(btnXoa);
        pnChucNang.add(btnLamMoi);

        Insets btnInsert = new Insets(50, 60, 0, 0);
        pnChucNang.setBorder(new EmptyBorder(btnInsert));

        // phan top
        pnSouth = new JPanel(new BorderLayout());
        pnSouthNorth = new JPanel(new FlowLayout(5));
        lblTuKhoa = new JLabel("Từ khóa:");
        txtTimKiem = new JTextField(20);
        btnXemTatCa = new JButton("Xem tất cả");
        pnSouthNorth.add(lblTuKhoa);
        pnSouthNorth.add(txtTimKiem);
        pnSouthNorth.add(btnXemTatCa);
        // phan bottom
        pnSouthBottom = new JPanel(new BorderLayout());
        pnSouthBottom = new JPanel(new BorderLayout());
        pnSouthBottom.setBorder(BorderFactory.createTitledBorder("Danh mục"));
        modelSP = new DefaultTableModel();
        tableSP = new JTable();
        modelSP.addColumn("Mã Loại Sản Phẩm");
        modelSP.addColumn("Tên Loại Sản Phẩm");
        tableSP.setModel(modelSP);
        JScrollPane scrollPane = new JScrollPane(tableSP);
        pnSouthBottom.add(scrollPane);

        pnSouth.add(pnSouthNorth, BorderLayout.NORTH);
        pnSouth.add(pnSouthBottom, BorderLayout.CENTER);

        pnMainThongTin.add(pnLoaiThongTin, BorderLayout.NORTH);
        pnMainThongTin.add(pnChucNang, BorderLayout.CENTER);

        pnMain.add(pnHeader, BorderLayout.NORTH);
        pnMain.add(pnMainThongTin, BorderLayout.CENTER);
        pnMain.add(pnSouth, BorderLayout.SOUTH);
        this.add(pnMain);

        btnThem.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnXoa.addActionListener(this);
        btnXemTatCa.addActionListener(this);
        tableSP.addMouseListener(this);
        txtTimKiem.addKeyListener(this);
        txtTenLoaiSanPham.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                int row = tableSP.getSelectedRow();
                if (row == -1)
                    txtIdLoaiSanPham.setText(autoID.autoID("LSP"));
                else
                    txtIdLoaiSanPham.setText(modelSP.getValueAt(row, 0).toString());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        loadData();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            addProductType();
        } else if (o.equals(btnLamMoi)) {
            refreshForm();
        } else if (o.equals(btnCapNhat)) {
            updateProductType();
        } else if (o.equals(btnXoa)) {
            deleteProductType();
        } else if (o.equals(btnXemTatCa)) {
            refreshForm();
            loadData();
        }
    }

    @SneakyThrows
    private void loadData() {
        modelSP.setRowCount(0);
        java.util.List<ProductType> productTypes = mainController.getAllProductType();

        if (productTypes != null) {
            for (ProductType productType : productTypes) {
                Object[] rowData = {productType.getProductTypeId(), productType.getProductTypeName()};
                modelSP.addRow(rowData);
            }
        } else {
            DialogUtils.showErrorMessage(this, "Lỗi tải dữ liệu");
        }
    }

    private void updateIdProductType() {
        if (tableSP.getSelectedRow() == -1) {
            Date currentTime = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String formattedTime = "PT" + sdf.format(currentTime);
            txtIdLoaiSanPham.setText(formattedTime);
        }
    }

    @SneakyThrows
    private void deleteProductType() {
        int row = tableSP.getSelectedRow();
        if (row == -1) {
            DialogUtils.showErrorMessage(this, "Vui lòng chọn một hàng để xóa!");
        } else {
            int confirmDialogResult = JOptionPane.showConfirmDialog(this, "bạn có chắc bạn muốn xóa mục này?", "Cảnh báo",
                    JOptionPane.YES_NO_OPTION);
            if (confirmDialogResult == JOptionPane.YES_OPTION) {
                String idProductType = txtIdLoaiSanPham.getText();
                if (mainController.deleteProductType(idProductType)) {
                    DialogUtils.showSuccessMessage(this, "Đã xóa mục thành công");
                    loadData();
                    refreshForm();
                } else {
                    DialogUtils.showErrorMessage(this, "Không thể xóa mục");
                }
            }
        }
    }

    @SneakyThrows
    private void updateProductType() {
        int row = tableSP.getSelectedRow();
        if (row >= 0) {
            int update = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn cập nhật thông tin này?", "Cảnh báo",
                    JOptionPane.YES_NO_OPTION);
            if (update == JOptionPane.YES_OPTION) {
//                if (validateFields()) {
                String idProductType = txtIdLoaiSanPham.getText().trim();
                String productName = txtTenLoaiSanPham.getText().trim();
                ProductType productType = new ProductType(idProductType, productName);
                if (mainController.updateProductType(productType)) {
                    DialogUtils.showSuccessMessage(this, "Cập nhật thành công");
                    refreshForm();
                    loadData();
                } else {
                    DialogUtils.showErrorMessage(this, "Cập nhật không thành công");
                }
//                }
            } else {
                DialogUtils.showErrorMessage(this, "Cập nhật không thành công");
            }
        } else {
            DialogUtils.showErrorMessage(this, "Vui lòng chọn một hàng");
        }
    }

    private void refreshForm() {
        txtTenLoaiSanPham.setText("");
        txtTimKiem.setText("");
        txtTenLoaiSanPham.selectAll();
        txtTenLoaiSanPham.requestFocus();
        tableSP.clearSelection();
        updateIdProductType();
    }

    private boolean validataField(JTextField textField, String regex, String errorMessage) {
//        String fieldValue = textField.getText().trim();
//        if (fieldValue.isEmpty()) {
//            showErrorDialog("Vui lòng nhập giá trị cho " + textField.getName() + "!");
//            textField.requestFocus();
//            return false;
//        }
//
//        if (!fieldValue.matches(regex)) {
//            showErrorDialog(errorMessage);
//            textField.requestFocus();
//            textField.selectAll();
//            return false;
//        }
//        return true;
        return true;
    }


    private boolean validataFields() {
        return validataField(txtTenLoaiSanPham, "^[\\p{L}\\s]+$",
                "Tên Loại Sản Phẩm không hợp lệ. Phải bắt đầu bằng chữ cái, không chấp nhận ký tự đặc biệt.");
    }

    @SneakyThrows
    private void addProductType() {
        String idProductType = txtIdLoaiSanPham.getText();
        String productName = txtTenLoaiSanPham.getText();
        ProductType productType = new ProductType(idProductType, productName);

        if (mainController.checkProductTypeExist(idProductType)) {
            DialogUtils.showErrorMessage(this, "ID loại sản phẩm trùng lặp. Vui lòng chọn một ID khác.");
        } else {
//            if (validateFields()) {
            if (mainController.addProductType(productType)) {
                modelSP.addRow(new Object[]{idProductType, productName});
                DialogUtils.showSuccessMessage(this, "Loại sản phẩm được thêm thành công");
                loadData();
                refreshForm();
            } else {
                DialogUtils.showErrorMessage(this, "Lỗi khi thêm loại sản phẩm");
            }
        }
//        }
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
        SwingUtilities.invokeLater(() -> {
            Object o = e.getSource();
            if (o.equals(txtTimKiem)) {
                DefaultTableModel model = (DefaultTableModel) tableSP.getModel();
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
                tableSP.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter("(?i)" + txtTimKiem.getText().trim(), 1));
            }
        });

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tableSP.getSelectedRow();
        if (row >= 0) {
            txtIdLoaiSanPham.setText(modelSP.getValueAt(row, 0).toString());
            txtTenLoaiSanPham.setText(modelSP.getValueAt(row, 1).toString());
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

}
