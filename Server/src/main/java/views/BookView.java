package views;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Currency;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import controller.MainController;
import controller.MainControllerInterface;
import lombok.SneakyThrows;
import models.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.toedter.calendar.JDateChooser;

import util.DialogUtils;
import util.GeneratorIDAuto;
import util.ProductStatusEnum;

public class BookView extends JPanel
        implements ActionListener, MouseListener, KeyListener, ItemListener, DocumentListener, ListSelectionListener {
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private JLabel lblIDSanPham;
    private JPanel pnCenter, pnChucNang, pnDanhMuc, pnMain;
    private JButton btnThemSP, btndeleteBook, btnCapNhatSP, btnLamMoi;
    private JLabel lblTuKhoa;
    private JTextField txtTuKhoa;
    private JButton btnTimKiem;
    private JButton btnXuatExCel;
    private JButton btnXemTatCa;
    private JButton btnimportBook;
    private JPanel pnChucNangCha;
    private JPanel pnChucNangTimKiem;


    private JTable table;
    private DefaultTableModel model;


    private JLabel lblTenSanPham;
    private JLabel lblTacGia;
    private JLabel lblTheLoai;
    private JLabel lblNamXuatBan;
    private JLabel lblISBN;
    private JLabel lblSoTrang;
    private JLabel lblLoaiSanPham;
    private JLabel lblNhaCungCap;
    private JLabel lblKichThuoc;
    private JLabel lblMauSac;
    private JLabel lblTrangThai;
    private JLabel lblGiaNhap;
    private JLabel lblSoLuong;
    private JLabel lblGiaBan;
    private JLabel lblLoaiSanPhamSearch;
    private JLabel lblNhaCungCapSearch;
    private JLabel lblTacGiaSearch;
    private JLabel lblTheLoaiSearch;

    private JTextField txtTenSanPham;
    private JTextField txtIdSanPham;
    private JTextField txtSoTrang;
    private JTextField txtKichThuoc;
    private JTextField txtMauSac;
    private JTextField txtGiaNhap;
    private JTextField txtSoLuong;
    private JTextField txtGiaBan;

    private JComboBox<String> cbLoaiTacGia, cbLoaiTheLoai;
    private JComboBox<String> cbLoaiSanPhamSearch;
    private JComboBox<String> cbNhaCungCapSearch;
    private JComboBox<String> cbTacGiaSearch;
    private JComboBox<String> cbTheLoaiSearch;
    private JComboBox<String> cbLoaiSanPham;
    private JComboBox<String> cbNhaCungCap;

    private JCheckBox chkTinhTrangKinhDoanh;

    private JDateChooser chooserXuatBan;

    private MaskFormatter formatter;
    JFormattedTextField isbnField;

    private ProductStatusEnum statusEnum;
    private static MainControllerInterface mainController;

    static {
        try {
            mainController = new MainController();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private DecimalFormat df;
    private GeneratorIDAuto autoID;

    public BookView(MainControllerInterface mainController) {

        this.mainController = mainController;
        autoID = new GeneratorIDAuto();
        init();
    }


    @SneakyThrows
    private void init() {
        setLayout(new BorderLayout());


        df = new DecimalFormat("#,###.##");
        mainController = new MainController();
        currencyFormat.setCurrency(Currency.getInstance("VND"));
        table = new JTable();
        model = new DefaultTableModel();


        // center
        pnCenter = new JPanel();
        pnCenter.setLayout(new GridLayout(5, 6, 10, 10));
        pnCenter.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));

        lblIDSanPham = new JLabel("ID Sản Phẩm(*):");
        txtIdSanPham = new JTextField();
        txtIdSanPham.setEditable(false);
        lblTenSanPham = new JLabel("Tên Sản Phẩm(*):");
        txtTenSanPham = new JTextField();
        lblTacGia = new JLabel("Tác Giả:");
        cbLoaiTacGia = new JComboBox<>();
        lblTheLoai = new JLabel("Thể Loại:");
        cbLoaiTheLoai = new JComboBox<>();
        lblNamXuatBan = new JLabel("Năm xuất bản:");
        chooserXuatBan = new JDateChooser();
        chooserXuatBan.setPreferredSize(new Dimension(200, 22));
        chooserXuatBan.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chooserXuatBan.setDateFormatString("dd/MM/yyyy");
        chooserXuatBan.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
        chooserXuatBan.setFont(new Font("SansSerif", Font.PLAIN, 15));
        chooserXuatBan.getCalendarButton().setPreferredSize(new Dimension(30, 24));
        chooserXuatBan.getCalendarButton().setBackground(new Color(102, 0, 153));
        chooserXuatBan.getCalendarButton().setToolTipText("Chọn ngày xuất bản");

        try {
            formatter = new MaskFormatter("###-###-###-###-#");
            formatter.setPlaceholderCharacter('X');
            isbnField = new JFormattedTextField(formatter);
            isbnField.setColumns(17);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        lblISBN = new JLabel("ISBN:");

        lblSoTrang = new JLabel("Số Trang:");
        txtSoTrang = new JTextField();
        lblLoaiSanPham = new JLabel("Loại Sản Phẩm:");
        cbLoaiSanPham = new JComboBox<>();
        lblNhaCungCap = new JLabel("Nhà Cung Cấp:");
        cbNhaCungCap = new JComboBox<>();
        lblKichThuoc = new JLabel("Kích Thước:");
        txtKichThuoc = new JTextField();
        lblMauSac = new JLabel("Màu Sắc:");
        txtMauSac = new JTextField();
        lblTrangThai = new JLabel("Tình Trạng Kinh Doanh:");
        chkTinhTrangKinhDoanh = new JCheckBox();
        chkTinhTrangKinhDoanh.setSelected(true);
        lblSoLuong = new JLabel("Số Lượng:");
        txtSoLuong = new JTextField();
        lblGiaNhap = new JLabel("Giá Nhập:");
        txtGiaNhap = new JTextField();
        lblGiaBan = new JLabel("Giá Bán:");
        txtGiaBan = new JTextField();
        txtGiaBan.setEditable(false);

        pnCenter.add(lblIDSanPham);
        pnCenter.add(txtIdSanPham);

        pnCenter.add(lblTenSanPham);
        pnCenter.add(txtTenSanPham);

        pnCenter.add(lblNamXuatBan);
        pnCenter.add(chooserXuatBan);

        pnCenter.add(lblTacGia);
        pnCenter.add(cbLoaiTacGia);

        pnCenter.add(lblISBN);
        pnCenter.add(isbnField);

        pnCenter.add(lblSoTrang);
        pnCenter.add(txtSoTrang);

        pnCenter.add(lblTheLoai);
        pnCenter.add(cbLoaiTheLoai);

        pnCenter.add(lblKichThuoc);
        pnCenter.add(txtKichThuoc);

        pnCenter.add(lblMauSac);
        pnCenter.add(txtMauSac);

        pnCenter.add(lblLoaiSanPham);
        pnCenter.add(cbLoaiSanPham);

        pnCenter.add(lblGiaNhap);
        pnCenter.add(txtGiaNhap);

        pnCenter.add(lblSoLuong);
        pnCenter.add(txtSoLuong);

        pnCenter.add(lblNhaCungCap);
        pnCenter.add(cbNhaCungCap);

        pnCenter.add(lblGiaBan);
        pnCenter.add(txtGiaBan);

        pnCenter.add(lblTrangThai);
        pnCenter.add(chkTinhTrangKinhDoanh);

        pnMain = new JPanel(new BorderLayout());

        pnChucNangCha = new JPanel(new BorderLayout(6, 8));
        pnChucNang = new JPanel(new GridLayout(1, 4, 10, 40));
        pnChucNangCha.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        btnThemSP = new JButton("THÊM SÁCH");
        btnCapNhatSP = new JButton("CẬP NHẬT SÁCH");
        btndeleteBook = new JButton("XÓA SÁCH");
        lblTuKhoa = new JLabel("Tìm kiếm:");
        txtTuKhoa = new JTextField();
        btnTimKiem = new JButton("Tìm kiếm");
        btnXemTatCa = new JButton("Xem tất cả");
        btnLamMoi = new JButton("LÀM MỚI");
        btnimportBook = new JButton("NHẬP SÁCH EXCEL ");

        btnXuatExCel = new JButton("XUẤT EXCEL");
//		btnXuatExCel.setEnabled(false);

        ImageIcon iconThem = new ImageIcon(getClass().getResource("/icons/add.png"));
        ImageIcon iconCapNhat = new ImageIcon(getClass().getResource("/icons/capnhat.png"));
        ImageIcon iconLamMoi = new ImageIcon(getClass().getResource("/icons/lammoi.png"));
        ImageIcon iconXoa = new ImageIcon(getClass().getResource("/icons/xoa.png"));

        btnCapNhatSP.setIcon(iconCapNhat);
        btnThemSP.setIcon(iconThem);
        btnLamMoi.setIcon(iconLamMoi);
        btndeleteBook.setIcon(iconXoa);

//		ImageIcon iconExcel = new ImageIcon(getClass().getResource("/icons/Excel.png"));

        btnThemSP.setIcon(iconThem);
        btnLamMoi.setIcon(iconLamMoi);
        btnCapNhatSP.setIcon(iconCapNhat);
        btndeleteBook.setIcon(iconXoa);
//		btnXuatExCel.setIcon(iconExcel);

        pnChucNang.add(btnThemSP);
        pnChucNang.add(btnimportBook);
        pnChucNang.add(btnCapNhatSP);
        pnChucNang.add(btnLamMoi);
        pnChucNang.add(btndeleteBook);
        pnChucNang.add(btnXuatExCel);

        pnChucNangTimKiem = new JPanel(new GridLayout(1, 8, 10, 10));
        lblLoaiSanPhamSearch = new JLabel("Loại sản phẩm:");
        lblNhaCungCapSearch = new JLabel("Nhà cung cấp:");
        lblTacGiaSearch = new JLabel("Tác giả:");
        lblTheLoaiSearch = new JLabel("Thể loại:");
        cbLoaiSanPhamSearch = new JComboBox<>();
        cbNhaCungCapSearch = new JComboBox<>();
        cbTacGiaSearch = new JComboBox<>();
        cbTheLoaiSearch = new JComboBox<>();

        pnChucNangTimKiem.add(lblLoaiSanPhamSearch);
        pnChucNangTimKiem.add(cbLoaiSanPhamSearch);
        pnChucNangTimKiem.add(lblNhaCungCapSearch);
        pnChucNangTimKiem.add(cbNhaCungCapSearch);
        pnChucNangTimKiem.add(lblTacGiaSearch);
        pnChucNangTimKiem.add(cbTacGiaSearch);
        pnChucNangTimKiem.add(lblTheLoaiSearch);
        pnChucNangTimKiem.add(cbTheLoaiSearch);
        pnChucNangTimKiem.add(lblTuKhoa);
        pnChucNangTimKiem.add(txtTuKhoa);
        pnChucNangTimKiem.add(btnXemTatCa);

        pnChucNangCha.add(pnChucNang, BorderLayout.NORTH);
        pnChucNangCha.add(pnChucNangTimKiem, BorderLayout.SOUTH);
        pnMain.add(pnChucNangCha, BorderLayout.NORTH);

        pnDanhMuc = new JPanel(new BorderLayout());
        pnDanhMuc.setBorder(BorderFactory.createTitledBorder("Danh mục"));
        model = new DefaultTableModel();
        table = new JTable();
        model.addColumn("ID Sách");
        model.addColumn("Tên Sách");
        model.addColumn("Tác Giả");
        model.addColumn("Thể Loại");
        model.addColumn("Năm Xuất Bản");
        model.addColumn("ISBN");
        model.addColumn("Số Trang");
        model.addColumn("Loại Sản Phẩm");
        model.addColumn("Nhà Cung Cấp");
        model.addColumn("Kích Thước");
        model.addColumn("Màu Sắc");
        model.addColumn("Trạng Thái");
        model.addColumn("Thuế");
        model.addColumn("Số Lượng");
        model.addColumn("Giá Nhập");
        model.addColumn("Giá Bán");
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        pnDanhMuc.add(scrollPane);
        pnMain.add(pnDanhMuc, BorderLayout.CENTER);

        add(pnCenter, BorderLayout.NORTH);
        add(pnMain, BorderLayout.CENTER);
        btnCapNhatSP.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnThemSP.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnXemTatCa.addActionListener(this);
        btndeleteBook.addActionListener(this);
        btnXuatExCel.addActionListener(this);
        btnimportBook.addActionListener(this);
        table.addMouseListener(this);
        table.getSelectionModel();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        txtGiaNhap.addKeyListener(this);
        cbLoaiSanPhamSearch.addItemListener(this);
        cbNhaCungCapSearch.addItemListener(this);
        cbTacGiaSearch.addItemListener(this);
        cbTheLoaiSearch.addItemListener(this);
        txtTuKhoa.addKeyListener(this);
        table.getSelectionModel().addListSelectionListener(this);

//		txtISBN.getDocument().addDocumentListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        table.requestFocus();
        loadData();
        loadComboBoxByLoaiSanPham();
        loadComboxBoxByNhaCungCap();
        loadComboBoxByTheLoai();
        loadComBoBoxByTacGia();
        refreshForm();
        txtTenSanPham.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                int row = table.getSelectedRow();
                if (row == -1)
                    txtIdSanPham.setText(autoID.autoID("SP"));
                else
                    txtIdSanPham.setText(model.getValueAt(row, 0).toString());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_RELEASED) {
                    if (e.getKeyCode() == KeyEvent.VK_F5) {
                        refreshForm();
                        loadData();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_INSERT) {
                    deleteBook();
                } else if (e.getKeyCode() == KeyEvent.VK_F2) {
                    updateBook();
                } else if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0 && e.getKeyCode() == KeyEvent.VK_S) {
                    txtTuKhoa.requestFocusInWindow();
                }
                return false;
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnLamMoi)) {
            refreshForm();
        } else if (o.equals(btnThemSP)) {
            addBook();
        } else if (o.equals(btnCapNhatSP)) {
            updateBook();
        } else if (o.equals(btndeleteBook)) {
            deleteBook();
        } else if (o.equals(btnXemTatCa)) {
            refreshForm();
            loadData();
        } else if (o.equals(btnXuatExCel)) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = System.getProperty("user.dir") + "/src/main/resources/DataExports/Sach/S_" + timeStamp
                    + ".xlsx";
            exportExcel(filePath);

        } else if (o.equals(btnimportBook)) {
            try {
                importBook();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @SneakyThrows
    private void loadComboBoxByTheLoai() {
        java.util.List<Category> categories = mainController.getAllCategory();
        cbLoaiTheLoai.removeAllItems();
        cbTheLoaiSearch.addItem("Tất cả");
        for (Category category : categories) {
            cbLoaiTheLoai.addItem(category.getCategoryName());
            cbTheLoaiSearch.addItem(category.getCategoryName());
        }
    }

    @SneakyThrows
    private void loadData() {
        model.setRowCount(0);
        java.util.List<Book> dsSach = mainController.getAllBook();
        for (Book s : dsSach) {
            double thuePhanTram = s.tax() * 100;
            df = new DecimalFormat("#,###.##");
            String idSanPham = s.getProductId();
            String tenSanPham = s.getProductName();
            String tenTacGia = s.getAuthorId() != null ? s.getAuthorId().getAuthorName() : "";
            String tenTheLoai = s.getCategoryId() != null ? s.getCategoryId().getCategoryName() : "";
            Date date = s.getPublicationYear() != null ? Date.from(s.getPublicationYear().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
            LocalDate localDate = date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            String isbn = s.getISBN();
            int soTrang = s.getNumberOfPages();
            String loaiSanPham = s.getProductTypeId() != null ? s.getProductTypeId().getProductTypeName() : "";
            String nhaCungCap = s.getSupplierId() != null ? s.getSupplierId().getSupplierName() : "";
            double kichThuoc = s.getSize();
            String mauSac = s.getColor();
            String trangThai = s.getStatus().getDescription();
            double thue = s.tax();
            int soLuong = s.getQuantity();
            double giaNhap = s.getImportPrice();
            double giaBan = s.sellingPrice();

            model.addRow(new Object[]{idSanPham, tenSanPham, tenTacGia, tenTheLoai,
                    localDate, isbn, soTrang, loaiSanPham, nhaCungCap, kichThuoc, mauSac,
                    trangThai, currencyFormat.format(thue), soLuong, currencyFormat.format(giaNhap),
                    currencyFormat.format(giaBan)});
        }
    }

    @SneakyThrows
    private void loadComBoBoxByTacGia() {
        java.util.List<Author> authors = mainController.getAllAuthor();
        cbLoaiTacGia.removeAllItems();
        cbTacGiaSearch.addItem("Tất cả");
        for (Author author : authors) {
            cbLoaiTacGia.addItem(author.getAuthorName());
            cbTacGiaSearch.addItem(author.getAuthorName());
        }
    }

    @SneakyThrows
    private void loadComboBoxByLoaiSanPham() {
        java.util.List<ProductType> productTypes = mainController.getAllProductType();
        cbLoaiSanPham.removeAllItems();
        cbLoaiSanPhamSearch.addItem("Tất cả");
        for (ProductType productType : productTypes) {
            cbLoaiSanPham.addItem(productType.getProductTypeName());
            cbLoaiSanPhamSearch.addItem(productType.getProductTypeName());
        }
    }

    @SneakyThrows
    private void loadComboxBoxByNhaCungCap() {
        java.util.List<Supplier> suppliers = mainController.getAllSuppliers();
        cbNhaCungCap.removeAllItems();
        cbNhaCungCapSearch.addItem("Tất cả");
        for (Supplier supplier : suppliers) {
            cbNhaCungCap.addItem(supplier.getSupplierName());
            cbNhaCungCapSearch.addItem(supplier.getSupplierName());
        }
    }

    private void updateIdBook() {
        if (table.getSelectedRow() == -1) {
            Date currentTime = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String formattedTime = "S" + sdf.format(currentTime);
            txtIdSanPham.setText(formattedTime);
        }
    }

    public void importBook() throws SQLException {
        String defaultCurrentDirectoryPath = System.getProperty("user.dir") + "/src/main/resources/DataImports";
        JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
        excelFileChooser.setDialogTitle("Chọn File Excel");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("TẤT CẢ CÁC FILE EXCEL", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showOpenDialog(null);

        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            File excelFile = excelFileChooser.getSelectedFile();

            try (FileInputStream fis = new FileInputStream(excelFile); Workbook workbook = new XSSFWorkbook(fis)) {
                xulyTrangAuthor(workbook.getSheetAt(0));
                xulyTrangCategory(workbook.getSheetAt(1));
                xulyTrangSupplier(workbook.getSheetAt(2));
                xulyTrangProductType(workbook.getSheetAt(3));
                xulyTrangSanPhamCon(workbook.getSheetAt(4));
                loadComboBoxByLoaiSanPham();
                loadComBoBoxByTacGia();
                loadComboBoxByTheLoai();
                loadComboxBoxByNhaCungCap();
                JOptionPane.showMessageDialog(null, "Thêm thành công");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    @SneakyThrows
    private void xulyTrangSanPhamCon(Sheet sheet) throws SQLException {
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Book s = new Book();
                s.setProductId(row.getCell(0).getStringCellValue());
                s.setProductName(row.getCell(1).getStringCellValue());
                s.setAuthorId(new Author(row.getCell(2).getStringCellValue()));
                s.setCategoryId(new Category(row.getCell(3).getStringCellValue()));
                Date namXuatBanUtil = row.getCell(4).getDateCellValue();
                LocalDate namXuatBan = namXuatBanUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                s.setPublicationYear(namXuatBan);
                s.setISBN(row.getCell(5).getStringCellValue());
                int soTrang = (int) row.getCell(6).getNumericCellValue();
                s.setNumberOfPages(soTrang);
                s.setProductTypeId(new ProductType(row.getCell(7).getStringCellValue()));
                s.setSupplierId(new Supplier(row.getCell(8).getStringCellValue()));
                s.setSize(row.getCell(9).getNumericCellValue());
                s.setColor(row.getCell(10).getStringCellValue());
                int trangThaiValue = (int) row.getCell(11).getNumericCellValue();
                ProductStatusEnum status = ProductStatusEnum.getById(trangThaiValue);
                s.setStatus(status);
                s.tax();
                int soLuong = (int) row.getCell(13).getNumericCellValue();
                s.setQuantity(soLuong);
                s.setImportPrice(row.getCell(14).getNumericCellValue());
                s.sellingPrice();
//                s.s(row.getCell(16).getNumericCellValue());
                boolean checkIDLoaiSanPham = mainController.checkProductTypeExist(s.getProductTypeId().getProductTypeId());
                boolean checkIDNhaCungCap = mainController.checkSupplierId(s.getSupplierId().getSupplierId());

                boolean checkIDTacGia = mainController.checkIdAuthor(s.getAuthorId().getAuthorId());

                boolean checkIDTheLoai = mainController.checkIdCategory(s.getCategoryId().getIdCategory());

                if (!checkIDLoaiSanPham || !checkIDNhaCungCap || !checkIDTacGia || !checkIDTheLoai) {
                    JOptionPane.showMessageDialog(this,
                            "Không thể thêm sản phẩm: Một hoặc nhiều khóa phụ không tồn tại hoặc không hợp lệ.");
                    continue;
                }
                if (mainController.checkIdBook(s.getProductId())) {
                    mainController.updateBook(s);
                    refreshForm();
                } else {
                    boolean result = mainController.addBook(s);
                    loadData();
                    refreshForm();
                    if (!result) {
                        System.out.println("Không thể thêm sản phẩm: " + s.getProductId());
                    }
                }
            }
        }
    }

    private void xulyTrangCategory(Sheet sheet) {
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Category tl = new Category();
                tl.setIdCategory(row.getCell(0).getStringCellValue());
                tl.setCategoryName(row.getCell(1).getStringCellValue());
                int soLuong = (int) row.getCell(2).getNumericCellValue();
                tl.setBookQuantity(soLuong);
                try {
                    if (!mainController.checkIdCategory(tl.getIdCategory())) {
                        boolean result = mainController.addCategory(tl);
                        if (!result) {
                            System.out.println("Không thể thêm thể loại: " + tl.getIdCategory());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void xulyTrangAuthor(Sheet sheet) {
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Author author = new Author();

                author.setAuthorId(row.getCell(0).getStringCellValue());

                author.setAuthorName(row.getCell(1).getStringCellValue());

                Cell namXuatBanCell = row.getCell(2);
                if (namXuatBanCell != null) {
                    if (namXuatBanCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(namXuatBanCell)) {
                        Date namXuatBanUtil = namXuatBanCell.getDateCellValue();
                        LocalDate namXuatBan = namXuatBanUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        author.setDate(namXuatBan);
                    } else {

                    }
                } else {
                }


                int soLuong = (int) row.getCell(3).getNumericCellValue();
                author.setNumberOfWorks(soLuong);

                try {
                    if (mainController.checkIdAuthor(author.getAuthorId())) {
                        mainController.updateAuthor(author);
                    } else {
                        mainController.addAuthor(author);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void xulyTrangProductType(Sheet sheet) {
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                ProductType lsp = new ProductType();
                lsp.setProductTypeId(row.getCell(0).getStringCellValue());
                lsp.setProductTypeName(row.getCell(1).getStringCellValue());

                try {
                    if (!mainController.checkProductTypeExist(lsp.getProductTypeId())) {
                        boolean result = mainController.addProductType(lsp);
                        if (!result) {
                            System.out.println("Không thể thêm loại sản phẩm: " + lsp.getProductTypeId());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void xulyTrangSupplier(Sheet sheet) {
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

//	private void importBook() throws SQLException {
//		String defaultCurrentDirectoryPath = System.getProperty("user.dir") + "/src/main/resources/DataImports";
//		JFileChooser exelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
//		exelFileChooser.setDialogTitle("Select Excel file:");
//		FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
//		exelFileChooser.setFileFilter(fnef);
//		int excelChooser = exelFileChooser.showOpenDialog(null);
//		if (excelChooser == JFileChooser.APPROVE_OPTION) {
//			File excelFile = exelFileChooser.getSelectedFile();
//			try (FileInputStream fis = new FileInputStream(excelFile); Workbook workbook = new XSSFWorkbook(fis)) {
//				Sheet sheet = workbook.getSheetAt(0);
//				for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
//					Row row = sheet.getRow(rowIndex);
//					if (row != null) {
//						SachCon s = new SachCon();
//						s.setIdSanPham(row.getCell(0).getStringCellValue());
//						s.setTenSanPham(row.getCell(1).getStringCellValue());
//						s.setTacGia(new TacGia(row.getCell(2).getStringCellValue()));
//						s.setTheLoai(new TheLoai(row.getCell(3).getStringCellValue()));
//						java.util.Date namXuatBanUtil = row.getCell(4).getDateCellValue();
//						java.sql.Date namXuatBanSql = new java.sql.Date(namXuatBanUtil.getTime());
//						s.setNamXuatBan(namXuatBanSql);
//						s.setISBN(row.getCell(5).getStringCellValue());
//						int soTrang = (int) row.getCell(6).getNumericCellValue();
//						s.setSoTrang(soTrang);
//						s.setIdLoaiSanPham(new LoaiSanPham(row.getCell(7).getStringCellValue()));
//						s.setIdNhaCungCap(new NhaCungCap(row.getCell(8).getStringCellValue()));
//						s.setKichThuoc(row.getCell(9).getNumericCellValue());
//						s.setMauSac(row.getCell(10).getStringCellValue());
//						int trangThaiValue = (int) row.getCell(11).getNumericCellValue();
//						TrangThaiSPEnum trangThaiEnum = TrangThaiSPEnum.getById(trangThaiValue);
//						s.setTrangThai(trangThaiEnum);
//						s.thue();
//						int soLuong = (int) row.getCell(13).getNumericCellValue();
//						s.setSoLuong(soLuong);
//						s.setGiaNhap(row.getCell(14).getNumericCellValue());
//						s.giaBan();
//						s.setGiaKM(row.getCell(16).getNumericCellValue());
//						boolean checkIDLoaiSanPham = daoLoaiSanPham
//								.checkIdLoaiSanPham(s.getIdLoaiSanPham().getIdLoaiSanPham());
//						boolean checkIDNhaCungCap = daoNhaCungCap
//								.checkIdNhaCungCap(s.getIdNhaCungCap().getIdNhaCungCap());
//						boolean checkIDTacGia = daoTacGia.checkIdTacGia(s.getTacGia().getIdTacGia());
//						boolean checkIDTheLoai = daoTheLoai.checkIdTheLoai(s.getTheLoai().getIdTheLoai());
//						if (!checkIDLoaiSanPham || !checkIDNhaCungCap || !checkIDTacGia || !checkIDTheLoai) {
//							JOptionPane.showMessageDialog(this,
//									"Không thể thêm sản phẩm: Một hoặc nhiều khóa phụ không tồn tại hoặc không hợp lệ.");
//							continue;
//						}
//						if (daoSach.checkIdSach(s.getIdSanPham())) {
//							daoSach.updateBook(s);
//							lamMoi();
//						} else {
//							boolean result = daoSach.themSach(s);
//							loadData();
//							lamMoi();
//							if (!result) {
//								System.out.println("Không thể thêm sản phẩm: " + s.getIdSanPham());
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

    private Double parseDoubleWithMultiplePoints(String input) {
        String cleanedInput = input.replaceAll("[^\\d.]", "");
        cleanedInput = cleanedInput.contains(".")
                ? cleanedInput.substring(0, cleanedInput.indexOf(".") + 1)
                + cleanedInput.substring(cleanedInput.indexOf(".") + 1).replace(".", "")
                : cleanedInput;
        return cleanedInput.isEmpty() ? 0.0 : Double.parseDouble(cleanedInput);
    }

    private void exportExcel(String filePath) {
        double tongGiaNhap = 0;
        int tongSoLuong = 0;
        double tongGiaBan = 0;
        int rowCount = model.getRowCount();
        java.util.List<Book> dsSach = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            String idSanPham = (String) model.getValueAt(i, 0);
            String tenSanPham = (String) model.getValueAt(i, 1);
            String tacGia = (String) model.getValueAt(i, 2);
            String theLoai = (String) model.getValueAt(i, 3);


            LocalDate date = (LocalDate) model.getValueAt(i, 4);
            String dateString = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(dateString, formatter);


            String isbn = (String) model.getValueAt(i, 5);
            int soTrang = (int) model.getValueAt(i, 6);
            String loaiSanPham = (String) model.getValueAt(i, 7);
            String nhaCungCap = (String) model.getValueAt(i, 8);
            Double kichThuoc = (Double) model.getValueAt(i, 9);
            String mauSac = (String) model.getValueAt(i, 10);
            String trangThaiStr = (String) model.getValueAt(i, 11);
            ProductStatusEnum trangThai = ProductStatusEnum.getByName(trangThaiStr);
            String thueStr = ((String) model.getValueAt(i, 12)).replaceAll("\\D+", "");
            Double thue = parseDoubleWithMultiplePoints(thueStr);
            int soLuong = (int) model.getValueAt(i, 13);
            String giaNhapStr = ((String) model.getValueAt(i, 14)).replaceAll("\\D+", "");
            Double giaNhap = parseDoubleWithMultiplePoints(giaNhapStr);
            Book s = new Book();
            s.setProductId(idSanPham);
            s.setProductName(tenSanPham);
            if (tacGia != null) {
                Author loaiTG = new Author();
                loaiTG.setAuthorName(tacGia);
                s.setAuthorId(loaiTG);
            }
            if (theLoai != null) {
                Category loaiTL = new Category();
                loaiTL.setCategoryName(theLoai);
                s.setCategoryId(loaiTL);
            }

            s.setPublicationYear(localDate);

            s.setISBN(isbn);

            s.setNumberOfPages(soTrang);
            if (loaiSanPham != null) {
                ProductType loaiSP = new ProductType();
                loaiSP.setProductTypeName(loaiSanPham);
                s.setProductTypeId(loaiSP);
            }

            if (nhaCungCap != null) {
                Supplier ncc = new Supplier();
                ncc.setSupplierName(nhaCungCap);
                s.setSupplierId(ncc);
            }
            s.setSize(kichThuoc);
            s.setColor(mauSac);
            s.setStatus(trangThai);
            s.setQuantity(soLuong);
            s.setImportPrice(giaNhap);
            dsSach.add(s);
        }
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh sách sách");
            Row headerRow = sheet.createRow(0);
            String[] columnNames = {"ID Sản phẩm", "Tên sản phẩm", "Tác giả", "Thể loại", "Năm xuất bản", "ISBN",
                    "Số trang", "Loại sản phẩm", "Nhà cung cấp", "Kích thước", "Màu sắc", "Trạng thái", "Thuế",
                    "Số lượng", "Giá nhập", "Giá bán"};

            for (int i = 0; i < columnNames.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnNames[i]);
            }
            int rowNumber = 1;
            for (Book s : dsSach) {
                Row row = sheet.createRow(rowNumber++);
                org.apache.poi.ss.usermodel.Cell idSanPhamCell = row.createCell(0);
                idSanPhamCell.setCellValue(s.getProductId());
                org.apache.poi.ss.usermodel.Cell tenSanPhamCell = row.createCell(1);
                tenSanPhamCell.setCellValue(s.getProductName());
                org.apache.poi.ss.usermodel.Cell tacGia = row.createCell(2);
                tacGia.setCellValue(s.getAuthorId() != null ? s.getAuthorId().getAuthorName() : "");
                org.apache.poi.ss.usermodel.Cell theLoai = row.createCell(3);
                theLoai.setCellValue(s.getCategoryId() != null ? s.getCategoryId().getCategoryName() : "");
                org.apache.poi.ss.usermodel.Cell namXuatBan = row.createCell(4);
                namXuatBan.setCellValue(s.getPublicationYear());
                org.apache.poi.ss.usermodel.Cell ISBN = row.createCell(5);
                ISBN.setCellValue(s.getISBN());
                org.apache.poi.ss.usermodel.Cell soTrang = row.createCell(6);
                soTrang.setCellValue(s.getNumberOfPages());
                org.apache.poi.ss.usermodel.Cell loaiSanPhamCell = row.createCell(7);
                loaiSanPhamCell
                        .setCellValue(s.getProductTypeId() != null ? s.getProductTypeId().getProductTypeName() : "");
                org.apache.poi.ss.usermodel.Cell nhaCungCapCell = row.createCell(8);
                nhaCungCapCell.setCellValue(s.getSupplierId() != null ? s.getSupplierId().getSupplierName() : "");
                org.apache.poi.ss.usermodel.Cell kichThuocCell = row.createCell(9);
                kichThuocCell.setCellValue(s.getSize());
                org.apache.poi.ss.usermodel.Cell mauSacCell = row.createCell(10);
                mauSacCell.setCellValue(s.getColor());
                org.apache.poi.ss.usermodel.Cell trangThaiCell = row.createCell(11);
                trangThaiCell.setCellValue(s.getStatus() != null ? s.getStatus().getDescription() : "");
                org.apache.poi.ss.usermodel.Cell thueCell = row.createCell(12);
                thueCell.setCellValue(s.tax());
                org.apache.poi.ss.usermodel.Cell soLuongCell = row.createCell(13);
                soLuongCell.setCellValue(s.getSize());
                org.apache.poi.ss.usermodel.Cell giaNhapCell = row.createCell(14);
                giaNhapCell.setCellValue(s.getImportPrice());
                org.apache.poi.ss.usermodel.Cell giaBanCell = row.createCell(15);
                giaBanCell.setCellValue(s.sellingPrice());
                tongGiaNhap += s.getImportPrice();
                tongSoLuong += s.getQuantity();
                tongGiaBan += s.sellingPrice();
            }
            for (int i = 0; i < columnNames.length; i++) {
                sheet.autoSizeColumn(i);
            }
            sheet.createRow(rowNumber);
            org.apache.poi.ss.usermodel.Cell summaryCell = sheet.getRow(rowNumber).createCell(0);
            summaryCell.setCellValue("Tổng cộng");

            org.apache.poi.ss.usermodel.Cell tongGiaNhapCell = sheet.getRow(rowNumber).createCell(14);
            tongGiaNhapCell.setCellValue(tongGiaNhap);

            org.apache.poi.ss.usermodel.Cell tongSoLuongCell = sheet.getRow(rowNumber).createCell(13);
            tongSoLuongCell.setCellValue(tongSoLuong);

            org.apache.poi.ss.usermodel.Cell tongGiaBanCell = sheet.getRow(rowNumber).createCell(15);
            tongGiaBanCell.setCellValue(tongGiaBan);
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
            DialogUtils.showSuccessMessage(this, "Data has been exported to Excel file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @SneakyThrows
    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        } else {
            Book book = getBookInfo();
            int confirmDialog = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Warning",
                    JOptionPane.YES_NO_OPTION);
            if (confirmDialog == JOptionPane.YES_OPTION) {
                model.removeRow(row);
                String productId = txtIdSanPham.getText();
                mainController.deleteBook(productId);
                mainController.decreaseNumberOfBooks(book.getAuthorId().getAuthorId());
                mainController.decreaseNumberOfBooks(book.getCategoryId().getIdCategory());
                JOptionPane.showMessageDialog(this, "Product deleted successfully.");
                refreshForm();
            }
        }
    }


    @SneakyThrows
    private void updateBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để cập nhật", "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            int confirmation = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật thông tin của sản phẩm này?", "Thông báo",
                    JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                if (validateFieldsAndShowErrors()) {
                    try {
                        Book book = getBookInfo();
                        String productId = txtIdSanPham.getText();
                        mainController.updateBook(book);
                        JOptionPane.showMessageDialog(this, "Cập nhật sách thành công!");
                        loadData();
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Vui lòng kiểm tra định dạng số", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }


    @SneakyThrows
    private Book getBookInfo() throws NumberFormatException {
        String idSanPham = txtIdSanPham.getText().trim();
        String tenSanPham = txtTenSanPham.getText().trim();
        String tacGia = (String) cbLoaiTacGia.getSelectedItem();
        String theLoai = (String) cbLoaiTheLoai.getSelectedItem();
        Date date = chooserXuatBan.getDate();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String ISBN = isbnField.getText().trim();
        int soTrang = Integer.parseInt(txtSoTrang.getText().trim());
        String tenLoaiSanPham = (String) cbLoaiSanPham.getSelectedItem();
        String tenNhaCungCap = (String) cbNhaCungCap.getSelectedItem();
        double kichThuoc = Double.parseDouble(txtKichThuoc.getText().trim());
        String mauSac = txtMauSac.getText().trim();
        boolean trangThaiValue = chkTinhTrangKinhDoanh.isSelected();
        ProductStatusEnum trangThai = trangThaiValue ? ProductStatusEnum.ACTIVE : ProductStatusEnum.INACTIVE;
        int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
        double giaNhap = Double.parseDouble(txtGiaNhap.getText().trim());


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

        Author author = null;
        for (Author item : mainController.getAllAuthor()) {
            if (item.getAuthorName().equals(tacGia)) {
                author = item;
                break;
            }
        }
        Category category = null;
        for (Category item : mainController.getAllCategory()) {
            if (item.getCategoryName().equals(theLoai)) {
                category = item;
                break;
            }
        }

        Book book = new Book();
        book.setProductId(idSanPham);
        book.setProductName(tenSanPham);
        book.setAuthorId(author);
        book.setCategoryId(category);
        book.setPublicationYear(localDate);
        book.setISBN(ISBN);
        book.setNumberOfPages(soTrang);
        book.setProductTypeId(productType);
        book.setSupplierId(supplier);
        book.setSize(kichThuoc);
        book.setColor(mauSac);
        book.setStatus(trangThai);
        book.setQuantity(soLuong);
        book.setImportPrice(giaNhap);

        return book;
    }

    private boolean validateFieldsAndShowErrors() {
        JTextField emptyTextField = getFirstEmptyTextField();


        if (!isValidName(txtTenSanPham.getText().trim())) {
            showErrorAndFocus(this, "Tên sản phẩm không hợp lệ. Chỉ cho phép các chữ cái và số không có ký tự đặc biệt", txtTenSanPham);
            return false;
        }

        if (!isValidISBN(isbnField.getText().trim())) {
            showErrorAndFocus(this, "Định dạng ISBN không hợp lệ", isbnField);
            return false;
        }

        if (!isValidInt(txtSoTrang.getText().trim())) {
            showErrorAndFocus(this, "Số trang không hợp lệ. Vui lòng nhập một số nguyên.", txtSoTrang);
            return false;
        }

        if (!isValidColor(txtMauSac.getText().trim())) {
            showErrorAndFocus(this, "Màu không hợp lệ. Chỉ cho phép chữ cái", txtMauSac);
            return false;
        }

        if (!isValidDouble(txtKichThuoc.getText().trim())) {
            showErrorAndFocus(this, "Định dạng kích thước không hợp lệ. Vui lòng nhập số thập phân.", txtKichThuoc);
            return false;
        }

        if (!isValidDouble(txtGiaNhap.getText().trim())) {
            showErrorAndFocus(this, "Định dạng giá nhập không hợp lệ. Vui lòng nhập số thập phân.", txtGiaNhap);
            return false;
        }

        if (!isValidInt(txtSoLuong.getText().trim())) {
            showErrorAndFocus(this, "Định dạng số lượng không hợp lệ. Vui lòng nhập một số nguyên.", txtSoLuong);
            return false;
        }

        if (!validateDateChooser(chooserXuatBan)) {
            showErrorDialog("Invalid publication date.");
            chooserXuatBan.requestFocus();
            return false;
        }
        if (emptyTextField != null) {
            showErrorAndFocus(this, "Vui lòng điền vào tất cả các lĩnh vực", emptyTextField);
            return false;
        }

        return true;
    }


    private void showErrorAndFocus(Component parentComponent, String message, JTextField textField) {
        JOptionPane.showMessageDialog(parentComponent, message, "Error", JOptionPane.ERROR_MESSAGE);
        textField.requestFocusInWindow();
        textField.selectAll();
    }

    private JTextField getFirstEmptyTextField() {
        if (txtTenSanPham.getText().trim().isEmpty()) {
            return txtTenSanPham;
        } else if (isbnField.getText().trim().isEmpty()) {
            return isbnField;
        } else if (txtSoTrang.getText().trim().isEmpty()) {
            return txtSoTrang;
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

    private boolean validateDateChooser(JDateChooser dateChooser) {
        Date selectedDate = dateChooser.getDate();
        if (selectedDate == null) {
            showErrorDialog("Vui lòng chọn năm xuất bản");
            return false;
        }

        Date currentDate = new Date();
        if (selectedDate.after(currentDate)) {
            showErrorDialog("Năm xuất bản không thể muộn hơn ngày hiện tại!");
            return false;
        }

        return true;
    }

    private boolean isValidName(String input) {
        return input.matches("^[\\p{L}0-9\\s]+$");
    }

    private boolean isValidColor(String input) {
        return input.matches("^[\\p{L}\\s]+$");
    }

    private boolean isValidISBN(String isbn) {
        return isbn.matches("^\\d{3}-\\d{3}-\\d{3}-\\d{3}-\\d{1}$");
    }

    private boolean isValidDouble(String input) {
        if (input.trim().isEmpty()) {
//            showErrorDialog("Value cannot be empty.");
            return false;
        }

        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            showErrorDialog("Định dạng giá trị không hợp lệ. Vui lòng nhập số thập phân hợp lệ.");
            return false;
        }
    }

    private boolean isValidInt(String input) {
        if (input.trim().isEmpty()) {
//            showErrorDialog("Value cannot be empty.");
            return false;
        }

        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            showErrorDialog("Định dạng giá trị không hợp lệ. Vui lòng nhập một số nguyên hợp lệ.");
            return false;
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }


    @SneakyThrows
    private void addBook() {
        String idSanPham = txtIdSanPham.getText();
        if (mainController.checkIdBook(idSanPham)) {
            JOptionPane.showMessageDialog(this, "ID sản phẩm trùng lặp. Vui lòng chọn ID khác.");
            return;
        }
        if (!validateFieldsAndShowErrors()) {
            return;
        }
        try {
            Book book = getBookInfo();
            if (book != null) {
                boolean check = mainController.addBook(book);
                if (check) {
                    DialogUtils.showSuccessMessage(this, "Thêm sách thành công");
                    mainController.increaseNumberOfBooks(book.getAuthorId().getAuthorId());
                    mainController.increaseNumberOfCategory(book.getCategoryId().getIdCategory());
                    loadData();
                    refreshForm();
                } else {
                    DialogUtils.showErrorMessage(this, "Thêm sách không thành công");
                }
            }

        } catch (NumberFormatException e) {
            DialogUtils.showErrorMessage(this, "Vui lòng nhập các giá trị số hợp lệ.");
        } catch (NullPointerException e) {
            DialogUtils.showErrorMessage(this, "Một số trường chứa giá trị null. Vui lòng kiểm tra đầu vào của bạn.");
        }
    }


    private void refreshForm() {
        txtTenSanPham.setText("");
        cbTacGiaSearch.setSelectedIndex(0);
        cbTheLoaiSearch.setSelectedIndex(0);
        cbLoaiSanPhamSearch.setSelectedIndex(0);
        cbNhaCungCapSearch.setSelectedIndex(0);

        if (cbLoaiTacGia.getItemCount() > 1) {
            cbLoaiTacGia.setSelectedIndex(1);
        }

        if (cbLoaiTheLoai.getItemCount() > 1) {
            cbLoaiTheLoai.setSelectedIndex(1);
        }

        chooserXuatBan.setDate(new Date());
        isbnField.setText("");
        txtSoTrang.setText("");

        if (cbLoaiSanPham.getItemCount() > 1) {
            cbLoaiSanPham.setSelectedIndex(1);
        }

        if (cbNhaCungCap.getItemCount() > 1) {
            cbNhaCungCap.setSelectedIndex(1);
        }

        txtKichThuoc.setText("");
        txtMauSac.setText("");
//        chkTinhTrangKinhDoanh.setSelected(trangThai.ac == trangThai.DANG_KINH_DOANH);
        txtSoLuong.setText("");
        txtGiaNhap.setText("");
        txtGiaBan.setText("");
        table.clearSelection();
        table.requestFocus();
        table.clearSelection();
        txtTenSanPham.requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Object o = e.getSource();
            if (o == txtTenSanPham) {
                addBook();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        SwingUtilities.invokeLater(() -> {
            Object o = e.getSource();
            if (o.equals(txtGiaNhap)) {
                calculateSellingPrice(txtGiaNhap, txtGiaBan);
            } else if (o.equals(txtTuKhoa)) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
                table.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter("(?i)" + txtTuKhoa.getText().trim(), 1, 4, 5));
            }
        });

    }

    private void calculateSellingPrice(JTextField txtGiaNhap2, JTextField txtGiaBan2) {
        try {
            double giaNhap = Double.parseDouble(txtGiaNhap2.getText());
            double thue = giaNhap * 0.05;
            double sellingPrice = giaNhap + (giaNhap * 0.55) + thue;
            txtGiaBan2.setText(df.format(sellingPrice));
        } catch (NumberFormatException ex) {
            txtGiaBan2.setText("Vui lòng nhập số hợp lệ.");
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtIdSanPham.setText(model.getValueAt(row, 0).toString());
            txtTenSanPham.setText(model.getValueAt(row, 1).toString());
            cbLoaiTacGia.setSelectedItem(model.getValueAt(row, 2).toString());
            cbLoaiTheLoai.setSelectedItem(model.getValueAt(row, 3).toString());
            String namXuatBanString = model.getValueAt(row, 4).toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date namXuatBan = null;
            try {
                namXuatBan = dateFormat.parse(namXuatBanString);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            chooserXuatBan.setDate(namXuatBan);
            isbnField.setText(model.getValueAt(row, 5).toString());
            txtSoTrang.setText(model.getValueAt(row, 6).toString());
            cbLoaiSanPham.setSelectedItem(model.getValueAt(row, 7).toString());
            cbNhaCungCap.setSelectedItem(model.getValueAt(row, 8).toString());
            txtKichThuoc.setText(model.getValueAt(row, 9).toString());
            txtMauSac.setText(model.getValueAt(row, 10).toString());

            String trangThaiValue = model.getValueAt(row, 11).toString();
            chkTinhTrangKinhDoanh.setSelected(trangThaiValue.equals("Đang kinh doanh"));

            String giaNhapStr = model.getValueAt(row, 14).toString().replaceAll("\\D+", "");
            String giaBanStr = model.getValueAt(row, 15).toString().replaceAll("\\D+", "");

            txtGiaNhap.setText(giaNhapStr);
            txtGiaBan.setText(giaBanStr);

            txtSoLuong.setText(model.getValueAt(row, 13).toString());
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
    public void itemStateChanged(ItemEvent e) {
        Object o = e.getSource();
        if (o == cbLoaiSanPhamSearch) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedLoaiSanPham = (String) cbLoaiSanPhamSearch.getSelectedItem();
                if (selectedLoaiSanPham.equals("Tất cả")) {
                    loadData();
                } else if (selectedLoaiSanPham != null && !selectedLoaiSanPham.isEmpty()) {
                    loadtableByProductType(selectedLoaiSanPham);
                } else {
                    loadData();
                }
            }
        } else if (o == cbNhaCungCapSearch) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedNhaCungCap = (String) cbNhaCungCapSearch.getSelectedItem();
                if (selectedNhaCungCap.equals("Tất cả")) {
                    loadData();
                } else if (selectedNhaCungCap != null && !selectedNhaCungCap.isEmpty()) {
                    loadtableBySupplier(selectedNhaCungCap);
                } else {
                    loadData();
                }
            }
        } else if (o == cbTheLoaiSearch) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedTheLoai = (String) cbTheLoaiSearch.getSelectedItem();
                if (selectedTheLoai.equals("Tất cả")) {
                    loadData();
                } else if (selectedTheLoai != null && !selectedTheLoai.isEmpty()) {
                    loadTableByCategory(selectedTheLoai);
                } else {
                    loadData();
                }
            }
        } else if (o == cbTacGiaSearch) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedTacGia = (String) cbTacGiaSearch.getSelectedItem();
                if (selectedTacGia.equals("Tất cả")) {
                    loadData();
                } else if (selectedTacGia != null && !selectedTacGia.isEmpty()) {
                    loadtableByAuthor(selectedTacGia);
                } else {
                    loadData();

                }
            }
        }
    }


    private void loadTableByCategory(String selectedTheLoai) {
        loadTableByCriteria("category", selectedTheLoai);
    }


    private void loadtableByAuthor(String selectedTacGia) {
        loadTableByCriteria("author", selectedTacGia);
    }

    private void loadtableBySupplier(String selectedNhaCungCap) {
        loadTableByCriteria("supplier", selectedNhaCungCap);
    }

    private void loadtableByProductType(String selectedLoaiSanPham) {
        loadTableByCriteria("productType", selectedLoaiSanPham);
    }

    private void loadTableByCriteria(String criteria, String selectedCriteria) {
        model.setRowCount(0);
        try {
            java.util.List<Book> listBook = null;
            if (criteria.equals("supplier")) {
                listBook = mainController.findSupplierByName(selectedCriteria);
            } else if (criteria.equals("productType")) {
                listBook = mainController.findBookByProductType(selectedCriteria);
            } else if (criteria.equals("category")) {
                listBook = mainController.findCategoryIdByName(selectedCriteria);
            } else if (criteria.equals("author")) {
                listBook = mainController.findBookByAuthor(selectedCriteria);
            }

            if (listBook.isEmpty()) {
                DialogUtils.showErrorMessage(this, "No product found for this " + criteria + ".");
                loadData();
                if (criteria.equals("supplier")) {
                    cbNhaCungCapSearch.setSelectedItem("Tất cả");
                } else if (criteria.equals("productType")) {
                    cbLoaiSanPhamSearch.setSelectedItem("Tất cả");
                } else if (criteria.equals("category")) {
                    cbTheLoaiSearch.setSelectedItem("Tất cả");
                } else if (criteria.equals("author")) {
                    cbTacGiaSearch.setSelectedItem("Tất cả");
                }
            } else {
                for (Book book : listBook) {
                    String idSanPham = book.getProductId();
                    String tenSanPham = book.getProductName();
                    String tenTacGia = book.getAuthorId().getAuthorName();
                    String tenTheLoai = book.getCategoryId().getCategoryName();
                    LocalDate namXuatBan = book.getPublicationYear();
                    String ISBN = book.getISBN();
                    int soTrang = book.getNumberOfPages();
                    String loaiSanPham = book.getProductTypeId().getProductTypeName();
                    String nhaCungCap = book.getSupplierId().getSupplierName();
                    double kichThuoc = book.getSize();
                    String mauSac = book.getColor();
                    String trangThai = book.getStatus().getDescription();
                    double thue = book.getImportPrice() * 0.05;
                    int soLuong = book.getQuantity();
                    double giaNhap = book.getImportPrice();
                    double giaBan = book.getImportPrice() + (book.getImportPrice() * 0.55) + thue;
                    model.addRow(new Object[]{idSanPham, tenSanPham, tenTacGia, tenTheLoai, namXuatBan, ISBN, soTrang,
                            loaiSanPham, nhaCungCap, kichThuoc, mauSac, trangThai, currencyFormat.format(thue), soLuong,
                            currencyFormat.format(giaNhap), currencyFormat.format(giaBan)});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
//	    updateISBN(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
//	    updateISBN(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // updateISBN(e);
    }

    private void updateISBN(DocumentEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int row = table.getSelectedRow();
            if (row != -1) {
                txtIdSanPham.setText(model.getValueAt(row, 0).toString());
                txtTenSanPham.setText(model.getValueAt(row, 1).toString());
                cbLoaiTacGia.setSelectedItem(model.getValueAt(row, 2).toString());
                cbLoaiTheLoai.setSelectedItem(model.getValueAt(row, 3).toString());
                String namXuatBanString = model.getValueAt(row, 4).toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date namXuatBan = null;
                try {
                    namXuatBan = dateFormat.parse(namXuatBanString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                chooserXuatBan.setDate(namXuatBan);
                isbnField.setText(model.getValueAt(row, 5).toString());
                txtSoTrang.setText(model.getValueAt(row, 6).toString());
                cbLoaiSanPham.setSelectedItem(model.getValueAt(row, 7).toString());
                cbNhaCungCap.setSelectedItem(model.getValueAt(row, 8).toString());
                txtKichThuoc.setText(model.getValueAt(row, 9).toString());
                txtMauSac.setText(model.getValueAt(row, 10).toString());

                String trangThaiValue = model.getValueAt(row, 11).toString();
                chkTinhTrangKinhDoanh.setSelected(trangThaiValue.equals("Đang kinh doanh"));

                String giaNhapStr = model.getValueAt(row, 14).toString().replaceAll("\\D+", "");
                String giaBanStr = model.getValueAt(row, 15).toString().replaceAll("\\D+", "");

                txtGiaNhap.setText(giaNhapStr);
                txtGiaBan.setText(giaBanStr);

                txtSoLuong.setText(model.getValueAt(row, 13).toString());
                txtGiaNhap.setText(model.getValueAt(row, 14).toString());
                txtGiaBan.setText(model.getValueAt(row, 15).toString());
            }
        }
    }
}
