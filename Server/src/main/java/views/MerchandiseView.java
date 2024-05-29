package views;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controller.MainController;
import controller.MainControllerInterface;
import lombok.SneakyThrows;
import models.Merchandise;
import models.ProductType;
import models.Supplier;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.DialogUtils;
import util.GeneratorIDAuto;
import util.ProductStatusEnum;


public class MerchandiseView extends JPanel implements ActionListener, ItemListener, MouseListener, KeyListener, ListSelectionListener {
    private JTabbedPane tabbedPane;
    private JTextField txtTenSanPham, txtIdSanPham, txtKichThuoc, txtSoLuong, txtMauSac, txtGiaBan, txtGiaNhap;
    private JComboBox<String> cbTinhTrangKhinhDoanh;
    private JComboBox<String> cbLoaiSanPham;
    private JComboBox<String> cbNhaCungCap;
    private JCheckBox chkTinhTrangKinhDoanh;
    private JLabel lblTieuDe, lblIDSanPham, lblTenSanPham, lblNhaCungCap, lblLoaiSanPham, lblKichThuoc, lblMauSac,
            lblSoLuong, lblGiaBan, lblThueVAT, lblTinhTrangKinhDoanh, lblGiaNhap;
    private JTable sanPhamTable;
    private JPanel pnCenter, pnChucNang, pnDanhMuc, pnMain;
    private JButton btnThemSP, btnXoaSP, btnNhapSP, btnCapNhatSP, btnHienThiLS, btnrefeshForm;
    private JTable table;
    private DefaultTableModel modelSP;
    private JLabel lblTuKhoa;
    private JTextField txtTuKhoa;
    private JButton btnTimKiem;
    private JButton btnXemTatCa;
    private JButton btnNhapNhieuSanPham;
    private JPanel pnChucNangCha;
    private JPanel pnChucNangTimKiem;
    private JComboBox<String> cbLoaiSanPhamSearch;
    private JComboBox<String> cbNhaCungCapSearch;
    private JLabel lblLoaiSanPhamSearch;
    private JLabel lblNhaCungCapSearch;
    private BookView sachPanel;
    private JButton btnXuatExCel;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private static MainControllerInterface mainController;

