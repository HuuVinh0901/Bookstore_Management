package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controller.MainController;
import controller.MainControllerInterface;
import lombok.SneakyThrows;
import models.*;
import util.GeneratorIDAuto;


public class SaleManagerView extends JPanel implements ActionListener, MouseListener, KeyListener {
    private JPanel pnLeft;
    private JPanel pnRight;
    private JTextField txtMaSP;
    private JTextField txtMaSP2;
    private JTextField txtTimKiemSP;
    private JTextField txtMaHD;
    private JTextField txtTenNV;
    private JTextField txtTenKH;
    private JTextField txtTenKH2;
    private JTextField txtMaKH;
    private JTextField txtMaKH2;
    private JTextField txtTimKiemKH;
    private JTextField txtTongTienGioHang;
    private JTextField txtTienKhachDua;
    private JTextField txtTienTraKhach;
    private JTextField txtNgayLap;
    private JTextField txtThue;
    private JTextField txtCapNhatSoLuong;
    private JTextField txtTongTienHoaDon;
    private JTextField txtTimKiemHDCho;
    private JTable tblSanPham;
    private JTable tblGioHang;
    private JTable tblkhachHang;
    private JTable tblHangCho;
    private JButton btnThemGioHang;
    private JButton btnThemGioHang2;
    private JButton btnXoaGioHang;
    private JButton btnLamMoiGioHang;
    private JButton btnLuuHoaDon;
    private JButton btnXoaHangCho;
    private JButton btnChonThanhToan;
    private JButton btnLamMoiHangCho;
    private JButton btnThanhToan;
    private JButton btnLamMoiDonHang;
    private JButton btnChonKH;
    private JButton btnTimKiemSanPham;
    private JButton btnXemTatCaSanPham;
    private JButton btnXemTatCaKhachHang;
    private JButton btnDongSanPham;
    private JButton btnDongKhachHang;
    private JButton btnTimKhachHang;
    private JButton btnChonMaSanPham;
    private JButton btnCapNhatSoLuong;
    private JButton btnXacNhanCapNhat;
    private JButton btnXemTatCaHDC;
    private DefaultTableModel modelSP;
    private DefaultTableModel modelKH;
    private DefaultTableModel modelGioHang;
    private DefaultTableModel modelHangCho;

    private JSpinner spinnerSoLuong;
    private JSpinner spinnerSoLuong2;
    private JDialog dialogSanPham;
    private JDialog dialogKhachHang;
    private JDialog dialogSoLuong;
    private JComboBox<String> cbLocSanPham;
    private Date ngayNhap;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private JWindow windowMaSP;
    private JTable tblMaSp;
    private DefaultTableModel modelMaSP;
    private JFrame frameThongBao;
    private static MainControllerInterface mainController;