    static {
        try {
            mainController = new MainController();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    private GeneratorIDAuto autoID;

    public MerchandiseView(MainControllerInterface mainController) throws RemoteException {

        this.mainController = mainController;
        autoID = new GeneratorIDAuto();
        currencyFormat.setCurrency(Currency.getInstance("VND"));
        setLayout(new BorderLayout(8, 6));
        tabbedPane = new JTabbedPane();
        // tab sách
        sachPanel = new BookView(mainController);
        // Tab Sản phẩm
        JPanel sanPhamPanel = new JPanel();
        sanPhamPanel.setLayout(new BorderLayout());
        sanPhamPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        // center
        pnCenter = new JPanel();
        pnCenter.setLayout(new GridLayout(5, 5, 10, 10));
        pnCenter.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));

        lblIDSanPham = new JLabel("ID Sản Phẩm(*):");
        txtIdSanPham = new JTextField();
        txtIdSanPham.setEditable(false);
        lblTenSanPham = new JLabel("Tên Sản Phẩm(*):");
        txtTenSanPham = new JTextField();
        txtTenSanPham.requestFocus();
        lblNhaCungCap = new JLabel("Nhà Cung Cấp(*):");
        cbNhaCungCap = new JComboBox<>();
        lblLoaiSanPham = new JLabel("Loại Sản Phẩm(*):");
        cbLoaiSanPham = new JComboBox<>();
        lblKichThuoc = new JLabel("Kích Thước (*):");
        txtKichThuoc = new JTextField();
        lblMauSac = new JLabel("Màu Sắc (*):");
        txtMauSac = new JTextField();
        lblSoLuong = new JLabel("Số Lượng (*):");
        txtSoLuong = new JTextField();
        lblGiaNhap = new JLabel("Giá Nhập (*)");
        txtGiaNhap = new JTextField();
        lblGiaBan = new JLabel("Giá Bán (*):");
        txtGiaBan = new JTextField();
        txtGiaBan.setEditable(false);
        lblTinhTrangKinhDoanh = new JLabel("Tình Trạng Kinh Doanh(*):");
        chkTinhTrangKinhDoanh = new JCheckBox();
        chkTinhTrangKinhDoanh.setSelected(true);

        txtIdSanPham.setToolTipText("ID + Date + XXXX");
        txtKichThuoc.setToolTipText("Chỉ nhận số");
        txtSoLuong.setToolTipText("Chỉ nhận số nguyên");
        txtMauSac.setToolTipText("Chỉ nhận chữ và không có kí tự đặc biệt");
        txtTenSanPham.setToolTipText("Chỉ nhận chữ và số và không có kí tự đặc biệt");
        txtGiaNhap.setToolTipText("Chỉ nhận số");

        pnCenter.add(lblIDSanPham);
        pnCenter.add(txtIdSanPham);
        pnCenter.add(lblTenSanPham);
        pnCenter.add(txtTenSanPham);
        pnCenter.add(lblNhaCungCap);
        pnCenter.add(cbNhaCungCap);
        pnCenter.add(lblLoaiSanPham);
        pnCenter.add(cbLoaiSanPham);
        pnCenter.add(lblKichThuoc);
        pnCenter.add(txtKichThuoc);
        pnCenter.add(lblMauSac);
        pnCenter.add(txtMauSac);
        pnCenter.add(lblSoLuong);
        pnCenter.add(txtSoLuong);
        pnCenter.add(lblGiaNhap);
        pnCenter.add(txtGiaNhap);
        pnCenter.add(lblGiaBan);
        pnCenter.add(txtGiaBan);
        pnCenter.add(lblTinhTrangKinhDoanh);
        pnCenter.add(chkTinhTrangKinhDoanh);

        pnMain = new JPanel(new BorderLayout());

        pnChucNangCha = new JPanel(new BorderLayout(6, 8));
        pnChucNang = new JPanel(new GridLayout(1, 4, 10, 40));
        pnChucNangCha.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        btnThemSP = new JButton("THÊM SẢN PHẨM");
        btnCapNhatSP = new JButton("CẬP NHẬT SẢN PHẨM");
        btnXoaSP = new JButton("XÓA SẢN PHẨM");
        lblTuKhoa = new JLabel("Tìm kiếm theo tên sản phẩm:");
        txtTuKhoa = new JTextField();
        btnTimKiem = new JButton("Tìm kiếm");
        btnXemTatCa = new JButton("Xem tất cả");
        btnrefeshForm = new JButton("LÀM MỚI");
        btnNhapNhieuSanPham = new JButton("NHẬP SẢN PHẨM EXCEL");
        btnXuatExCel = new JButton("XUẤT EXCEL");

//		ImageIcon iconThem = new ImageIcon(getClass().getResource("/icons/add.png"));
//		ImageIcon iconCapNhat = new ImageIcon(getClass().getResource("/icons/capnhat.png"));
//		ImageIcon iconrefeshForm = new ImageIcon(getClass().getResource("/icons/refeshForm.png"));
//		ImageIcon iconXoa = new ImageIcon(getClass().getResource("/icons/xoa.png"));

//		btnCapNhatSP.setIcon(iconCapNhat);
//		btnThemSP.setIcon(iconThem);
//		btnrefeshForm.setIcon(iconrefeshForm);
//		btnXoaSP.setIcon(iconXoa);
//
//		btnThemSP.setIcon(iconThem);
//		btnCapNhatSP.setIcon(iconCapNhat);
//		btnrefeshForm.setIcon(iconrefeshForm);
//		btnXoaSP.setIcon(iconXoa);

        pnChucNang.add(btnThemSP);
        pnChucNang.add(btnNhapNhieuSanPham);
        pnChucNang.add(btnCapNhatSP);
        pnChucNang.add(btnrefeshForm);
        pnChucNang.add(btnXoaSP);
        pnChucNang.add(btnXuatExCel);

        pnChucNangTimKiem = new JPanel(new GridLayout(1, 7, 10, 10));
        lblLoaiSanPhamSearch = new JLabel("Loại sản phẩm:");
        lblNhaCungCapSearch = new JLabel("Nhà cung cấp:");
        cbLoaiSanPhamSearch = new JComboBox<>();
        cbNhaCungCapSearch = new JComboBox<>();

        pnChucNangTimKiem.add(lblLoaiSanPhamSearch);
        pnChucNangTimKiem.add(cbLoaiSanPhamSearch);
        pnChucNangTimKiem.add(lblNhaCungCapSearch);
        pnChucNangTimKiem.add(cbNhaCungCapSearch);

        pnChucNangTimKiem.add(lblTuKhoa);
        pnChucNangTimKiem.add(txtTuKhoa);
        pnChucNangTimKiem.add(btnXemTatCa);

        pnChucNangCha.add(pnChucNang, BorderLayout.NORTH);
        pnChucNangCha.add(pnChucNangTimKiem, BorderLayout.SOUTH);
        pnMain.add(pnChucNangCha, BorderLayout.NORTH);

        pnDanhMuc = new JPanel(new BorderLayout());
        pnDanhMuc.setBorder(BorderFactory.createTitledBorder("Danh mục"));
        modelSP = new DefaultTableModel();
        table = new JTable();
        modelSP.addColumn("ID Sản Phẩm");
        modelSP.addColumn("Tên Sản Phẩm");
        modelSP.addColumn("Loại Sản Phẩm");
        modelSP.addColumn("Nhà Cung Cấp");
        modelSP.addColumn("Kích Thước");
        modelSP.addColumn("Màu Sắc");
        modelSP.addColumn("Trạng Thái");
        modelSP.addColumn("Thuế");
        modelSP.addColumn("Giá Nhập");
        modelSP.addColumn("Số Lượng");
        modelSP.addColumn("Giá Bán");
        table.setModel(modelSP);
        JScrollPane scrollPane = new JScrollPane(table);
        pnDanhMuc.add(scrollPane);
        pnMain.add(pnDanhMuc, BorderLayout.CENTER);

        // Bảng
        sanPhamTable = new JTable();
        sanPhamPanel.add(pnCenter, BorderLayout.NORTH);
        sanPhamPanel.add(pnMain, BorderLayout.CENTER);

        tabbedPane.addTab("Sản phẩm", sanPhamPanel);
        tabbedPane.add("Sách", sachPanel);
        add(tabbedPane);

//        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
//            @Override
//            protected Void doInBackground() throws Exception {
//                loadData();
//                loadComboxBoxLoaiSanPham();
//                loadComboxBoxNhaCungCap();
//                return null;
//            }
//        };
//        worker.execute();
        loadData();
        loadComboxBoxLoaiSanPham();
        loadComboxBoxNhaCungCap();
        table.addMouseListener(this);
        btnThemSP.addActionListener(this);
        btnCapNhatSP.addActionListener(this);
        btnXemTatCa.addActionListener(this);
        btnrefeshForm.addActionListener(this);
        btnXoaSP.addActionListener(this);
        btnNhapNhieuSanPham.addActionListener(this);
        btnXuatExCel.addActionListener(this);
        cbLoaiSanPhamSearch.addItemListener(this);
        cbNhaCungCapSearch.addItemListener(this);
        txtTuKhoa.addKeyListener(this);
        txtKichThuoc.addKeyListener(this);
        txtSoLuong.addKeyListener(this);
        txtGiaNhap.addKeyListener(this);
        txtMauSac.addKeyListener(this);
        txtTenSanPham.addKeyListener(this);
        tabbedPane.addKeyListener(this);
        table.getSelectionModel().addListSelectionListener(this);

        txtTenSanPham.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                txtIdSanPham.setText(autoID.autoID("SP"));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        this.setFocusable(true);
        this.requestFocusInWindow();
        table.requestFocus();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_RELEASED) {
                    if (e.getKeyCode() == KeyEvent.VK_F5) {
                        refeshForm();
                        loadData();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_INSERT) {
                    System.out.println("Xoa thanh cong");
                    deleteMerchandise();
                } else if (e.getKeyCode() == KeyEvent.VK_F2) {
                    updateMerchandise();
                } else if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0 && e.getKeyCode() == KeyEvent.VK_S) {
                    txtTuKhoa.requestFocusInWindow();
                }
                return false;
            }
        });
    }

    @SneakyThrows
    private void loadDataIntoTable() {
        modelSP.setRowCount(0);
        java.util.List<Merchandise> merchandiseList = mainController.getAllMerchandise();
        for (Merchandise sp : merchandiseList) {
            String idSanPham = sp.getProductId();
            String tenSanPham = sp.getProductName();
            String tenLoaiSanPham = sp.getProductTypeId().getProductTypeId();
            String tenNhaCungCap = sp.getSupplierId().getSupplierId();
            double kichThuoc = sp.getSize();
            String mauSac = sp.getColor();
            String trangThai = sp.getStatus().getDescription() + "";
            double thue = sp.tax();
            double giaNhap = sp.getImportPrice();
            int soLuong = sp.getQuantity();
            double giaBan = sp.sellingPrice();
            modelSP.addRow(new Object[]{idSanPham, tenSanPham, tenLoaiSanPham, tenNhaCungCap, kichThuoc, mauSac,
                    trangThai, currencyFormat.format(thue), currencyFormat.format(giaNhap), soLuong,
                    currencyFormat.format(giaBan)});
        }
    }

    @SneakyThrows
    private void loadData() {
        txtTenSanPham.requestFocus();
        modelSP.setRowCount(0);
        java.util.List<Merchandise> merchandiseList = mainController.getAllMerchandise();
        for (Merchandise sp : merchandiseList) {
            String idSanPham = sp.getProductId();
            String tenSanPham = sp.getProductName();
            String tenLoaiSanPham = sp.getProductTypeId().getProductTypeName();
            String tenNhaCungCap = sp.getSupplierId().getSupplierName();
            double kichThuoc = sp.getSize();
            String mauSac = sp.getColor();
            String trangThai = sp.getStatus().getDescription() + "";
            double thue = sp.tax();
            double giaNhap = sp.getImportPrice();
            int soLuong = sp.getQuantity();
            double giaBan = sp.sellingPrice();
            modelSP.addRow(new Object[]{idSanPham, tenSanPham, tenLoaiSanPham, tenNhaCungCap, kichThuoc, mauSac,
                    trangThai, currencyFormat.format(thue), currencyFormat.format(giaNhap), soLuong,
                    currencyFormat.format(giaBan)});
        }
    }

    @SneakyThrows
    private void loadComboxBoxLoaiSanPham() {
        java.util.List<ProductType> productTypes = mainController.getAllProductType();
        cbLoaiSanPham.removeAllItems();
        cbLoaiSanPhamSearch.addItem("Tất cả");
        for (ProductType productType : productTypes) {
            cbLoaiSanPham.addItem(productType.getProductTypeName());
            cbLoaiSanPhamSearch.addItem(productType.getProductTypeName());
        }
    }

    @SneakyThrows
    private void loadComboxBoxNhaCungCap() {
        java.util.List<Supplier> suppliers = mainController.getAllSuppliers();
        cbNhaCungCap.removeAllItems();
        cbNhaCungCapSearch.addItem("Tất cả");
        for (Supplier supplier : suppliers) {
            cbNhaCungCap.addItem(supplier.getSupplierName());
            cbNhaCungCapSearch.addItem(supplier.getSupplierName());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThemSP)) {
            addMerchandise();
        } else if (o.equals(btnCapNhatSP)) {
            updateMerchandise();
        } else if (o.equals(btnrefeshForm)) {
            refeshForm();
        } else if (o.equals(btnXemTatCa)) {
            refeshForm();
            loadData();
        } else if (o.equals(btnXoaSP)) {
            deleteMerchandise();
        } else if (o.equals(btnXuatExCel)) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = System.getProperty("user.dir") + "/src/main/resources/DataExports/SanPham/SP_" + timeStamp
                    + ".xlsx";
            exportExcel(filePath);
        } else if (o.equals(btnNhapNhieuSanPham)) {
            try {
                importExcel();
//                loadData();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void importExcel() throws SQLException {
        String defaultCurrentDirectoryPath = System.getProperty("user.dir") + "/src/main/resources/DataImports";
        JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
        excelFileChooser.setDialogTitle("Chọn File Excel");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("TẤT CẢ CÁC FILE EXCEL", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showOpenDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            File excelFile = excelFileChooser.getSelectedFile();

            try (FileInputStream fis = new FileInputStream(excelFile); Workbook workbook = new XSSFWorkbook(fis)) {
                xulyTrangNhaCungCap(workbook.getSheetAt(0));
                xulyTrangLoaiSanPham(workbook.getSheetAt(1));
                xulyTrangSanPhamCon(workbook.getSheetAt(2));
                loadComboxBoxLoaiSanPham();
                loadComboxBoxNhaCungCap();
                loadData();
                DialogUtils.showSuccessMessage(this, "Nhập dữ liệu thành công");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }


    @SneakyThrows
    private void xulyTrangSanPhamCon(Sheet sheet) {
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Merchandise sp = new Merchandise();
                sp.setProductId(row.getCell(0).getStringCellValue());
                sp.setProductName(row.getCell(1).getStringCellValue());
                sp.setProductTypeId(new ProductType(row.getCell(2).getStringCellValue()));
                sp.setSupplierId(new Supplier(row.getCell(3).getStringCellValue()));
                sp.setSize(row.getCell(4).getNumericCellValue());
                sp.setColor(row.getCell(5).getStringCellValue());
                int trangThaiValue = (int) row.getCell(6).getNumericCellValue();
                ProductStatusEnum trangThaiEnum = ProductStatusEnum.getById(trangThaiValue);
                sp.setStatus(trangThaiEnum);
                sp.tax();
                sp.setImportPrice(row.getCell(8).getNumericCellValue());
                int soLuong = (int) row.getCell(9).getNumericCellValue();
                sp.setQuantity(soLuong);
                sp.sellingPrice();

                boolean checkIDLoaiSanPham = mainController.checkProductTypeExist(sp.getProductTypeId().getProductTypeId());
                boolean checkIDNhaCungCap = mainController.checkSupplierId(sp.getSupplierId().getSupplierId());

                if (!checkIDLoaiSanPham || !checkIDNhaCungCap) {
                    loadData();
                    continue;
                }

                try {
                    if (mainController.checkIdExist(sp.getProductId())) {
                        mainController.updateMerchandise(sp);
                    } else {
                        boolean result = mainController.addMerchandise(sp);
                        if (!result) {
                            System.out.println("Không thể thêm sản phẩm: " + sp.getProductId());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void xulyTrangLoaiSanPham(Sheet sheet) {
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                ProductType lsp = new ProductType();
                lsp.setProductTypeId(row.getCell(0).getStringCellValue());
                lsp.setProductTypeName(row.getCell(1).getStringCellValue());
                try {
                    if (mainController.checkProductTypeExist(lsp.getProductTypeId())) {
                        mainController.updateProductType(lsp);
                    } else {
                        mainController.addProductType(lsp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void xulyTrangNhaCungCap(Sheet sheet) {
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Supplier ncc = new Supplier();
                ncc.setSupplierId(row.getCell(0).getStringCellValue());
                ncc.setSupplierName(row.getCell(1).getStringCellValue());
                ncc.setAddress(row.getCell(2).getStringCellValue());
                ncc.setPhoneNumber(row.getCell(3).getStringCellValue());

                try {
                    if (!mainController.checkSupplierId(ncc.getSupplierId())) {
                        boolean result = mainController.addSupplier(ncc);
                        if (!result) {
                            System.out.println("Không thể thêm nhà cung cấp: " + ncc.getSupplierId());
                        }
                    } else {
                        mainController.updateSupplier(ncc);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

//	public void importExcel() throws SQLException {
//		String defaultCurrentDirectoryPath = System.getProperty("user.dir") + "/src/main/resources/DataImports";
//		JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
//		excelFileChooser.setDialogTitle("Select Excel File");
//		FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
//		excelFileChooser.setFileFilter(fnef);
//		int excelChooser = excelFileChooser.showOpenDialog(null);
//		if (excelChooser == JFileChooser.APPROVE_OPTION) {
//			File excelFile = excelFileChooser.getSelectedFile();
//			try (FileInputStream fis = new FileInputStream(excelFile); Workbook workbook = new XSSFWorkbook(fis)) {
//				Sheet sheet = workbook.getSheetAt(0);
//				for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
//					Row row = sheet.getRow(rowIndex);
//					if (row != null) {
//						SanPhamCon sp = new SanPhamCon();
//						sp.setIdSanPham(row.getCell(0).getStringCellValue());
//						sp.setTenSanPham(row.getCell(1).getStringCellValue());
//						sp.setIdLoaiSanPham(new LoaiSanPham(row.getCell(2).getStringCellValue()));
//						sp.setIdNhaCungCap(new NhaCungCap(row.getCell(3).getStringCellValue()));
//						sp.setKichThuoc(row.getCell(4).getNumericCellValue());
//						sp.setMauSac(row.getCell(5).getStringCellValue());
//						int trangThaiValue = (int) row.getCell(6).getNumericCellValue();
//						TrangThaiSPEnum trangThaiEnum = TrangThaiSPEnum.getById(trangThaiValue);
//						sp.setTrangThai(trangThaiEnum);
//						sp.thue();
//						sp.setGiaNhap(row.getCell(8).getNumericCellValue());
//						int soLuong = (int) row.getCell(9).getNumericCellValue();
//						sp.setSoLuong(soLuong);
//						sp.giaBan();
//						sp.setGiaKM(row.getCell(11).getNumericCellValue());
//						boolean checkIDLoaiSanPham = daoLoaiSanPham
//								.checkIdLoaiSanPham(sp.getIdLoaiSanPham().getIdLoaiSanPham());
//						boolean checkIDNhaCungCap = daoNhaCungCap
//								.checkIdNhaCungCap(sp.getIdNhaCungCap().getIdNhaCungCap());
//						if (!checkIDLoaiSanPham || !checkIDNhaCungCap) {
//							JOptionPane.showMessageDialog(this,
//									"Không thể thêm sản phẩm: Một hoặc nhiều khóa phụ không tồn tại hoặc không hợp lệ.");
//							continue;
//						}
//						if (daoSanPham.checkIdSanPham(sp.getIdSanPham())) {
//							daoSanPham.updateMerchandise(sp);
//						} else {
//							boolean result = daoSanPham.addMerchandise(sp);
//							loadDataIntoTable();
//							if (!result) {
//								System.out.println("Không thể thêm sản phẩm: " + sp.getIdSanPham());
//							}
//						}
//					}
//				}
//				JOptionPane.showMessageDialog(null, "Thêm thành công");
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null, e.getMessage());
//			}
//		}
//	}


    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }


    private Double parseDoubleWithMultiplePoints(String input) {
        String cleanedInput = input.replaceAll("[^\\d.]", "");
        cleanedInput = cleanedInput.contains(".")
                ? cleanedInput.substring(0, cleanedInput.indexOf(".") + 1)
                + cleanedInput.substring(cleanedInput.indexOf(".") + 1).replace(".", "")
                : cleanedInput;
        return cleanedInput.isEmpty() ? 0.0 : Double.parseDouble(cleanedInput);
    }

    public void exportExcel(String filePath) {
        double tongGiaNhap = 0;
        int tongSoLuong = 0;
        double tongGiaBan = 0;
        int rowCount = modelSP.getRowCount();

        java.util.List<Merchandise> merchandiseList = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            String idSanPham = (String) modelSP.getValueAt(i, 0);
            String tenSanPham = (String) modelSP.getValueAt(i, 1);
            String loaiSanPham = (String) modelSP.getValueAt(i, 2);
            String nhaCungCap = (String) modelSP.getValueAt(i, 3);
            Double kichThuoc = (Double) modelSP.getValueAt(i, 4);
            String mauSac = (String) modelSP.getValueAt(i, 5);
            String trangThaiStr = (String) modelSP.getValueAt(i, 6);
            ProductStatusEnum trangThai = ProductStatusEnum.getByName(trangThaiStr);
            String thueStr = ((String) modelSP.getValueAt(i, 7)).replaceAll("\\D+", "");
            Double thue = parseDoubleWithMultiplePoints(thueStr);
            String giaNhapStr = ((String) modelSP.getValueAt(i, 8)).replaceAll("\\D+", "");
            Double giaNhap = parseDoubleWithMultiplePoints(giaNhapStr);

            int soLuong = (int) modelSP.getValueAt(i, 9);
            Merchandise sp = new Merchandise();
            sp.setProductId(idSanPham);
            sp.setProductName(tenSanPham);

            if (loaiSanPham != null) {
                ProductType loaiSP = new ProductType();
                loaiSP.setProductTypeName(loaiSanPham);
                sp.setProductTypeId(loaiSP);
            }

            if (nhaCungCap != null) {
                Supplier ncc = new Supplier();
                ncc.setSupplierName(nhaCungCap);
                sp.setSupplierId(ncc);
            }
            sp.setSize(kichThuoc);
            sp.setColor(mauSac);
            sp.setStatus(trangThai);
            sp.setImportPrice(giaNhap);
            sp.setQuantity(soLuong);
            merchandiseList.add(sp);
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh sách sản phẩm");
            Row headerRow = sheet.createRow(0);

            String[] columnNames = {"ID Sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Nhà cung cấp", "Kích thước",
                    "Màu sắc", "Trạng thái", "Thuế", "Giá nhập", "Số lượng", "Giá bán"};

            for (int i = 0; i < columnNames.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnNames[i]);
            }

            int rowNumber = 1;
            for (Merchandise spc : merchandiseList) {
                Row row = sheet.createRow(rowNumber++);
                org.apache.poi.ss.usermodel.Cell idSanPhamCell = row.createCell(0);
                idSanPhamCell.setCellValue(spc.getProductId());
                org.apache.poi.ss.usermodel.Cell tenSanPhamCell = row.createCell(1);
                tenSanPhamCell.setCellValue(spc.getProductName());
                org.apache.poi.ss.usermodel.Cell loaiSanPhamCell = row.createCell(2);
                loaiSanPhamCell
                        .setCellValue(spc.getProductTypeId() != null ? spc.getProductTypeId().getProductTypeName() : "");
                org.apache.poi.ss.usermodel.Cell nhaCungCapCell = row.createCell(3);
                nhaCungCapCell
                        .setCellValue(spc.getSupplierId() != null ? spc.getSupplierId().getSupplierName() : "");
                org.apache.poi.ss.usermodel.Cell kichThuocCell = row.createCell(4);
                kichThuocCell.setCellValue(spc.getSize());
                org.apache.poi.ss.usermodel.Cell mauSacCell = row.createCell(5);
                mauSacCell.setCellValue(spc.getColor());
                org.apache.poi.ss.usermodel.Cell trangThaiCell = row.createCell(6);
                trangThaiCell.setCellValue(spc.getStatus() != null ? spc.getStatus().getDescription() : "");
                org.apache.poi.ss.usermodel.Cell thueCell = row.createCell(7);
                thueCell.setCellValue(spc.tax());
                org.apache.poi.ss.usermodel.Cell giaNhapCell = row.createCell(8);
                giaNhapCell.setCellValue(spc.getImportPrice());
                org.apache.poi.ss.usermodel.Cell soLuongCell = row.createCell(9);
                soLuongCell.setCellValue(spc.getQuantity());
                org.apache.poi.ss.usermodel.Cell giaBanCell = row.createCell(10);
                giaBanCell.setCellValue(spc.sellingPrice());
            }
            for (int i = 0; i < columnNames.length; i++) {
                sheet.autoSizeColumn(i);
            }
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }

            System.out.println("Dữ liệu SanPham đã được ghi vào tệp Excel thành công.");
            DialogUtils.showSuccessMessage(this, "Xuất thống kê excel thành công");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private void addMerchandise() {
        if (validataFieldsAndShowErrors()) {
            String idSanPham = txtIdSanPham.getText();
            String tenSanPham = txtTenSanPham.getText();
            double kichThuoc = Double.parseDouble(txtKichThuoc.getText());
            String mauSac = txtMauSac.getText();
            boolean trangThaiValue = chkTinhTrangKinhDoanh.isSelected();
            ProductStatusEnum trangThai = trangThaiValue ? ProductStatusEnum.ACTIVE : ProductStatusEnum.INACTIVE;
            double giaNhap = Double.parseDouble(txtGiaNhap.getText());
            String tenLoaiSanPham = (String) cbLoaiSanPham.getSelectedItem();
            String tenNhaCungCap = (String) cbNhaCungCap.getSelectedItem();
            int soLuong = Integer.parseInt(txtSoLuong.getText());
//			KhuyenMai khuyenMai = null;

            ProductType productType = null;
            for (ProductType item : mainController.getAllProductType()) {
                if (item.getProductTypeName().equals(tenLoaiSanPham)) {
                    productType = item;
                    break;
                }
            }
            Supplier supplier = null;
            for (Supplier item : mainController.getAllSuppliers()) {
                if (item.getSupplierName().equals(tenNhaCungCap)) {
                    supplier = item;
                    break;
                }
            }

            if (productType != null && supplier != null) {
                Merchandise sp = new Merchandise();
                sp.setProductId(idSanPham);
                sp.setProductName(tenSanPham);
                sp.setProductTypeId(productType);
                sp.setSupplierId(supplier);
                sp.setSize(kichThuoc);
                sp.setColor(mauSac);
                sp.setStatus(trangThai);
                sp.setImportPrice(giaNhap);
                sp.setQuantity(soLuong);
                try {
                    if (mainController.checkIdExist(idSanPham)) {
                        DialogUtils.showErrorDialog(MerchandiseView.this, "Trùng ID sản phẩm. Vui lòng chọn ID khác.");
                        refeshForm();
                        return;
                    } else {
                        mainController.addMerchandise(sp);
                        loadData();
                        refeshForm();
                        DialogUtils.showSuccessMessage(MerchandiseView.this, "Thêm sản phẩm thành công!");
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                DialogUtils.showErrorMessage(MerchandiseView.this, "Không tìm thấy loại sản phẩm hoặc nhà cung cấp!");

            }
        }
    }

    private boolean validataFieldsAndShowErrors() {
        String tenSanPham = txtTenSanPham.getText().trim();
        String mauSac = txtMauSac.getText().trim();
        String kichThuoc = txtKichThuoc.getText().trim();
        String soLuong = txtSoLuong.getText().trim();
        String giaNhap = txtGiaNhap.getText().trim();


        if (!isValidName(tenSanPham)) {
            showErrorAndFocus(this, "Tên sản phẩm không hợp lệ. Chỉ chấp nhận chữ và số và không có kí tự đặc biệt",
                    txtTenSanPham);
            return false;
        }

        if (!isValidMauSac(mauSac)) {
            showErrorAndFocus(this, "Màu sắc không hợp lệ. Chỉ chấp nhận chữ", txtMauSac);
            return false;
        }
        if (!isValidDouble(kichThuoc)) {
            showErrorAndFocus(this, "Kích thước không hợp lệ. Nhập số thực.", txtKichThuoc);
            return false;
        }

        if (!isValidDouble(giaNhap)) {
            showErrorAndFocus(this, "Giá nhập không hợp lệ. Nhập số thực.", txtGiaNhap);
            return false;
        }

        if (!isValidInt(soLuong)) {
            showErrorAndFocus(this, "Số lượng không hợp lệ. Nhập số nguyên.", txtSoLuong);
            return false;
        }
        if (tenSanPham.isEmpty() && mauSac.isEmpty() && kichThuoc.isEmpty() && soLuong.isEmpty() && giaNhap.isEmpty()) {
            showErrorAndFocus(this, "Vui lòng điền thông tin trước khi thêm.", getFirstEmptyTextField());

            return false;
        }
        return true;
    }

    private void showErrorAndFocus(Component parentComponent, String message, JTextField textField) {
        JOptionPane.showMessageDialog(parentComponent, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
        textField.requestFocusInWindow();
        textField.selectAll();
    }

    private JTextField getFirstEmptyTextField() {
        if (txtTenSanPham.getText().trim().isEmpty()) {
            return txtTenSanPham;
        } else if (txtMauSac.getText().trim().isEmpty()) {
            return txtMauSac;
        } else if (txtKichThuoc.getText().trim().isEmpty()) {
            return txtKichThuoc;
        } else if (txtSoLuong.getText().trim().isEmpty()) {
            return txtSoLuong;
        } else if (txtGiaNhap.getText().trim().isEmpty()) {
            return txtGiaNhap;
        }
        return null;
    }

    private boolean isValidName(String input) {
        return input.matches("^[\\p{L}0-9\\s]+$");
    }

    private boolean isValidMauSac(String input) {
        return input.matches("^[\\p{L}\\s]+$");
    }

    private boolean isValidDouble(String input) {
        if (input == null || input.trim().isEmpty()) {
            showErrorDialog("Giá trị không được để trống.");
            return false;
        }

        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            showErrorDialog("Giá trị không hợp lệ. Vui lòng nhập số thực.");
            return false;
        }
    }

    private boolean isValidInt(String input) {
        if (input == null || input.trim().isEmpty()) {
            showErrorDialog("Giá trị không được để trống.");
            return false;
        }

        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            showErrorDialog("Giá trị không hợp lệ. Vui lòng nhập số nguyên.");
            return false;
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void deleteMerchandise() {
        int row = table.getSelectedRow();
        if (row == -1) {
            DialogUtils.showErrorMessage(MerchandiseView.this, "Vui lòng chọn sản phẩm cần xóa!");
        } else {
            try {
                int hoiNhac = JOptionPane.showConfirmDialog(this, "Bạn có chắc xóa không!", "Cảnh báo",
                        JOptionPane.YES_NO_OPTION);
                if (hoiNhac == JOptionPane.YES_OPTION) {
                    modelSP.removeRow(row);
                    String idSanPham = txtIdSanPham.getText();
                    mainController.deleteMerchandise(idSanPham);
                    DialogUtils.showSuccessMessage(MerchandiseView.this, "Xóa sản phẩm thành công!");
                    refeshForm();
                }
            } catch (Exception e) {
                e.printStackTrace();
                DialogUtils.showErrorMessage(MerchandiseView.this, "Không thể xóa sản phẩm này!");
            }
        }

    }

    private void refeshForm() {
        txtTenSanPham.setText("");
        cbLoaiSanPham.setSelectedIndex(0);
        cbNhaCungCap.setSelectedIndex(0);
        cbLoaiSanPhamSearch.setSelectedIndex(0);
        cbNhaCungCapSearch.setSelectedIndex(0);
        txtKichThuoc.setText("");
        txtMauSac.setText("");
        txtGiaBan.setText("");
        txtSoLuong.setText("");
        txtGiaBan.setText("");
        txtGiaNhap.setText("");
        txtTuKhoa.setText("");
        if (cbLoaiSanPham.getItemCount() > 1) {
            cbLoaiSanPham.setSelectedIndex(1);
        }

        if (cbNhaCungCap.getItemCount() > 1) {
            cbNhaCungCap.setSelectedIndex(1);
        }
        txtTenSanPham.selectAll();
        table.clearSelection();
        txtTenSanPham.requestFocus();
    }

    @SneakyThrows
    private void updateMerchandise() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int update = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin sản phẩm này", "Thông báo",
                    JOptionPane.YES_NO_OPTION);
            if (update == JOptionPane.YES_OPTION) {
                if (!validataFieldsAndShowErrors()) {
                    return;
                }
                String idSanPham = txtIdSanPham.getText().trim();
                String tenSanPham = txtTenSanPham.getText().trim();
                double kichThuoc = Double.parseDouble(txtKichThuoc.getText());
                String mauSac = txtMauSac.getText();
                boolean trangThaiValue = chkTinhTrangKinhDoanh.isSelected();
                ProductStatusEnum trangThai = trangThaiValue ? ProductStatusEnum.ACTIVE : ProductStatusEnum.INACTIVE;
                double giaNhap = Double.parseDouble(txtGiaNhap.getText());
                int soLuong = Integer.parseInt(txtSoLuong.getText());
                String tenLoaiSanPham = (String) cbLoaiSanPham.getSelectedItem();
                String tenNhaCungCap = (String) cbNhaCungCap.getSelectedItem();

                ProductType productType = null;
                for (ProductType item : mainController.getAllProductType()) {
                    if (item.getProductTypeName().equals(tenLoaiSanPham)) {
                        productType = item;
                        break;
                    }
                }
                Supplier supplier = null;
                for (Supplier item : mainController.getAllSuppliers()) {
                    if (item.getSupplierName().equals(tenNhaCungCap)) {
                        supplier = item;
                        break;
                    }
                }

                if (productType != null && supplier != null) {
                    Merchandise sp = new Merchandise();
                    sp.setProductId(idSanPham);
                    sp.setProductName(tenSanPham);
                    sp.setProductTypeId(productType);
                    sp.setSupplierId(supplier);
                    sp.setSize(kichThuoc);
                    sp.setColor(mauSac);
                    sp.setStatus(trangThai);
                    sp.setImportPrice(giaNhap);
                    sp.setQuantity(soLuong);

                    mainController.updateMerchandise(sp);
                    DialogUtils.showSuccessMessage(MerchandiseView.this, "Cập nhật sản phẩm thành công!");
                    loadData();
                    refeshForm();
                } else {
                    DialogUtils.showErrorMessage(MerchandiseView.this, "Không tìm thấy loại sản phẩm hoặc nhà cung cấp!");
                }
            }

        } else {
            DialogUtils.showErrorMessage(MerchandiseView.this, "Vui lòng chọn sản phẩm cần cập nhật!");
        }

    }

    private void loadtableByLoaiSanPham(String selectedLoaiSanPham) {
        loadTableByCriteria("productType", selectedLoaiSanPham);
    }

    private void loadtableByNhaCungCap(String selectedNhaCungCap) {
        loadTableByCriteria("supplier", selectedNhaCungCap);

    }

    private void loadTableByCriteria(String criteria, String selectedCriteria) {
        modelSP.setRowCount(0);
        try {
            java.util.List<Merchandise> merchandiseList = null;
            if (criteria.equals("supplier")) {
                merchandiseList = mainController.findSupplierByNameMerchandise(selectedCriteria);
            } else if (criteria.equals("productType")) {
                merchandiseList = mainController.findBookByProductTypeMerchandise(selectedCriteria);
            }
            if (merchandiseList.isEmpty()) {
                DialogUtils.showErrorMessage(MerchandiseView.this, "Không tìm thấy sản phẩm!");
                if (criteria.equals("supplier")) {
                    cbNhaCungCapSearch.setSelectedItem("Tất cả");
                } else if (criteria.equals("productType")) {
                    cbLoaiSanPhamSearch.setSelectedItem("Tất cả");
                }
            } else {
                for (Merchandise merchandise : merchandiseList) {
                    String idSanPham = merchandise.getProductId();
                    String tenSanPham = merchandise.getProductName();
                    String loaiSanPham = merchandise.getProductTypeId().getProductTypeName();
                    String nhaCungCap = merchandise.getSupplierId().getSupplierName();
                    double kichThuoc = merchandise.getSize();
                    String mauSac = merchandise.getColor();
                    String trangThai = merchandise.getStatus().getDescription();
                    double thue = merchandise.tax();
                    double giaNhap = merchandise.getImportPrice();
                    int soLuong = merchandise.getQuantity();
                    double giaBan = merchandise.sellingPrice();
                    modelSP.addRow(new Object[]{idSanPham, tenSanPham, loaiSanPham, nhaCungCap, kichThuoc, mauSac,
                            trangThai, currencyFormat.format(thue), currencyFormat.format(giaNhap), soLuong,
                            currencyFormat.format(giaBan)});
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();
        if (source == cbLoaiSanPhamSearch) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedLoaiSanPham = (String) cbLoaiSanPhamSearch.getSelectedItem();
                if (selectedLoaiSanPham.equals("Tất cả")) {
                    loadData();
                } else if (selectedLoaiSanPham != null && !selectedLoaiSanPham.isEmpty()) {
                    loadtableByLoaiSanPham(selectedLoaiSanPham);
                } else {
                    loadData();
                }
            }
        } else if (source == cbNhaCungCapSearch) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedNhaCungCap = (String) cbNhaCungCapSearch.getSelectedItem();
                if (selectedNhaCungCap.equals("Tất cả")) {
                    loadData();
                } else if (selectedNhaCungCap != null && !selectedNhaCungCap.isEmpty()) {
                    loadtableByNhaCungCap(selectedNhaCungCap);
                } else {
                    loadData();

                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtIdSanPham.setText(modelSP.getValueAt(row, 0).toString());
            txtTenSanPham.setText(modelSP.getValueAt(row, 1).toString());
            cbLoaiSanPham.setSelectedItem(modelSP.getValueAt(row, 2).toString());
            cbNhaCungCap.setSelectedItem(modelSP.getValueAt(row, 3).toString());
            txtKichThuoc.setText(modelSP.getValueAt(row, 4).toString());
            txtMauSac.setText(modelSP.getValueAt(row, 5).toString());

            String trangThaiValue = modelSP.getValueAt(row, 6).toString();
            chkTinhTrangKinhDoanh.setSelected(trangThaiValue.equals("Đang kinh doanh"));

            String giaNhapStr = modelSP.getValueAt(row, 8).toString().replaceAll("\\D+", "");
            String giaBanStr = modelSP.getValueAt(row, 10).toString().replaceAll("\\D+", "");

            txtGiaNhap.setText(giaNhapStr);
            txtGiaBan.setText(giaBanStr);
//			txtGiaNhap.setText(modelSP.getValueAt(row, 8).toString());
            txtSoLuong.setText(modelSP.getValueAt(row, 9).toString());
//			txtGiaBan.setText(modelSP.getValueAt(row, 10).toString());
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Object o = e.getSource();
            if (o == txtTenSanPham || o == txtKichThuoc || o == txtMauSac || o == txtSoLuong || o == txtGiaNhap
                    || o == cbLoaiSanPham || o == cbNhaCungCap) {
                addMerchandise();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_F5) {
            refeshForm();
        } else if (table.getSelectedRow() != -1) {
            if (e.getKeyCode() == KeyEvent.VK_F5) {
                refeshForm();
                loadData();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_TAB) {
            tabbedPane.setSelectedIndex(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        SwingUtilities.invokeLater(() -> {
            Object o = e.getSource();
            if (o.equals(txtTuKhoa)) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
                table.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter("(?i)" + txtTuKhoa.getText().trim(), 1));
            } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                refeshForm();
                loadData();
            } else if (e.getKeyCode() == KeyEvent.VK_F10) {
                refeshForm();
                loadData();
            } else if (o.equals(txtGiaNhap)) {
                calculateSellingPrice(txtGiaNhap, txtGiaBan);
            }
        });
    }

    private void calculateSellingPrice(JTextField txtGiaNhap2, JTextField txtGiaBan2) {
        try {
            double giaNhap = Double.parseDouble(txtGiaNhap2.getText());
            double thue = giaNhap * 0.05;
            double sellingPrice = giaNhap + (giaNhap * 0.55) + thue;
            txtGiaBan2.setText(String.valueOf(sellingPrice));
        } catch (NumberFormatException ex) {
            txtGiaBan2.setText("Vui lòng nhập số hợp lệ.");
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtIdSanPham.setText(modelSP.getValueAt(row, 0).toString());
            txtTenSanPham.setText(modelSP.getValueAt(row, 1).toString());
            cbLoaiSanPham.setSelectedItem(modelSP.getValueAt(row, 2).toString());
            cbNhaCungCap.setSelectedItem(modelSP.getValueAt(row, 3).toString());
            txtKichThuoc.setText(modelSP.getValueAt(row, 4).toString());
            txtMauSac.setText(modelSP.getValueAt(row, 5).toString());

            String trangThaiValue = modelSP.getValueAt(row, 6).toString();
            chkTinhTrangKinhDoanh.setSelected(trangThaiValue.equals("Đang kinh doanh"));

            String giaNhapStr = modelSP.getValueAt(row, 8).toString().replaceAll("\\D+", "");
            String giaBanStr = modelSP.getValueAt(row, 10).toString().replaceAll("\\D+", "");

            txtGiaNhap.setText(giaNhapStr);
            txtGiaBan.setText(giaBanStr);
//			txtGiaNhap.setText(modelSP.getValueAt(row, 8).toString());
            txtSoLuong.setText(modelSP.getValueAt(row, 9).toString());
//			txtGiaBan.setText(modelSP.getValueAt(row, 10).toString());
        }
    }
}