    static {
        try {
            mainController = new MainController();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private GeneratorIDAuto autoID;
    private Employee employee;

    @SneakyThrows
    public SaleManagerView(Employee e, MainControllerInterface mainController) {
        this.employee = e;
        this.mainController = mainController;
        autoID = new GeneratorIDAuto();
        // Xóa logo Eclipse ở phía trên hộp thoại
        UIManager.put("OptionPane.windowIcons", new ImageIcon[0]);
        currencyFormat.setCurrency(Currency.getInstance("VND"));
        setLayout(new GridBagLayout());
        pnLeft = new JPanel(new BorderLayout());
        pnRight = new JPanel(new BorderLayout());
//        JLabel lblTitleLeft = new JLabel("CHI TIẾT HOÁ ĐƠN");
        JLabel lblTitleRight = new JLabel("HOÁ ĐƠN");
//        JPanel pnTitleLeft = new JPanel();
        JPanel pnTitleRight = new JPanel();
        JPanel pnHeaderLeft = new JPanel(new BorderLayout());
        JPanel pnHeaderRight = new JPanel(new BorderLayout());
        JPanel pn1 = new JPanel();
        JPanel pn2 = new JPanel();
        JPanel pn3 = new JPanel();
        JPanel pn5 = new JPanel();
        JPanel pn6 = new JPanel();
        JPanel pn7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnSanPham = new JPanel(new GridLayout(2, 2, 5, 5));
        JPanel pnHoaDon = new JPanel(new GridLayout(4, 2, 5, 5));
        JLabel lblMaSP = new JLabel("Mã sản phẩm");
        JLabel lblSoLuong = new JLabel("Số lượng");
        JLabel lblTimKiemSP = new JLabel("Tìm kiếm theo Tên / Mã / Loại");
        JLabel lblMaHD = new JLabel("Mã đơn hàng");
        JLabel lblTenNV = new JLabel("Tên nhân viên");
        JLabel lblTenKH = new JLabel("Tên khách hàng");
        JLabel lblMaKH = new JLabel("Mã khách hàng");
        JPanel pnGioHang = new JPanel(new BorderLayout());
        JPanel pnXoaLamMoi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnChucNangHangCho = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel pnTblHangCho = new JPanel(new BorderLayout());
        JLabel lblNgayLap = new JLabel("Ngày lập");
        JLabel lblThue = new JLabel("Thuế VAT(10%)");
        JLabel lblTongTien = new JLabel("Tổng tiền giỏ hàng");
        JLabel lblTienKhachDua = new JLabel("Tiền khách đưa");
        JLabel lblTienTraKhach = new JLabel("Tiền trả lại khách");
        JLabel lblTongTienHoaDon = new JLabel("TỔNG TIỀN");
        JPanel pnTinhTien = new JPanel(new GridLayout(6, 2, 5, 5));
        JPanel pnFooterRight = new JPanel(new BorderLayout());
        JPanel pnLamMoiHD = new JPanel();
        JPanel pnLamThanhToan = new JPanel();
        JPanel pnTimKiemKH = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtMaSP = new JTextField(20);
        txtMaSP.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtMaSP2 = new JTextField(20);
        txtMaSP2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtMaHD = new JTextField(20);
        txtMaHD.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtMaHD.setEditable(false);
        txtTenNV = new JTextField(20);
        txtTenNV.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtTenNV.setEditable(false);
        txtTenKH = new JTextField(20);
        txtTenKH.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtTenKH.setEditable(false);
        txtTimKiemKH = new JTextField(20);
        txtTimKiemKH.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtTongTienGioHang = new JTextField(20);
        txtTongTienGioHang.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtTongTienGioHang.setEditable(false);
        txtTongTienGioHang.setText(currencyFormat.format(0.0));
        txtTongTienHoaDon = new JTextField(20);
        txtTongTienHoaDon.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtTongTienHoaDon.setEditable(false);
        txtTongTienHoaDon.setText(currencyFormat.format(0.0));
        txtTienKhachDua = new JTextField(20);
        txtTienKhachDua.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtTienTraKhach = new JTextField(20);
        txtTienTraKhach.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtTienTraKhach.setEditable(false);
        txtMaKH = new JTextField(20);
        txtMaKH.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtMaKH.setEditable(false);
        txtNgayLap = new JTextField(20);
        txtNgayLap.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtNgayLap.setEditable(false);
        txtThue = new JTextField(20);
        txtThue.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtThue.setEditable(false);
        txtThue.setText(currencyFormat.format(0.0));
        txtTienTraKhach.setText(currencyFormat.format(0.0));
        txtCapNhatSoLuong = new JTextField(10);
        txtCapNhatSoLuong.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtMaKH2 = new JTextField(20);
        txtMaKH2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtMaKH2.setEditable(false);
        txtTenKH2 = new JTextField(20);
        txtTenKH2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtTenKH2.setEditable(false);
//        lblTitleLeft.setFont(new Font("Tahoma", Font.BOLD, 30));
//        lblTitleLeft.setForeground(new Color(26, 102, 227));
        lblTitleRight.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTitleRight.setForeground(new Color(26, 102, 227));
        lblMaSP.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblSoLuong.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTimKiemSP.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblMaKH.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblMaHD.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTenNV.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTenKH.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNgayLap.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTongTien.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTienKhachDua.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTienTraKhach.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTongTienHoaDon.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTongTienHoaDon.setForeground(Color.red);
        lblThue.setFont(new Font("SansSerif", Font.BOLD, 14));
        spinnerSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spinnerSoLuong.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnTimKiemSanPham = new JButton("Xem tất cả sản phẩm");
        btnDongSanPham = new JButton("Đóng");
        btnDongKhachHang = new JButton("Đóng");
        btnXacNhanCapNhat = new JButton("Cập nhật");
        cbLocSanPham = new JComboBox<String>();
        cbLocSanPham.addItem("Sản Phẩm");
        cbLocSanPham.addItem("Sách");
        btnThemGioHang2 = new JButton("Thêm vào giỏ hàng");
        btnXemTatCaKhachHang = new JButton("Xem tất cả");
        btnChonKH = new JButton("Chọn khách hàng");
        btnXemTatCaSanPham = new JButton("Xem tất cả");
        tblkhachHang = new JTable();
        modelKH = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa các ô trong bảng
            }
        };
        tblSanPham = new JTable();
        modelSP = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        tblMaSp = new JTable();
        modelMaSP = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelMaSP.addColumn("Mã sản phẩm");
        tblMaSp.setModel(modelMaSP);
        JScrollPane scrollTblSP = new JScrollPane(tblMaSp);
        windowMaSP = new JWindow();
        windowMaSP.setLayout(new BorderLayout());
        btnChonMaSanPham = new JButton("Chọn");
        JPanel pnChonMaSanPham = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnChonMaSanPham.add(btnChonMaSanPham);
        windowMaSP.add(scrollTblSP, BorderLayout.CENTER);
        windowMaSP.add(pnChonMaSanPham, BorderLayout.SOUTH);


        ImageIcon iconThem = new ImageIcon(getClass().getResource("/icons/add.png"));
        ImageIcon iconLamMoi = new ImageIcon(getClass().getResource("/icons/lammoi.png"));
        ImageIcon iconXoa = new ImageIcon(getClass().getResource("/icons/xoa.png"));
        ImageIcon iconLuuVaIn = new ImageIcon(getClass().getResource("/icons/printing.png"));
        ImageIcon iconLuu = new ImageIcon(getClass().getResource("/icons/bookmark.png"));

//        pnTitleLeft.add(lblTitleLeft);
        pnSanPham.add(lblMaSP);
        pnSanPham.add(txtMaSP);
        pnSanPham.add(lblSoLuong);
        pnSanPham.add(spinnerSoLuong);
        pn1.add(pnSanPham);

        btnThemGioHang = new JButton("Thêm vào giỏ hàng");
        pn5.add(btnThemGioHang);
        pn5.add(btnTimKiemSanPham);
        pn6.add(pn5);
        pn7.add(pn6);
//        pnHeaderLeft.add(pnTitleLeft, BorderLayout.NORTH);
        pnHeaderLeft.add(pn1, BorderLayout.CENTER);
        pnHeaderLeft.add(pn7, BorderLayout.SOUTH);
        tblGioHang = new JTable();
        modelGioHang = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa các ô trong bảng
            }
        };
        modelGioHang.addColumn("Mã sản phẩm");
        modelGioHang.addColumn("Tên sản phẩm");
        modelGioHang.addColumn("Giá bán");
        modelGioHang.addColumn("Giá khuyến mãi");
        modelGioHang.addColumn("Số lượng");
        modelGioHang.addColumn("Thành tiền");

        tblGioHang.setModel(modelGioHang);
        JScrollPane scrollTblGH = new JScrollPane(tblGioHang);
        scrollTblGH.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));
        scrollTblGH.setPreferredSize(new Dimension(scrollTblGH.getPreferredSize().width, 170));
        btnXoaGioHang = new JButton("Xoá");

        btnLamMoiGioHang = new JButton("Làm mới giỏ hàng");
        btnLamMoiGioHang.setIcon(iconLamMoi);
        btnCapNhatSoLuong = new JButton("Cập nhật số lượng");
        btnLuuHoaDon = new JButton("Lưu");
        btnLuuHoaDon.setIcon(iconLuu);
        pnXoaLamMoi.add(btnXoaGioHang);
        pnXoaLamMoi.add(btnCapNhatSoLuong);
        pnXoaLamMoi.add(btnLamMoiGioHang);
        pnXoaLamMoi.add(btnLuuHoaDon);
        pnGioHang.add(scrollTblGH, BorderLayout.CENTER);
        pnGioHang.add(pnXoaLamMoi, BorderLayout.SOUTH);
        pnLeft.add(pnHeaderLeft, BorderLayout.CENTER);
        pnLeft.add(pnTblHangCho, BorderLayout.SOUTH);
        pnLeft.setBorder(BorderFactory.createTitledBorder("Chọn sản phẩm"));


        btnTimKhachHang = new JButton("Chọn khách hàng");
        pnTimKiemKH.add(btnTimKhachHang);
        pnTitleRight.add(lblTitleRight);
        pnHoaDon.add(lblMaHD);
        pnHoaDon.add(txtMaHD);
        pnHoaDon.add(lblTenNV);
        pnHoaDon.add(txtTenNV);
        pnHoaDon.add(lblTenKH);
        pnHoaDon.add(txtTenKH);
        pnHoaDon.add(lblMaKH);
        pnHoaDon.add(txtMaKH);
        pn2.add(pnHoaDon);
        pnHeaderRight.add(pnTitleRight, BorderLayout.NORTH);
        pnHeaderRight.add(pn2, BorderLayout.CENTER);
        pnHeaderRight.add(pnTimKiemKH, BorderLayout.SOUTH);

        JPanel pnTimKiemHDC = new JPanel(new GridLayout(1, 4, 5, 5));
        txtTimKiemHDCho = new JTextField();
        JLabel lbTimKiemHDC = new JLabel("Tìm kiếm hóa đơn chờ:");
        btnXemTatCaHDC = new JButton("Xem tất cả hóa đơn chờ");
        pnTimKiemHDC.add(lbTimKiemHDC);
        pnTimKiemHDC.add(txtTimKiemHDCho);
        pnTimKiemHDC.add(btnXemTatCaHDC);
        tblHangCho = new JTable();
        modelHangCho = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelHangCho.addColumn("Mã hoá đơn");
        modelHangCho.addColumn("Tên khách hàng");
        modelHangCho.addColumn("Mã khách hàng");
        modelHangCho.addColumn("Số điện thoại");
        modelHangCho.addColumn("Ngày lập");
        tblHangCho.setModel(modelHangCho);
        JScrollPane scrollTblHangDoi = new JScrollPane(tblHangCho);
        scrollTblHangDoi.setBorder(BorderFactory.createTitledBorder("Đơn hàng chờ thanh toán"));


        btnXoaHangCho = new JButton("Xoá");
        btnXoaHangCho.setIcon(iconXoa);
        btnChonThanhToan = new JButton("Chọn thanh toán");
        btnXoaGioHang.setIcon(iconXoa);
        btnLamMoiHangCho = new JButton("Làm mới hàng chờ");
        btnLamMoiHangCho.setIcon(iconLamMoi);
        pnChucNangHangCho.add(btnChonThanhToan);

        pnChucNangHangCho.add(btnXoaHangCho);
        pnChucNangHangCho.add(btnLamMoiHangCho);
        pnTblHangCho.add(pnTimKiemHDC, BorderLayout.NORTH);
        pnTblHangCho.add(scrollTblHangDoi, BorderLayout.CENTER);
        pnTblHangCho.add(pnChucNangHangCho, BorderLayout.SOUTH);

        ngayNhap = new Date();
        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(ngayNhap);
        txtNgayLap.setText(currentDate);
        pnTinhTien.add(lblNgayLap);
        pnTinhTien.add(txtNgayLap);
        pnTinhTien.add(lblTongTien);
        pnTinhTien.add(txtTongTienGioHang);
        pnTinhTien.add(lblThue);
        pnTinhTien.add(txtThue);
        pnTinhTien.add(lblTongTienHoaDon);
        pnTinhTien.add(txtTongTienHoaDon);
        pnTinhTien.add(lblTienKhachDua);
        pnTinhTien.add(txtTienKhachDua);
        pnTinhTien.add(lblTienTraKhach);
        pnTinhTien.add(txtTienTraKhach);

        pn3.add(pnTinhTien);
        pnFooterRight.add(pn3, BorderLayout.NORTH);
        pnFooterRight.add(pnLamMoiHD, BorderLayout.CENTER);
        btnThanhToan = new JButton("THANH TOÁN & IN");
        btnThanhToan.setIcon(iconLuuVaIn);
        btnThanhToan.setPreferredSize(new Dimension(200, 35));
        btnLamMoiDonHang = new JButton("Làm mới hoá đơn");
        btnLamMoiDonHang.setIcon(iconLamMoi);
        pnLamThanhToan.add(btnThanhToan);
        pnLamThanhToan.add(btnLamMoiDonHang);
        pnFooterRight.add(pnLamThanhToan, BorderLayout.SOUTH);
        pnRight.add(pnHeaderRight, BorderLayout.NORTH);

        pnRight.add(pnGioHang, BorderLayout.CENTER);
        pnRight.add(pnFooterRight, BorderLayout.SOUTH);
        pnRight.setBorder(BorderFactory.createTitledBorder(""));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(pnLeft, gbc);


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(pnRight, gbc);

        btnChonMaSanPham.addActionListener(this);
        btnThemGioHang.addActionListener(this);
        btnThemGioHang2.addActionListener(this);
        btnXoaGioHang.addActionListener(this);
        btnLamMoiGioHang.addActionListener(this);
        tblGioHang.addMouseListener(this);
        tblSanPham.addMouseListener(this);
        tblkhachHang.addMouseListener(this);
        btnThanhToan.addActionListener(this);
        btnLamMoiDonHang.addActionListener(this);
        btnLuuHoaDon.addActionListener(this);
        btnChonThanhToan.addActionListener(this);
        btnXoaHangCho.addActionListener(this);
        btnLamMoiHangCho.addActionListener(this);
        btnTimKiemSanPham.addActionListener(this);
        btnDongSanPham.addActionListener(this);
        btnDongKhachHang.addActionListener(this);
        btnTimKhachHang.addActionListener(this);
        cbLocSanPham.addActionListener(this);
        btnCapNhatSoLuong.addActionListener(this);
        btnXacNhanCapNhat.addActionListener(this);
        btnXemTatCaKhachHang.addActionListener(this);
        btnChonKH.addActionListener(this);
        btnXemTatCaHDC.addActionListener(this);
        btnXemTatCaSanPham.addActionListener(this);

        txtTienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Không sử dụng cho JTextField
            }
        });


        txtMaSP.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                modelMaSP.setRowCount(0);
                handleSearchProductById(txtMaSP.getText().trim());
                if (!txtMaSP.getText().equals("")) {
                    windowMaSP.setLocation(txtMaSP.getLocationOnScreen().x, txtMaSP.getLocationOnScreen().y + txtMaSP.getHeight());
                    windowMaSP.pack();
                    windowMaSP.setSize(txtMaSP.getWidth(), windowMaSP.getHeight() / 2);
                    windowMaSP.setVisible(true);
                } else {
                    windowMaSP.setVisible(false);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                modelMaSP.setRowCount(0);
                handleSearchProductById(txtMaSP.getText().trim());
                if (!txtMaSP.getText().equals("")) {
                    windowMaSP.setLocation(txtMaSP.getLocationOnScreen().x, txtMaSP.getLocationOnScreen().y + txtMaSP.getHeight());
                    windowMaSP.pack();
                    windowMaSP.setSize(txtMaSP.getWidth(), windowMaSP.getHeight() / 2);
                    windowMaSP.setVisible(true);
                } else {
                    windowMaSP.setVisible(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        modelGioHang.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.INSERT || e.getType() == TableModelEvent.DELETE || e.getType() == TableModelEvent.UPDATE) {
                    tinhTongThanhTien();
                    tinhThue();
                    tinhTongTienHoaDon();
                    tinhTienTraKhach();
                }

            }
        });

        ListSelectionModel selectionModel = tblGioHang.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    tinhTongThanhTien();
                    tinhThue();
                    tinhTongTienHoaDon();
                    tinhTienTraKhach();
                }

            }
        });
        txtTenNV.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                txtMaHD.setText(autoID.autoID("HD"));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        txtTienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!txtTienKhachDua.getText().equals("") && txtTienKhachDua.getText().matches("\\d+")) {
                    tinhTienTraKhach();
                } else if (txtTienKhachDua.getText().equals("")) {
                    txtTienTraKhach.setText(currencyFormat.format(0.0));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!txtTienKhachDua.getText().equals("") && txtTienKhachDua.getText().matches("\\d+")) {
                    tinhTienTraKhach();
                } else if (txtTienKhachDua.getText().equals("")) {
                    txtTienTraKhach.setText(currencyFormat.format(0.0));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        txtTimKiemHDCho.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                modelHangCho.setRowCount(0);
                handleSearchBillPending(txtTimKiemHDCho.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                modelHangCho.setRowCount(0);
                handleSearchBillPending(txtTimKiemHDCho.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        loadBillPending();
    }

    @SneakyThrows
    private void handleSearchBillPending(String cond) {
        if (!cond.equals("")) {
            for (BillPending billPending : mainController.findBillPendingByText(cond)) {
                String ngayLap = new SimpleDateFormat("dd/MM/yyyy").format(billPending.getNgayLap());
                modelHangCho.addRow(new Object[]{
                        billPending.getBillId(), billPending.getCustomer().getName(),
                        billPending.getCustomer().getIdCustomer(), billPending.getCustomer().getPhone(),
                        ngayLap
                });
            }
        } else {
            loadBillPending();
        }
    }

    private void tinhTienTraKhach() {
        if (!txtTienKhachDua.getText().equals("")) {
            Double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText());
            String tongThanhTienHoaDon = txtTongTienHoaDon.getText();
            double tongThanhTienHoaDonDouble = Double.parseDouble(tongThanhTienHoaDon.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
            if (tienKhachDua - tongThanhTienHoaDonDouble < 0) {
                txtTienTraKhach.setText(currencyFormat.format(0.0));
            } else {
                txtTienTraKhach.setText(currencyFormat.format(tienKhachDua - tongThanhTienHoaDonDouble));
            }
        }
    }

    private void tinhTongTienHoaDon() {
        String thue = txtThue.getText();
        double thueDouble = Double.parseDouble(thue.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
        String tongThanhTienGioHang = txtTongTienGioHang.getText();
        double tongThanhTienGioHangDouble = Double.parseDouble(tongThanhTienGioHang.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
        txtTongTienHoaDon.setText(currencyFormat.format(tongThanhTienGioHangDouble + thueDouble));
    }

    private void tinhThue() {
        String tongThanhTienGioHang = txtTongTienGioHang.getText();
        double tongThanhTienGioHangDouble = Double.parseDouble(tongThanhTienGioHang.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
        String thue = currencyFormat.format(tongThanhTienGioHangDouble * 0.1);
        txtThue.setText(thue);
    }

    private void tinhTongThanhTien() {
        int row = modelGioHang.getRowCount();
        double tongTienHoaDon = 0;
        if (row > 0) {
            for (int i = 0; i < row; i++) {
                String thanhTien = modelGioHang.getValueAt(i, 5).toString();
                double thanhTienDouble = Double.parseDouble(thanhTien.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
                tongTienHoaDon += thanhTienDouble;
            }
            txtTongTienGioHang.setText(currencyFormat.format(tongTienHoaDon));
        } else {
            txtTongTienGioHang.setText(currencyFormat.format(0.0));
        }
    }

    @SneakyThrows
    private void loadCustomer() {
        for (Customer customer : mainController.getAllCustomers()) {
            String ngaySinh = new SimpleDateFormat("dd/MM/yyyy").format(customer.getBirth());
            modelKH.addRow(new String[]{customer.getIdCustomer(), customer.getName(), customer.getPhone(), customer.getEmail(), customer.getAddress(), ngaySinh, customer.getGender()
            });
        }
    }

    @SneakyThrows
    private void loadProduct() {
        modelSP.setRowCount(0);
        List<Merchandise> merchandise = mainController.getAllMerchandise();
        for (Merchandise sanPham : merchandise) {
            String idSanPham = sanPham.getProductId();
            String tenSanPham = sanPham.getProductName();
            String tenLoaiSanPham = sanPham.getProductTypeId().getProductTypeName();
            String tenNhaCungCap = sanPham.getSupplierId().getSupplierName();
            double kichThuoc = sanPham.getSize();
            String mauSac = sanPham.getColor();
            String trangThai = sanPham.getStatus().getDescription() + "";
            int soLuong = sanPham.getQuantity();
            double giaBan = sanPham.sellingPrice();
            modelSP.addRow(new Object[]{idSanPham, tenSanPham, tenLoaiSanPham, tenNhaCungCap, kichThuoc, mauSac,
                    trangThai, soLuong, currencyFormat.format(giaBan)});
        }
    }

    @SneakyThrows
    private void loadBook() {
        modelSP.setRowCount(0);
        List<Book> ds = mainController.getAllBook();
        for (Book s : ds) {
            String idSanPham = s.getProductId();
            String tenSanPham = s.getProductName();
            String tenTacGia = s.getAuthorId().getAuthorName();
            String tenTheLoai = s.getCategoryId().getCategoryName();
            LocalDate namXuatBan = s.getPublicationYear();
            String ISBN = s.getISBN();
            int soTrang = s.getNumberOfPages();
            String loaiSanPham = s.getProductTypeId().getProductTypeId();
            String nhaCungCap = s.getSupplierId().getSupplierId();
            double kichThuoc = s.getSize();
            String mauSac = s.getColor();
            String trangThai = s.getStatus().getDescription();
            int soLuong = s.getQuantity();
            double giaBan = s.sellingPrice();
//
            modelSP.addRow(new Object[]{idSanPham, tenSanPham, tenTacGia, tenTheLoai, namXuatBan, ISBN, soTrang,
                    loaiSanPham, nhaCungCap, kichThuoc, mauSac, trangThai, soLuong
                    , currencyFormat.format(giaBan)});
        }
    }

    @SneakyThrows
    private void handleSearchCustomer(String cond) {
        if (!cond.equals("")) {
            for (Customer customer : mainController.findCustomerByText(cond)) {
                String ngaySinh = new SimpleDateFormat("dd/MM/yyyy").format(customer.getBirth());
                modelKH.addRow(new String[]{customer.getIdCustomer(), customer.getName(), customer.getPhone(), customer.getEmail(), customer.getAddress(), ngaySinh, customer.getGender()
                });
            }
        } else {
            loadCustomer();
        }
    }

    private void handleSelectiveProduct() {
        if (cbLocSanPham.getSelectedIndex() == 0) {
            modelSP.setColumnCount(0);
            modelSP.addColumn("ID Sản Phẩm");
            modelSP.addColumn("Tên Sản Phẩm");
            modelSP.addColumn("Loại sản phẩm");
            modelSP.addColumn("Nhà Cung Cấp");
            modelSP.addColumn("Kích Thước");
            modelSP.addColumn("Màu Sắc");
            modelSP.addColumn("Trạng Thái");
            modelSP.addColumn("Số Lượng");
            modelSP.addColumn("Giá Bán");
            modelSP.setRowCount(0);
            loadProduct();
            txtTimKiemSP.setText("");
        } else {
            modelSP.setColumnCount(0);
            modelSP.addColumn("ID Sách");
            modelSP.addColumn("Tên Sách");
            modelSP.addColumn("Tác Giả");
            modelSP.addColumn("Thể Loại");
            modelSP.addColumn("Năm Xuất Bản");
            modelSP.addColumn("ISBN");
            modelSP.addColumn("Số Trang");
            modelSP.addColumn("Loại Sản Phẩm");
            modelSP.addColumn("Nhà Cung Cấp");
            modelSP.addColumn("Kích Thước");
            modelSP.addColumn("Màu Sắc");
            modelSP.addColumn("Trạng Thái");
            modelSP.addColumn("Số Lượng");
            modelSP.addColumn("Giá Bán");
            modelSP.setRowCount(0);
            loadBook();
            txtTimKiemSP.setText("");
        }
    }

    @SneakyThrows
    private void handleSearchProduct(String cond) {
        if (!cond.equals("")) {
            modelSP.setRowCount(0);
            List<Product> ds = mainController.getProductByText(cond);
            for (Product sanPham : ds) {
                String idSanPham = sanPham.getProductId();
                String tenSanPham = sanPham.getProductName();
                String tenLoaiSanPham = sanPham.getProductTypeId().getProductTypeId();
                String tenNhaCungCap = sanPham.getSupplierId().getSupplierId();
                double kichThuoc = sanPham.getSize();
                String mauSac = sanPham.getColor();
                String trangThai = sanPham.getStatus().getDescription() + "";
                double thue = sanPham.tax();
                int soLuong = sanPham.getQuantity();
                double giaBan = sanPham.sellingPrice();
                ProductSale spKM = mainController.getProductSale(sanPham.getProductId());
                double giaKM;
                if (spKM != null) {
                    giaKM = spKM.getGiaBan();
                } else {
                    giaKM = giaBan;
                }
                modelSP.addRow(new Object[]{idSanPham, tenSanPham, tenLoaiSanPham, tenNhaCungCap, kichThuoc, mauSac,
                        trangThai, currencyFormat.format(thue), soLuong, currencyFormat.format(giaBan), currencyFormat.format(giaKM)});
            }
        }
    }

    private void handleChooseCustomer() {
        int rowKH = tblkhachHang.getSelectedRow();
        if (rowKH >= 0) {
            txtTenKH.setText(modelKH.getValueAt(rowKH, 1).toString());
            txtMaKH.setText(modelKH.getValueAt(rowKH, 0).toString());
            dialogKhachHang.dispose();
            modelKH.setColumnCount(0);
            modelKH.setRowCount(0);
            txtTenNV.setText(employee.getName());
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng");
        }
    }

    @SneakyThrows
    private void handleSearchBook(String cond) {
        if (!cond.equals("")) {
            modelSP.setRowCount(0);
            List<Book> ds = mainController.searchBook(cond);
            for (Book s : ds) {
                String idSanPham = s.getProductId();
                String tenSanPham = s.getProductName();
                String tenTacGia = s.getAuthorId().getAuthorName();
                String tenTheLoai = s.getCategoryId().getCategoryName();
                LocalDate namXuatBan = s.getPublicationYear();
                String ISBN = s.getISBN();
                int soTrang = s.getNumberOfPages();
                String loaiSanPham = s.getProductTypeId().getProductTypeId();
                String nhaCungCap = s.getSupplierId().getSupplierId();
                double kichThuoc = s.getSize();
                String mauSac = s.getColor();
                String trangThai = s.getStatus().getDescription();
                double thue = s.tax();
                int soLuong = s.getQuantity();
                double giaBan = s.sellingPrice();
                ProductSale spKM = mainController.getProductSale(s.getProductId());
                double giaKM;
                if (spKM != null) {
                    giaKM = spKM.getGiaBan();
                } else {
                    giaKM = giaBan;
                }
                modelSP.addRow(new Object[]{idSanPham, tenSanPham, tenTacGia, tenTheLoai, namXuatBan, ISBN, soTrang,
                        loaiSanPham, nhaCungCap, kichThuoc, mauSac, trangThai, currencyFormat.format(thue), soLuong
                        , currencyFormat.format(giaBan), currencyFormat.format(giaKM)});
            }
        }
    }

    private void showThongBao(String message) {
        frameThongBao = new JFrame();
        frameThongBao.setUndecorated(true);
        frameThongBao.setBackground(new Color(0, 0, 0, 0));
        frameThongBao.setAlwaysOnTop(true);

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setForeground(Color.gray);
        frameThongBao.add(label);

        frameThongBao.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                fadeIn();
            }
        });

        frameThongBao.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = frameThongBao.getSize().width;
        int frameHeight = frameThongBao.getSize().height;
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;
        frameThongBao.setLocation(x, y);

        frameThongBao.setVisible(true);
    }


    private void fadeIn() {
        Timer timer = new Timer(20, new ActionListener() {
            float opacity = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (opacity < 1) {
                    frameThongBao.setOpacity(opacity);
                    opacity += 0.05f;
                } else {
                    ((Timer) e.getSource()).stop();
                    fadeOut();
                }
            }
        });
        timer.start();
    }


    private void fadeOut() {
        Timer timer = new Timer(20, new ActionListener() {
            float opacity = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (opacity > 0) {
                    frameThongBao.setOpacity(opacity);
                    opacity -= 0.05f;
                } else {
                    ((Timer) e.getSource()).stop();
                    frameThongBao.dispose();
                }
            }
        });
        timer.setInitialDelay(100);
        timer.start();
    }

    @SneakyThrows
    private void addCartBySearch(int row) {
        String soLuong = String.valueOf(spinnerSoLuong2.getValue());
        if (cbLocSanPham.getSelectedIndex() == 0) {
            int soLuongTrongKho = Integer.parseInt(modelSP.getValueAt(row, 7).toString());
            String trangThaiKinhDoanh = modelSP.getValueAt(row, 6).toString();
            if (trangThaiKinhDoanh.equals("Đang kinh doanh")) {
                if (Integer.parseInt(soLuong) <= soLuongTrongKho) {
                    ProductSale productSale = mainController.getProductSale(modelSP.getValueAt(row, 0).toString());

                    String idSP = modelSP.getValueAt(row, 0).toString();
                    String tenSP = modelSP.getValueAt(row, 1).toString();
                    String giaBanValue = modelSP.getValueAt(row, 8).toString();
                    String giaBanDouble = giaBanValue.trim().replace("\u00A0", "").replaceAll("[.,₫]", "");
                    Double giaBan = Double.parseDouble(giaBanDouble);
                    double giaKM = 0;
                    if (productSale != null) {
                        Promotion promotion = mainController.findPromotionById(productSale.getPromotion().getPromotionId());
                        LocalDate dateStartPromotion = promotion.getPromotionStartDate();
                        LocalDate dateEndPromotion = promotion.getPromotionEndDate();
                        if (dateStartPromotion.isAfter(LocalDate.now()) || dateEndPromotion.isBefore(LocalDate.now())) {
                            giaKM = giaBan;
                        } else {
                            giaKM = productSale.getGiaBan();
                        }
                    } else {
                        giaKM = giaBan;

                    }
                    String thanhTien = currencyFormat.format(giaKM * Integer.parseInt(soLuong));
                    modelGioHang.addRow(new Object[]{
                            idSP, tenSP, currencyFormat.format(giaBan), currencyFormat.format(giaKM), soLuong, thanhTien
                    });
                    showThongBao("Đã thêm vào giỏ hàng");
                    spinnerSoLuong2.setValue(1);
                    txtTimKiemSP.setText("");
                    txtMaSP2.setText("");
                } else {
                    JOptionPane.showMessageDialog(dialogSanPham, "Số lượng sản phẩm trong kho không đủ");
                }
            } else {
                JOptionPane.showMessageDialog(dialogSanPham, "Sản phẩm đã ngừng kinh doanh");
            }

        } else {
            int soLuongTrongKho = Integer.parseInt(modelSP.getValueAt(row, 12).toString());
            String trangThaiKinhDoanh = modelSP.getValueAt(row, 11).toString();
            if (trangThaiKinhDoanh.equals("Đang kinh doanh")) {
                if (Integer.parseInt(soLuong) <= soLuongTrongKho) {
                    ProductSale productSale = mainController.getProductSale(modelSP.getValueAt(row, 0).toString());
                    String idSP = modelSP.getValueAt(row, 0).toString();
                    String tenSP = modelSP.getValueAt(row, 1).toString();
                    String giaBanValue = modelSP.getValueAt(row, 13).toString();
                    String giaBanDouble = giaBanValue.trim().replace("\u00A0", "").replaceAll("[.,₫]", "");
                    Double giaBan = Double.parseDouble(giaBanDouble);
                    double giaKM = 0;
                    if (productSale != null) {
                        Promotion promotion = mainController.findPromotionById(productSale.getPromotion().getPromotionId());
                        LocalDate dateStartPromotion = promotion.getPromotionStartDate();
                        LocalDate dateEndPromotion = promotion.getPromotionEndDate();
                        if (dateStartPromotion.isAfter(LocalDate.now()) || dateEndPromotion.isBefore(LocalDate.now())) {
                            giaKM = giaBan;
                        } else {
                            giaKM = productSale.getGiaBan();
                        }
                    } else {
                        giaKM = giaBan;

                    }
                    String thanhTien = currencyFormat.format(giaKM * Integer.parseInt(soLuong));
                    modelGioHang.addRow(new Object[]{
                            idSP, tenSP, currencyFormat.format(giaBan), currencyFormat.format(giaKM), soLuong, thanhTien
                    });
                    showThongBao("Đã thêm vào giỏ hàng");
                    spinnerSoLuong2.setValue(1);
                    txtTimKiemSP.setText("");
                    txtMaSP2.setText("");
                } else {
                    JOptionPane.showMessageDialog(dialogSanPham, "Số lượng sách trong kho không đủ");
                }
            } else {
                JOptionPane.showMessageDialog(dialogSanPham, "Sách đã ngừng kinh doanh");
            }
        }
    }

    @SneakyThrows
    private void addCartById() {
        Product product = mainController.getProductById(txtMaSP.getText().trim());
        int soLuong = (int) spinnerSoLuong.getValue();

        if (product != null) {
            if (product.getStatus().getDescription().equals("Đang kinh doanh")) {
                if (soLuong <= product.getQuantity()) {
                    ProductSale productSale = mainController.getProductSale(txtMaSP.getText().trim());
                    String idSP = product.getProductId();
                    String tenSP = product.getProductName();
                    Double giaBan = product.sellingPrice();
                    double giaKM = 0;

                    if (productSale != null) {
                        Promotion promotion = mainController.findPromotionById(productSale.getPromotion().getPromotionId());
                        LocalDate dateStartPromotion = promotion.getPromotionStartDate();
                        LocalDate dateEndPromotion = promotion.getPromotionEndDate();
                        if (dateStartPromotion.isAfter(LocalDate.now()) || dateEndPromotion.isBefore(LocalDate.now())) {
                            giaKM = giaBan;
                        } else {
                            giaKM = productSale.getGiaBan();
                        }
                    } else {
                        giaKM = giaBan;

                    }
                    String thanhTien = currencyFormat.format(giaKM * soLuong);
                    modelGioHang.addRow(new Object[]{
                            idSP, tenSP, currencyFormat.format(giaBan), currencyFormat.format(giaKM), soLuong, thanhTien
                    });
                    spinnerSoLuong.setValue(1);
                    txtMaSP.setText("");
                    showThongBao("Đã thêm vào giỏ hàng");
                    spinnerSoLuong.setValue(1);
                    txtMaSP.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Số lượng trong kho không đủ");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã ngừng kinh doanh");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm không tồn tại");
            txtMaSP.setText("");
        }
    }

    private void handleAddCartBySearch() {
        if (!txtMaKH.getText().equals("")) {
            int row = tblSanPham.getSelectedRow();
            if (row >= 0) {
                int rowCountGioHang = modelGioHang.getRowCount();
                int flag = -1;
                Boolean isExists = false;
                if (rowCountGioHang > 0) {
                    for (int i = 0; i < rowCountGioHang; ++i) {
                        if (modelGioHang.getValueAt(i, 0).toString().equals(modelSP.getValueAt(row, 0).toString())) {
                            isExists = true;
                            flag = i;
                            break;
                        }
                    }
                    if (!isExists) {
                        addCartBySearch(row);
                    } else {
                        int soLuongCu = Integer.parseInt(modelGioHang.getValueAt(flag, 4).toString());
                        int SoLuongMoi = soLuongCu + Integer.parseInt(spinnerSoLuong2.getValue().toString());

                        modelGioHang.setValueAt(SoLuongMoi, flag, 4);
                        showThongBao("Đã thêm vào giỏ hàng");
                        totalCart();
                        spinnerSoLuong2.setValue(1);
                        txtTimKiemSP.setText("");
                        txtMaSP2.setText("");
                    }
                } else {
                    addCartBySearch(row);
                }
            } else {
                JOptionPane.showMessageDialog(dialogSanPham, "Vui lòng chọn sản phẩm để thêm vào giỏ hàng");
            }
        } else {
            showThongBao("Vui lòng chọn khách hàng để thêm vào giỏ hàng");
            showDialogKhachHang();
        }


    }

    private void handleAddCartById() {
        if (!txtMaKH.getText().equals("")) {
            if (!txtMaSP.getText().equals("")) {
                int rowCountGioHang = modelGioHang.getRowCount();
                int flag = -1;
                Boolean isExists = false;
                if (rowCountGioHang > 0) {
                    for (int i = 0; i < rowCountGioHang; ++i) {
                        if (modelGioHang.getValueAt(i, 0).toString().equals(txtMaSP.getText())) {
                            isExists = true;
                            flag = i;
                            break;
                        }
                    }
                }
                if (!isExists) {
                    addCartById();
                } else {
                    int soLuongCu = Integer.parseInt(modelGioHang.getValueAt(flag, 4).toString());
                    int SoLuongMoi = soLuongCu + Integer.parseInt(spinnerSoLuong2.getValue().toString());

                    modelGioHang.setValueAt(SoLuongMoi, flag, 4);
                    showThongBao("Đã thêm vào giỏ hàng");
                    totalCart();
                    spinnerSoLuong.setValue(1);
                    txtMaSP.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng điền mã để thêm vào giỏ hàng");
            }
        } else {
            showThongBao("Vui lòng chọn khách hàng để thêm vào giỏ hàng");
            showDialogKhachHang();
        }
    }

    private void deleteCart() {
        int rowSelectedGioHang = tblGioHang.getSelectedRow();
        int rowCountGioHang = modelGioHang.getRowCount();
        if (rowCountGioHang > 0) {
            if (rowSelectedGioHang >= 0) {
                modelGioHang.removeRow(rowSelectedGioHang);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xoá");
            }
        } else {
            showThongBao("Giỏ hàng rỗng");
        }

    }

    private void totalCart() {
        int row = modelGioHang.getRowCount();
        if (row > 0) {
            for (int i = 0; i < row; i++) {
                String giaBanSauKM = modelGioHang.getValueAt(i, 3).toString();
                String giaKMDouble = giaBanSauKM.trim().replace("\u00A0", "").replaceAll("[.,₫]", "");
                String soLuong = modelGioHang.getValueAt(i, 4).toString();
                String thanhTien = currencyFormat.format(Double.parseDouble(giaKMDouble) * Integer.parseInt(soLuong));
                modelGioHang.setValueAt(thanhTien, i, 5);
            }
        }
    }

    private void showDialogSanPham() {
        dialogSanPham = new JDialog();
        dialogSanPham.setLayout(new BorderLayout());
        dialogSanPham.setUndecorated(true);
        txtTimKiemSP = new JTextField(20);
        txtTimKiemSP.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtMaSP2 = new JTextField(20);
        txtMaSP2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtMaSP2.setEditable(false);
        spinnerSoLuong2 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spinnerSoLuong2.setFont(new Font("SansSerif", Font.BOLD, 14));
        JPanel pnMain = new JPanel(new BorderLayout());
        JPanel pn1 = new JPanel(new BorderLayout());
        JPanel pnTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnLocTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnLoc = new JPanel();
        JPanel pnTimKiem = new JPanel();
        JPanel pnTitle = new JPanel();
        JPanel pnBot = new JPanel(new BorderLayout());
        JPanel pnMaSoLuong = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnThemLamMoi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pn2 = new JPanel(new BorderLayout());
        JPanel pnDong = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnMa = new JPanel();
        JPanel pnSoLuong = new JPanel();
        JLabel lblTitle = new JLabel("Tìm kiếm sản phẩm");
        JLabel lblTimKiem;
        JLabel lblMaSP;
        JLabel lblSoLuong;
        JLabel lblTieuChiLoc;

        lblTimKiem = new JLabel("Tìm kiếm theo Tên / Mã / Loại");
        lblTimKiem.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTitle.setForeground(new Color(26, 102, 227));
        lblMaSP = new JLabel("Mã sản phẩm");
        lblMaSP.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblSoLuong = new JLabel("Số lượng");
        lblSoLuong.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTieuChiLoc = new JLabel("Tiêu chí lọc");
        lblTieuChiLoc.setFont(new Font("SansSerif", Font.BOLD, 14));
        pnLoc.add(lblTieuChiLoc);
        pnLoc.add(Box.createHorizontalStrut(10));
        pnLoc.add(cbLocSanPham);
        pnLoc.add(Box.createHorizontalStrut(40));
        pnTimKiem.add(lblTimKiem);
        pnTimKiem.add(Box.createHorizontalStrut(10));
        pnTimKiem.add(txtTimKiemSP);
        pnLocTimKiem.add(pnLoc);
        pnLocTimKiem.add(pnTimKiem);
        pnTop.add(pnLocTimKiem, BorderLayout.CENTER);
        pnTop.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        pnTitle.add(lblTitle);
        pn1.add(pnTop, BorderLayout.CENTER);
        pn1.add(pnTitle, BorderLayout.NORTH);
        pnMain.add(pn1, BorderLayout.NORTH);
        pnMain.setBorder(BorderFactory.createTitledBorder(""));
        tblSanPham.setModel(modelSP);
        JScrollPane scrollTblSP = new JScrollPane(tblSanPham);
        scrollTblSP.setBorder(BorderFactory.createTitledBorder("Sản phẩm"));
        pnMain.add(scrollTblSP, BorderLayout.CENTER);
        handleSelectiveProduct();

        pnMa.add(lblMaSP);
        pnMa.add(Box.createHorizontalStrut(20));
        pnMa.add(txtMaSP2);
        pnMa.add(Box.createHorizontalStrut(20));
        pnSoLuong.add(lblSoLuong);
        pnSoLuong.add(Box.createHorizontalStrut(10));
        pnSoLuong.add(spinnerSoLuong2);
        pnMaSoLuong.add(pnMa);
        pnMaSoLuong.add(pnSoLuong);
        pnBot.add(pnMaSoLuong, BorderLayout.CENTER);
        pnThemLamMoi.add(btnThemGioHang2);
        pnThemLamMoi.add(btnXemTatCaSanPham);
        pnDong.add(btnDongSanPham);
        pn2.add(pnThemLamMoi, BorderLayout.CENTER);
        pn2.add(pnDong, BorderLayout.EAST);
        pnBot.add(pn2, BorderLayout.SOUTH);
        pnMaSoLuong.setBorder(BorderFactory.createTitledBorder("Số lượng"));
        pnThemLamMoi.setBorder(BorderFactory.createTitledBorder(""));
        pnDong.setBorder(BorderFactory.createTitledBorder(""));
        pnMain.add(pnBot, BorderLayout.SOUTH);
        modelSP.setRowCount(0);
        if (cbLocSanPham.getSelectedIndex() == 0) {
            loadProduct();
        } else {
            loadBook();
        }
        txtTimKiemSP.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!txtTimKiemSP.getText().equals("")) {
                    if (cbLocSanPham.getSelectedIndex() == 0) {
                        handleSearchProduct(txtTimKiemSP.getText().trim());
                    } else {
                        handleSearchBook(txtTimKiemSP.getText().trim());
                    }
                } else {
                    modelSP.setRowCount(0);
                    if (cbLocSanPham.getSelectedIndex() == 0) {
                        loadProduct();
                    } else {
                        loadBook();
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!txtTimKiemSP.getText().equals("")) {
                    if (cbLocSanPham.getSelectedIndex() == 0) {
                        handleSearchProduct(txtTimKiemSP.getText().trim());
                    } else {
                        handleSearchBook(txtTimKiemSP.getText().trim());
                    }
                } else {
                    modelSP.setRowCount(0);
                    if (cbLocSanPham.getSelectedIndex() == 0) {
                        loadProduct();
                    } else {
                        loadBook();
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Không sử dụng cho JTextField
            }
        });
        dialogSanPham.add(pnMain);
        Dimension frameSize = this.getSize();
        int frameWidth = frameSize.width;
        int frameHeight = frameSize.height;
        dialogSanPham.setSize(frameWidth, frameHeight);
        dialogSanPham.setLocationRelativeTo(this);
        dialogSanPham.setVisible(true);
    }

    private void chonMaSanPham() {
        if (modelMaSP.getRowCount() > 0) {
            int rowSP = tblMaSp.getSelectedRow();
            if (rowSP >= 0) {
                txtMaSP.setText(modelMaSP.getValueAt(rowSP, 0).toString());
                windowMaSP.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn mã sản phẩm");
                windowMaSP.setLocation(txtMaSP.getLocationOnScreen().x, txtMaSP.getLocationOnScreen().y + txtMaSP.getHeight());
                windowMaSP.pack();
                windowMaSP.setSize(txtMaSP.getWidth(), windowMaSP.getHeight() / 2);
                windowMaSP.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Mã sản phẩm không tồn tại");
        }
    }

    @SneakyThrows
    private void handleSearchProductById(String cond) {
        if (!cond.equals("")) {
            List<Product> ds = mainController.getProductByText(cond);
            for (Product sanPham : ds) {
                String idSanPham = sanPham.getProductId();
                modelMaSP.addRow(new Object[]{idSanPham});
            }
        }
    }

    private void showDialogKhachHang() {
        dialogKhachHang = new JDialog();
        dialogKhachHang.setLayout(new BorderLayout());
        dialogKhachHang.setUndecorated(true);
        txtTimKiemKH = new JTextField(20);
        txtTimKiemKH.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JPanel pnMain = new JPanel(new BorderLayout());
        JPanel pn1 = new JPanel(new BorderLayout());
        JPanel pnTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnLocTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnTimKiem = new JPanel();
        JPanel pnTitle = new JPanel();
        JPanel pnBot = new JPanel(new BorderLayout());
        JPanel pnMaTen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnChonLamMoi = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pn2 = new JPanel(new BorderLayout());
        JPanel pnDong = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnMaKH = new JPanel();
        JPanel pnTenKH = new JPanel();
        JLabel lblTitle = new JLabel("Tìm kiếm khách hàng");
        JLabel lblTimKiem;
        JLabel lblMaKH;
        JLabel lblTenKH;

        lblTimKiem = new JLabel("Tìm kiếm theo Tên / Mã / SDT");
        lblTimKiem.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTitle.setForeground(new Color(26, 102, 227));
        lblMaKH = new JLabel("Mã khách hàng");
        lblMaKH.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTenKH = new JLabel("Tên khách hàng");
        lblTenKH.setFont(new Font("SansSerif", Font.BOLD, 14));
        pnTimKiem.add(lblTimKiem);
        pnTimKiem.add(Box.createHorizontalStrut(10));
        pnTimKiem.add(txtTimKiemKH);
        pnLocTimKiem.add(pnTimKiem);
        pnTop.add(pnLocTimKiem, BorderLayout.CENTER);
        pnTop.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        pnTitle.add(lblTitle);
        pn1.add(pnTop, BorderLayout.CENTER);
        pn1.add(pnTitle, BorderLayout.NORTH);
        pnMain.add(pn1, BorderLayout.NORTH);
        pnMain.setBorder(BorderFactory.createTitledBorder(""));
        modelKH.addColumn("ID Khách Hàng");
        modelKH.addColumn("Tên Khách Hàng");
        modelKH.addColumn("Số Điện Thoại");
        modelKH.addColumn("Email");
        modelKH.addColumn("Địa Chỉ");
        modelKH.addColumn("Ngày Sinh");
        tblkhachHang.setModel(modelKH);
        JScrollPane scrollTblKH = new JScrollPane(tblkhachHang);
        scrollTblKH.setBorder(BorderFactory.createTitledBorder("Khách hàng"));
        pnMain.add(scrollTblKH, BorderLayout.CENTER);

        pnMaKH.add(lblMaKH);
        pnMaKH.add(Box.createHorizontalStrut(20));
        pnMaKH.add(txtMaKH2);
        pnMaKH.add(Box.createHorizontalStrut(20));
        pnTenKH.add(lblTenKH);
        pnTenKH.add(Box.createHorizontalStrut(10));
        pnTenKH.add(txtTenKH2);
        pnMaTen.add(pnMaKH);
        pnMaTen.add(pnTenKH);
        pnBot.add(pnMaTen, BorderLayout.CENTER);
        pnChonLamMoi.add(btnChonKH);
        pnChonLamMoi.add(btnXemTatCaKhachHang);
        pnDong.add(btnDongKhachHang);
        pn2.add(pnChonLamMoi, BorderLayout.CENTER);
        pn2.add(pnDong, BorderLayout.EAST);
        pnBot.add(pn2, BorderLayout.SOUTH);
        pnMaTen.setBorder(BorderFactory.createTitledBorder("Mã & Tên"));
        pnChonLamMoi.setBorder(BorderFactory.createTitledBorder(""));
        pnDong.setBorder(BorderFactory.createTitledBorder(""));
        pnMain.add(pnBot, BorderLayout.SOUTH);

        txtTimKiemKH.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                modelKH.setRowCount(0);
                handleSearchCustomer(txtTimKiemKH.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                modelKH.setRowCount(0);
                handleSearchCustomer(txtTimKiemKH.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        dialogKhachHang.add(pnMain);
        Dimension frameSize = this.getSize();
        int frameWidth = frameSize.width;
        int frameHeight = frameSize.height;
        dialogKhachHang.setSize(frameWidth, frameHeight);
        dialogKhachHang.setLocationRelativeTo(this);
        dialogKhachHang.setVisible(true);
        loadCustomer();
    }

    private void reloadCart() {
        int rowCountGioHang = modelGioHang.getRowCount();
        if (rowCountGioHang > 0) {
            modelGioHang.setRowCount(0);
        } else {
            showThongBao("Giỏ hàng rỗng");
        }
    }

    @SneakyThrows
    private void submitUpdateQuantity(String idSanPham) {
        Product sp = mainController.getProductById(idSanPham);
        if (txtCapNhatSoLuong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập số lượng cập nhật");
            dialogSoLuong.setVisible(false);
            showDialogUpdateQuantity();
        } else if (!txtCapNhatSoLuong.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số lượng nhập sai định dạng");
            dialogSoLuong.setVisible(false);
            showDialogUpdateQuantity();
        } else if (Integer.parseInt(txtCapNhatSoLuong.getText()) <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng không được âm");
            dialogSoLuong.setVisible(false);
            showDialogUpdateQuantity();
        } else if (Integer.parseInt(txtCapNhatSoLuong.getText()) <= sp.getQuantity()) {
            modelGioHang.setValueAt(txtCapNhatSoLuong.getText(), tblGioHang.getSelectedRow(), 4);
            totalCart();
            dialogSoLuong.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Số lượng sản phẩm trong kho không đủ");
            dialogSoLuong.setVisible(false);
            showDialogUpdateQuantity();
        }


    }

    private void showDialogUpdateQuantity() {
        int rowSelectedGioHang = tblGioHang.getSelectedRow();
        int rowCountGioHang = modelGioHang.getRowCount();
        if (rowCountGioHang > 0) {
            if (rowSelectedGioHang >= 0) {
                dialogSoLuong = new JDialog();
                dialogSoLuong.setType(Type.UTILITY);
                dialogSoLuong.setLayout(new BorderLayout());
                JPanel pnXacNhanCapNhat = new JPanel();
                pnXacNhanCapNhat.add(btnXacNhanCapNhat);
                dialogSoLuong.add(txtCapNhatSoLuong, BorderLayout.CENTER);
                dialogSoLuong.add(pnXacNhanCapNhat, BorderLayout.SOUTH);

                Rectangle cellRect = tblGioHang.getCellRect(rowSelectedGioHang, 4, true);
                int cellX = cellRect.x;
                int cellY = cellRect.y;
                dialogSoLuong.setLocation(cellX, cellY);
                dialogSoLuong.pack();
                dialogSoLuong.setLocationRelativeTo(null);
                dialogSoLuong.setVisible(true);
                txtCapNhatSoLuong.setText(modelGioHang.getValueAt(rowSelectedGioHang, 4).toString());
                txtCapNhatSoLuong.requestFocus();
                txtCapNhatSoLuong.setSelectionStart(0);
                txtCapNhatSoLuong.setSelectionEnd(txtCapNhatSoLuong.getText().length());
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần thay đổi số lượng");
            }
        } else {
            showThongBao("Giỏ hàng rỗng");
        }
    }

    @SneakyThrows
    private void saveBillPending() {
        String maHD = txtMaHD.getText();
        String maKhachHang = txtMaKH.getText();
        Customer kh = mainController.findCustomerById(maKhachHang);
        java.sql.Date ngayNhapSql = new java.sql.Date(ngayNhap.getTime());
        String currentDate = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        BillPending hdc = new BillPending("HDC" + currentDate, maHD, kh, ngayNhapSql);
        try {
            mainController.addBillPending(hdc);
            String ngayLap = new SimpleDateFormat("dd/MM/yyyy").format(ngayNhap);
            modelHangCho.addRow(new Object[]{
                    maHD, kh.getName(), kh.getIdCustomer(), kh.getPhone(), ngayLap

            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm hoá đơn chờ thất bại");
        }
        int rowCountGioHang = modelGioHang.getRowCount();
        for (int i = 0; i < rowCountGioHang; i++) {
            String maSP = modelGioHang.getValueAt(i, 0).toString();
            int soLuong = Integer.parseInt(modelGioHang.getValueAt(i, 4).toString());
            Product sp = mainController.getProductById(maSP);
            String giaBanSauKM = modelGioHang.getValueAt(i, 3).toString();
            double giaBanSauKMDouble = Double.parseDouble(giaBanSauKM.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
            String thanhTien = modelGioHang.getValueAt(i, 5).toString();
            double thanhTienDouble = Double.parseDouble(thanhTien.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
            DetailsBillPending cthdc = new DetailsBillPending(hdc, sp, maHD, soLuong, giaBanSauKMDouble, thanhTienDouble);
            try {
                mainController.addDetailBillPending(cthdc);
                int quantityNew = sp.getQuantity() - soLuong;
                mainController.updateProductQuantity(maSP, quantityNew);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Thêm chi tiết hoá đơn chờ thất bại");
            }

        }
        modelGioHang.setRowCount(0);
        reloadBill();
    }

    @SneakyThrows
    private void submitPay(int rowSelectedDonHangCho) {
        txtMaHD.setText(modelHangCho.getValueAt(rowSelectedDonHangCho, 0).toString());
        txtTenKH.setText(modelHangCho.getValueAt(rowSelectedDonHangCho, 1).toString());
        txtMaKH.setText(modelHangCho.getValueAt(rowSelectedDonHangCho, 2).toString());
        List<DetailsBillPending> dsChiTietSanPham = mainController.getDetailBillPendingByIdBill(modelHangCho.getValueAt(rowSelectedDonHangCho, 0).toString());
        modelHangCho.removeRow(rowSelectedDonHangCho);
        if (dsChiTietSanPham.size() != 0) {
            for (DetailsBillPending cthdc : dsChiTietSanPham) {
                Product product = mainController.getProductById(cthdc.getProduct().getProductId());
                modelGioHang.addRow(new Object[]{
                        cthdc.getProduct().getProductId(), product.getProductName(),
                        currencyFormat.format(product.sellingPrice()),
                        currencyFormat.format(cthdc.getPrice()), cthdc.getQuantity(), currencyFormat.format(cthdc.getTotal())
                });
            }
        }
        mainController.deleteDetailsBillPendingById(txtMaHD.getText());
        mainController.deleteBillPendingById(txtMaHD.getText());
    }

    @SneakyThrows
    private void reloadBillPending() {
        int hoiNhac = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xoá toàn bộ hàng chờ không", "Cảnh báo",
                JOptionPane.YES_NO_OPTION);
        if (hoiNhac == JOptionPane.YES_OPTION) {
            mainController.deleteAllDetailBillPending();
            mainController.deleteAllBillPending();
            modelHangCho.setRowCount(0);
        }
    }

    @SneakyThrows
    private void handleXoaHangCho() {
        if (modelHangCho.getRowCount() > 0) {
            int rowSelectedDonHangCho = tblHangCho.getSelectedRow();
            if (rowSelectedDonHangCho >= 0) {
                int hoiNhac = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá hóa đơn chờ này không", "Cảnh báo",
                        JOptionPane.YES_NO_OPTION);
                if (hoiNhac == JOptionPane.YES_OPTION) {
                    mainController.deleteDetailsBillPendingById(modelHangCho.getValueAt(rowSelectedDonHangCho, 0).toString());
                    mainController.deleteBillPendingById(modelHangCho.getValueAt(rowSelectedDonHangCho, 0).toString());
                    modelHangCho.removeRow(rowSelectedDonHangCho);
                    showThongBao("Xoá hàng chờ thành công");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn chưa chọn hoá đơn chờ để xoá");
            }
        } else {
            showThongBao("Hàng chờ rỗng");
        }
    }

    private void handleSubmitPayBillPending() {
        if (modelHangCho.getRowCount() > 0) {
            int rowSelectedDonHangCho = tblHangCho.getSelectedRow();
            if (modelGioHang.getRowCount() == 0) {
                if (rowSelectedDonHangCho >= 0) {
                    submitPay(rowSelectedDonHangCho);
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn chưa chọn hoá đơn chờ để tiếp tục thanh toán");
                }
            } else {
                if (rowSelectedDonHangCho >= 0) {
                    int hoiNhac = JOptionPane.showConfirmDialog(this, "Bạn có muốn lưu đơn hàng hiện tại không!", "Cảnh báo",
                            JOptionPane.YES_NO_OPTION);
                    if (hoiNhac == JOptionPane.YES_OPTION) {
                        saveBillPending();
                        submitPay(rowSelectedDonHangCho);
                    } else {
                        modelGioHang.setRowCount(0);
                        submitPay(rowSelectedDonHangCho);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Bạn chưa chọn hoá đơn chờ để tiếp tục thanh toán");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hàng chờ rỗng");
        }
    }

    private void reloadBill() {
        if (modelGioHang.getRowCount() == 0) {
            txtMaHD.setText("");
            txtTenKH.setText("");
            txtMaKH.setText("");
            tblHangCho.clearSelection();
        } else {
            int hoiNhac = JOptionPane.showConfirmDialog(this, "Bạn có muốn lưu đơn hàng hiện tại không!", "Cảnh báo",
                    JOptionPane.YES_NO_OPTION);
            if (hoiNhac == JOptionPane.YES_OPTION) {
                saveBillPending();
            } else {
                txtMaHD.setText("");
                txtTenKH.setText("");
                txtMaKH.setText("");
                modelGioHang.setRowCount(0);
                tblHangCho.clearSelection();
            }
        }

    }

    private void handleReloadBill() {
        if (modelGioHang.getRowCount() == 0) {
            txtMaHD.setText("");
            txtTenKH.setText("");
            txtMaKH.setText("");
            tblHangCho.clearSelection();
        } else {
            int hoiNhac = JOptionPane.showConfirmDialog(this, "Bạn có muốn lưu đơn hàng hiện tại không!", "Cảnh báo",
                    JOptionPane.YES_NO_OPTION);
            if (hoiNhac == JOptionPane.YES_OPTION) {
                saveBillPending();
            } else {
                txtMaHD.setText("");
                txtTenKH.setText("");
                txtMaKH.setText("");
                modelGioHang.setRowCount(0);
                tblHangCho.clearSelection();
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int rowSP = tblSanPham.getSelectedRow();
        if (rowSP >= 0) {
            txtMaSP2.setText(modelSP.getValueAt(rowSP, 0).toString());
        } else {
            txtMaSP2.setText("");
        }

        int rowKH = tblkhachHang.getSelectedRow();
        if (rowKH >= 0) {
            txtMaKH2.setText(modelKH.getValueAt(rowKH, 0).toString());
            txtTenKH2.setText(modelKH.getValueAt(rowKH, 1).toString());
        } else {
            txtMaKH2.setText("");
            txtTenKH2.setText("");
        }
    }

    @SneakyThrows
    private void loadBillPending() {
        List<BillPending> dsHoaDonCho = mainController.getAllBillPending();
        if (dsHoaDonCho.size() > 0) {
            for (BillPending hdc : dsHoaDonCho) {
                String ngayLap = new SimpleDateFormat("dd/MM/yyyy").format(hdc.getNgayLap());
                modelHangCho.addRow(new Object[]{
                        hdc.getBillId(), hdc.getCustomer().getName(),
                        hdc.getCustomer().getIdCustomer(), hdc.getCustomer().getPhone(),
                        ngayLap
                });
            }
        }
    }

    @SneakyThrows
    private void saveBill() {
        String idHD = txtMaHD.getText();
        Customer kh = mainController.findCustomerById(txtMaKH.getText());
        Employee nv = mainController.findEmployeeById(employee.getIdEmployee());
        String tienKhachDua = txtTienKhachDua.getText();
        double tienKhachDuaDouble = Double.parseDouble(tienKhachDua.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
        String tongTienHoaDon = txtTongTienHoaDon.getText();
        double tongTienHoaDonDouble = Double.parseDouble(tongTienHoaDon.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
        LocalDate ngayTaoHD = LocalDate.now();
        double tongLoiNhuan = 0;
        int gioHangRowCount = modelGioHang.getRowCount();

        for (int i = 0; i < gioHangRowCount; i++) {
            Product sanPham = mainController.getProductById(modelGioHang.getValueAt(i, 0).toString());
            int soLuong = Integer.parseInt(modelGioHang.getValueAt(i, 4).toString());
            double giaNhap = sanPham.getImportPrice();
            double loiNhuan = (giaNhap * 0.55) * soLuong;
            tongLoiNhuan += loiNhuan;
        }

        Bill hd = new Bill(idHD, ngayTaoHD, kh, nv, tienKhachDuaDouble, tongTienHoaDonDouble, tongLoiNhuan);
        try {
            mainController.addBill(hd);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm hoá đơn thất bại");
        }
        int rowCountGioHang = modelGioHang.getRowCount();
        for (int i = 0; i < rowCountGioHang; i++) {
            int soLuong = Integer.parseInt(modelGioHang.getValueAt(i, 4).toString());
            String maSP = modelGioHang.getValueAt(i, 0).toString();

            String thanhTien = modelGioHang.getValueAt(i, 5).toString();
            double thanhTienDouble = Double.parseDouble(thanhTien.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
            Product product = mainController.getProductById(maSP);
            String giaBan = modelGioHang.getValueAt(i, 3).toString();
            double giaBanDouble = Double.parseDouble(giaBan.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));

            DetailsBill cthd = new DetailsBill(hd, product, soLuong, giaBanDouble, thanhTienDouble);
            try {
                mainController.addDetailsBill(cthd);
                int quantityNew = product.getQuantity() - soLuong;
                mainController.updateProductQuantity(maSP, quantityNew);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Thêm chi tiết hoá đơn thất bại");
            }
        }
    }

    private void handLePayment() throws DocumentException {
        if (!txtMaKH.getText().equals("")) {
            if (modelGioHang.getRowCount() >= 0) {

                if (txtTienKhachDua.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Chưa nhập tiền khách đưa");
                    txtTienKhachDua.requestFocus();
                    txtTienKhachDua.setSelectionStart(0);
                    txtTienKhachDua.setSelectionEnd(txtTienKhachDua.getText().length());
                    return;
                }

                if (!txtTienKhachDua.getText().matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Tiền khách đưa nhập sai định dạng");
                    txtTienKhachDua.requestFocus();
                    txtTienKhachDua.setSelectionStart(0);
                    txtTienKhachDua.setSelectionEnd(txtTienKhachDua.getText().length());
                    return;
                }

                String tongTienHoaDon = txtTongTienHoaDon.getText();
                double tongTienHoaDonDouble = Double.parseDouble(tongTienHoaDon.trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
                double tienKhachDua = Double.parseDouble(txtTienKhachDua.getText());

                if (tienKhachDua < tongTienHoaDonDouble) {
                    JOptionPane.showMessageDialog(this, "Tiền khách đưa bé hơn tổng tiền hoá đơn");
                    txtTienKhachDua.requestFocus();
                    txtTienKhachDua.setSelectionStart(0);
                    txtTienKhachDua.setSelectionEnd(txtTienKhachDua.getText().length());
                    return;
                }
                saveBill();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String filePath = System.getProperty("user.dir") + "/Server/src/main/resources/DataExports/HoaDonPDF/HD_" + timeStamp + ".pdf";
                exportToPdf(filePath);
                reloadCart();
                handleReloadBill();
                txtTienKhachDua.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Giỏ hàng rỗng");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa thể thanh toán được vì chưa chọn khách hàng");
            showDialogKhachHang();
        }

    }

    private void exportToPdf(String filePath) throws DocumentException {
        OutputStream file = null;
        try {
            file = new FileOutputStream(filePath);
            // Create a new Document object
            Document document = new Document();

            // You need PdfWriter to generate PDF document
            PdfWriter.getInstance(document, file);
            // You need PdfWriter to generate PDF document

            // Opening document for writing PDF
            document.open();
            String idKH = txtMaKH.getText();
            Customer customer = mainController.findCustomerById(idKH);
            String idHD = txtMaHD.getText();
            Bill bill = mainController.getBillById(idHD);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateTimeString = now.format(formatter);
            // Writing content
            String filePath2 = System.getProperty("user.dir") + "/Server/src/main/resources/database/vuArial.ttf";
            BaseFont bf = BaseFont.createFont(filePath2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Paragraph tieuDe = new Paragraph("HÓA ĐƠN", new com.itextpdf.text.Font(bf, 30, 1, BaseColor.BLUE));
            Paragraph tenKH = new Paragraph("Tên khách hàng : " + customer.getName(), new com.itextpdf.text.Font(bf, 15));
            Paragraph idKH2 = new Paragraph("ID khách hàng   : " + customer.getIdCustomer(), new com.itextpdf.text.Font(bf, 15));
            Paragraph idHD2 = new Paragraph("ID hóa đơn      : " + txtMaHD.getText().toString(), new com.itextpdf.text.Font(bf, 15, Font.BOLD));
            String ngay = new SimpleDateFormat("dd").format(new Date());
            String thang = new SimpleDateFormat("MM").format(new Date());
            String nam = new SimpleDateFormat("yyyy").format(new Date());
            Paragraph DateTime = new Paragraph("Ngày " + ngay + " tháng " + thang + " năm " + nam, new com.itextpdf.text.Font(bf, 15, Font.ITALIC));
            tieuDe.setAlignment(Element.ALIGN_CENTER);
            DateTime.setAlignment(Element.ALIGN_RIGHT);
            document.add(tieuDe);
            document.add(DateTime);
            document.add(idHD2);
            document.add(tenKH);
            document.add(idKH2);
            document.add(new Paragraph("SDT khách hàng: " + customer.getPhone(), new com.itextpdf.text.Font(bf, 15)));
            document.add(new Paragraph("Tên nhân viên    : " + employee.getName().toString(), new com.itextpdf.text.Font(bf, 15)));
            document.add(new Paragraph("ID nhân viên      : " + employee.getIdEmployee(), new com.itextpdf.text.Font(bf, 15)));
            document.add(new Paragraph("Ngày lập            : " + dateTimeString, new com.itextpdf.text.Font(bf, 15)));
            document.add(new Paragraph(" "));
            // Add meta data information to PDF file

            PdfPTable table = new PdfPTable(6);
            //Khởi tạo ô header
            PdfPCell header1 = new PdfPCell(new Paragraph("ID sản phẩm", new com.itextpdf.text.Font(bf, 12)));
            PdfPCell header2 = new PdfPCell(new Paragraph("Tên sản phẩm", new com.itextpdf.text.Font(bf, 12)));
            PdfPCell header3 = new PdfPCell(new Paragraph("Số lượng", new com.itextpdf.text.Font(bf, 12)));
            PdfPCell header4 = new PdfPCell(new Paragraph("Thành tiền", new com.itextpdf.text.Font(bf, 12)));
            PdfPCell header5 = new PdfPCell(new Paragraph("Giá niêm yết", new com.itextpdf.text.Font(bf, 12)));
            PdfPCell header6 = new PdfPCell(new Paragraph("Giá khuyến mãi", new com.itextpdf.text.Font(bf, 12)));

            header1.setMinimumHeight(70f); // Kích thước tối thiểu là 50
            header2.setMinimumHeight(70f);
            header3.setMinimumHeight(70f);
            header4.setMinimumHeight(70f);
            header5.setMinimumHeight(70f);
            header6.setMinimumHeight(70f);
            //Thêm 4 ô header vào table
            table.addCell(header1);
            table.addCell(header2);
            table.addCell(header5);
            table.addCell(header6);
            table.addCell(header3);
            table.addCell(header4);


            for (DetailsBill cthd : mainController.findDetailsBillByBillId(idHD)) {
                String idSP = cthd.getProduct().getProductId();
                ProductSale productSale = mainController.getProductSale(idSP);
                Product product = mainController.getProductById(idSP);
                String giaBanSauKM;
                if (productSale != null) {
                    giaBanSauKM = currencyFormat.format(productSale.getGiaBan()) + "(-" + productSale.getDescription().replaceAll("[^0-9%]", "") + ")";
                } else {
                    giaBanSauKM = currencyFormat.format(product.sellingPrice());
                }

                String tenSP = product.getProductName();
                String soLuong = String.valueOf(cthd.getQuantity());
                String thanhTien = currencyFormat.format(cthd.getTotal());
                String giaBan = currencyFormat.format(product.sellingPrice());

                table.addCell(new PdfPCell(new Paragraph(idSP)));
                table.addCell(new PdfPCell(new Paragraph(tenSP, new com.itextpdf.text.Font(bf, 12))));
                table.addCell(new PdfPCell(new Paragraph(giaBan)));
                table.addCell(new PdfPCell(new Paragraph(giaBanSauKM)));
                table.addCell(new PdfPCell(new Paragraph(soLuong)));
                table.addCell(new PdfPCell(new Paragraph(thanhTien)));
            }

            document.add(table);
            String StrtienKhachDua = currencyFormat.format(bill.getAmountReceived());

            Paragraph tongTien = new Paragraph("TỔNG TIỀN : " + txtTongTienHoaDon.getText(), new com.itextpdf.text.Font(bf, 25, 1, BaseColor.RED));
            Paragraph tienKhachDua = new Paragraph("Tiền khách đưa  : " + StrtienKhachDua, new com.itextpdf.text.Font(bf, 20));
            Paragraph tienTraKhach = new Paragraph("Tiền trả khách    : " + txtTienTraKhach.getText(), new com.itextpdf.text.Font(bf, 20));
            Paragraph tienGioHang = new Paragraph("Tổng thành tiền  : " + txtTongTienGioHang.getText(), new com.itextpdf.text.Font(bf, 20, Font.BOLD));
            Paragraph thue = new Paragraph("Thuế VAT (10%): " + txtThue.getText(), new com.itextpdf.text.Font(bf, 20));
            document.add(tienGioHang);
            document.add(thue);
            document.add(tongTien);
            document.add(tienKhachDua);
            document.add(tienTraKhach);
            // close the document
            document.close();
            Desktop.getDesktop().open(new java.io.File(filePath));

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            // closing FileOutputStream
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException io) {/*Failed to close*/

            }

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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThemGioHang)) {
            handleAddCartById();
        }
        if (o.equals(btnThemGioHang2)) {
            handleAddCartBySearch();
        }
        if (o.equals(cbLocSanPham)) {
            handleSelectiveProduct();
        }
        if (o.equals(btnXoaGioHang)) {
            deleteCart();
        }
        if (o.equals(btnCapNhatSoLuong)) {
            showDialogUpdateQuantity();
        }
        if (o.equals(btnLamMoiGioHang)) {
            reloadCart();
        }
        if (o.equals(btnLamMoiDonHang)) {
            handleReloadBill();
        }
        if (o.equals(btnXemTatCaHDC)) {
            txtTimKiemHDCho.setText("");
        }
        if (o.equals(btnTimKiemSanPham)) {
            showDialogSanPham();
        }
        if (o.equals(btnTimKhachHang)) {
            showDialogKhachHang();
        }
        if (o.equals(btnDongKhachHang)) {
            dialogKhachHang.dispose();
            modelKH.setColumnCount(0);
            modelKH.setRowCount(0);
        }
        if (o.equals(btnDongSanPham)) {
            dialogSanPham.dispose();
            cbLocSanPham.setSelectedItem(0);
        } else if (o.equals(btnChonKH)) {
            handleChooseCustomer();
        }
        if (o.equals(btnChonMaSanPham)) {
            chonMaSanPham();
        }
        if (o.equals(btnXacNhanCapNhat)) {
            int rowSelected = tblGioHang.getSelectedRow();
            submitUpdateQuantity(modelGioHang.getValueAt(rowSelected, 0).toString());
        }
        if (o.equals(btnXemTatCaKhachHang)) {
            txtTimKiemKH.setText("");
        }
        if (o.equals(btnLuuHoaDon)) {
            saveBillPending();
        }
        if (o.equals(btnXoaHangCho)) {
            handleXoaHangCho();
        }
        if (o.equals(btnLamMoiHangCho)) {
            reloadBillPending();
        }
        if (o.equals(btnChonThanhToan)) {
            handleSubmitPayBillPending();
        }
        if (o.equals(btnThanhToan)) {
            try {
                handLePayment();
            } catch (DocumentException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (o.equals(btnXemTatCaSanPham)) {
            txtTimKiemSP.setText("");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}