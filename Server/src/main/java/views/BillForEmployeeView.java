package views;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import controller.MainController;
import controller.MainControllerInterface;
import lombok.SneakyThrows;
import models.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.DialogUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.Font;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;

public class BillForEmployeeView extends JPanel implements MouseListener, ItemListener, PropertyChangeListener, ActionListener, KeyListener {
    private JPanel pnMain;
    private JPanel pnLeft;
    private JPanel pnRight;
    private JPanel pnLeft_Bottom;
    private JLabel lblGetSearchBill;
    private JLabel lblGetSearchProduct;
    private JLabel lblManagerBill;
    private JLabel lblSumBill;
    private JLabel lblSumBillValue;
    private JLabel lbSearchByDate;
    private JButton btnExportExcel;
    private JButton btnRefresh;
    private JDateChooser dateChooserEnd;
    private JDateChooser dateChooserStart;
    private JTextField txtGetSearchBill;
    private JTextField getTxtGetSearchProduct;
    private JCheckBox checkBoxSearchByDate;


    private JComboBox<Object> cboSearchBillDate;
    private JDateChooser dateChooser;

    private JTextArea txtAreaBillDetail;
    private SimpleDateFormat dfNgaySQL;
    private JTable table;
    private DefaultTableModel tableModel, tableModel1;
    private JButton btnSearchByDate;
    private JLabel lblTxtMaHoaDon;
    private JLabel lblTxtThoiGian;
    private JLabel lblTxtNhanVien;
    private JLabel lblTxtKhachHang;
    private JLabel lblTxtGiamGia;
    private JLabel lblTxtKhachDaDua;
    private JLabel lbTxtTienTraKhach;
    private JLabel lblTxtTongTien;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private JButton btnExportBillExcel;
    private static MainControllerInterface controller;

    static {
        try {
            controller = new MainController();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private Employee employee;

    public BillForEmployeeView(Employee e, MainControllerInterface controller) {
        setLayout(null);
        this.employee = e;
        this.controller = controller;
        init();

    }

    @SneakyThrows
    public void init() {
        dfNgaySQL = new SimpleDateFormat("yyyy-MM-dd");
        controller = new MainController();
        currencyFormat.setCurrency(Currency.getInstance("VND"));
        pnLeft = new JPanel();
        pnLeft.setLayout(null);
        checkBoxSearchByDate = new JCheckBox();
        JLabel lbFrom = new JLabel("Từ:");
        JLabel lbTo = new JLabel("Đến:");
        btnSearchByDate = new JButton("Lọc");
        table = new JTable();
        tableModel = new DefaultTableModel();
        pnMain = new JPanel();
        btnRefresh = new JButton();
        txtAreaBillDetail = new JTextArea();
        pnRight = new JPanel();
        btnExportBillExcel = new JButton();
        lblSumBill = new JLabel();
        lblSumBillValue = new JLabel();
        pnLeft.setBorder(BorderFactory.createTitledBorder("Tim kiem"));
        pnLeft.setBounds(10, 10, 230, 200);
        lblGetSearchBill = new JLabel("Mã hóa đơn");
        lblGetSearchBill.setBounds(10, 30, 150, 30);
        txtGetSearchBill = new JTextField();
        txtGetSearchBill.setBounds(10, 60, 200, 30);
        lblGetSearchProduct = new JLabel("Mã/Tên khách hàng");
        lblGetSearchProduct.setBounds(10, 100, 200, 30);
        getTxtGetSearchProduct = new JTextField(20);
        getTxtGetSearchProduct.setBounds(10, 130, 200, 30);
        pnLeft.add(lblGetSearchBill);
        pnLeft.add(txtGetSearchBill);
        pnLeft.add(lblGetSearchProduct);
        pnLeft.add(getTxtGetSearchProduct);

        pnLeft_Bottom = new JPanel();
        pnLeft_Bottom.setLayout(null);
        pnLeft_Bottom.setBounds(10, 220, 230, 200);
        pnLeft_Bottom.setBorder(BorderFactory.createTitledBorder("Tim kiem theo ngay"));

        checkBoxSearchByDate = new JCheckBox();
        checkBoxSearchByDate.setBounds(10, 20, 20, 30);
        lbSearchByDate = new JLabel("Lọc theo thời gian");
        lbSearchByDate.setBounds(50, 20, 150, 30);
        lbFrom.setBounds(10, 70, 50, 30);
        lbTo.setBounds(10, 120, 50, 30);
        btnSearchByDate.setBounds(10, 160, 100, 30);
        dateChooserStart = new JDateChooser();
        dateChooserEnd = new JDateChooser();
        dateChooserStart.setBounds(50, 70, 170, 30);
        dateChooserStart.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dateChooserStart.setDateFormatString("dd/MM/yyyy");
        dateChooserStart.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
        dateChooserStart.setFont(new Font("SansSerif", Font.PLAIN, 15));
        dateChooserStart.getCalendarButton().setPreferredSize(new Dimension(30, 24));
        dateChooserStart.getCalendarButton().setBackground(new Color(102, 0, 153));
        dateChooserStart.setDate(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        dateChooserEnd.setDate(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        dateChooserEnd.setBounds(50, 120, 170, 30);
        dateChooserEnd.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dateChooserEnd.setDateFormatString("dd/MM/yyyy");
        dateChooserEnd.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
        dateChooserEnd.setFont(new Font("SansSerif", Font.PLAIN, 15));
        dateChooserEnd.getCalendarButton().setPreferredSize(new Dimension(30, 24));
        dateChooserEnd.getCalendarButton().setBackground(new Color(102, 0, 153));
        dateChooserEnd.setDate(Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        dateChooserEnd.setEnabled(false);
        dateChooserStart.setEnabled(false);
        btnSearchByDate.setEnabled(false);
        checkBoxSearchByDate.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Nếu JCheckBox được chọn, kích hoạt JDateChooser
                    dateChooserEnd.setEnabled(true);
                    dateChooserStart.setEnabled(true);
                    btnSearchByDate.setEnabled(true);
                } else {
                    // Nếu JCheckBox không được chọn, làm mờ JDateChooser
                    dateChooserEnd.setEnabled(false);
                    dateChooserStart.setEnabled(false);
                    btnSearchByDate.setEnabled(false);
                }
            }
        });

        pnMain = new JPanel(null);
        pnMain.setBounds(250, 10, 620, 900);
        pnMain.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn"));
        lblManagerBill = new JLabel("Hóa đơn");
        lblManagerBill.setFont(new Font("Arial", Font.BOLD, 35));
        lblManagerBill.setBounds(40, 70, 200, 30);
        btnExportExcel = new JButton("Luư trữ");
        btnExportExcel.setBounds(460, 70, 100, 20);
        btnRefresh = new JButton("Làm mới");
        btnRefresh.setBounds(350, 70, 100, 20);
        lblSumBill = new JLabel("Tổng số hóa đơn:");
        lblSumBill.setBounds(800, 865, 150, 30);
        lblSumBillValue = new JLabel();
        lblSumBillValue.setBounds(950, 865, 50, 30);
        lblSumBillValue.setText(String.valueOf(calculateTotalBill()));

        pnMain.add(lblManagerBill);
        pnMain.add(btnRefresh);
        pnMain.add(btnExportExcel);
        pnMain.add(lblSumBill);
        pnMain.add(lblSumBillValue);


        tableModel.addColumn("Mã hóa đơn");
        tableModel.addColumn("Thời gian");
        tableModel.addColumn("Nhân viên");
        tableModel.addColumn("Khách hàng");
        tableModel.addColumn("Khách đã đưa");
        tableModel.addColumn("Tổng tiền hàng");

        table.setModel(tableModel);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(10, 110, 600, 760);
        pnMain.add(sp);
//        calculateTotalPayment();


        pnLeft_Bottom.add(checkBoxSearchByDate);
        pnLeft_Bottom.add(dateChooserStart);
        pnLeft_Bottom.add(lbSearchByDate);
        pnLeft_Bottom.add(dateChooserEnd);
        pnLeft_Bottom.add(btnSearchByDate);
        pnLeft_Bottom.add(lbFrom);
        pnLeft_Bottom.add(lbTo);


        pnRight = new JPanel();
        pnRight.setBounds(870, 10, 420, 900);
        pnRight.setBorder(BorderFactory.createTitledBorder("Chi tiết hóa đơn"));
        pnRight.setLayout(null);

        JLabel lblMaHoaDon = new JLabel("Mã hóa đơn:");
        JLabel lblThoiGian = new JLabel("Thời gian:");
        JLabel lblNhanVien = new JLabel("Nhân viên:");
        JLabel lblKhachHang = new JLabel("Khách hàng:");
        JLabel lblKhachDaDua = new JLabel("Tiền khách đưa:");
        JLabel lblTienTraKhach = new JLabel("Tiền trả lại khách:");
        JLabel lblTongTien = new JLabel("TỔNG TIỀN (bao gồm thuế VAT 10%):");
        lblTongTien.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblTongTien.setForeground(Color.RED);
        lblTxtMaHoaDon = new JLabel();
        lblTxtThoiGian = new JLabel();
        lblTxtNhanVien = new JLabel();
        lblTxtKhachHang = new JLabel();
        lblTxtGiamGia = new JLabel();
        lblTxtKhachDaDua = new JLabel();
        lblTxtTongTien = new JLabel();
        lbTxtTienTraKhach = new JLabel();
        lblTxtTongTien.setFont(new Font("SansSerif", Font.BOLD, 15));

        JTable table1 = new JTable();
        tableModel1 = new DefaultTableModel();

        tableModel1.addColumn("Mã sản phẩm");
        tableModel1.addColumn("Tên sản phẩm");
        tableModel1.addColumn("Số lượng");
        tableModel1.addColumn("Đơn giá");
        tableModel1.addColumn("Thành tiền");
        table1.setModel(tableModel1);
        JScrollPane cthd = new JScrollPane(table1);
        cthd.setBounds(10, 240, 405, 450);

        btnExportBillExcel = new JButton("In hóa đơn");
        btnExportBillExcel.setBounds(200, 700, 150, 30);
        int y = 30;
        int verticalGap = 10;
//        txtAreaBillDetail.setBounds(10, y, 369, 160); // Đ?ặt vị trí và kích thước cho JTextArea

        lblMaHoaDon.setBounds(10, y, 369, 20);
        lblTxtMaHoaDon.setBounds(190, y, 369, 20);
        y += 20 + verticalGap;
        lblThoiGian.setBounds(10, y, 369, 20);
        lblTxtThoiGian.setBounds(190, y, 369, 20);
        y += 20 + verticalGap;
        lblNhanVien.setBounds(10, y, 369, 20);
        lblTxtNhanVien.setBounds(190, y, 369, 20);
        y += 20 + verticalGap;
        lblKhachHang.setBounds(10, y, 369, 20);
        lblTxtKhachHang.setBounds(190, y, 369, 20);
        y += 20 + verticalGap;
        lblKhachDaDua.setBounds(10, y, 369, 20);
        lblTxtKhachDaDua.setBounds(190, y, 369, 20);
        y += 20 + verticalGap;
        lblTienTraKhach.setBounds(10, y, 369, 20);
        lbTxtTienTraKhach.setBounds(190, y, 369, 20);
        y += 20 + verticalGap;
        lblTongTien.setBounds(10, y, 369, 20);
        lblTxtTongTien.setBounds(290, y, 369, 20);
        pnRight.add(lblTongTien);
        pnRight.add(lblMaHoaDon);
        pnRight.add(lblThoiGian);
        pnRight.add(lblNhanVien);
        pnRight.add(lblKhachHang);
        pnRight.add(lblKhachDaDua);
        pnRight.add(lblTienTraKhach);
        pnRight.add(lbTxtTienTraKhach);
        pnRight.add(lblTxtMaHoaDon);
        pnRight.add(lblTxtThoiGian);
        pnRight.add(lblTxtNhanVien);
        pnRight.add(lblTxtKhachHang);
        pnRight.add(lblTxtGiamGia);
        pnRight.add(lblTxtKhachDaDua);
        pnRight.add(lblTxtTongTien);

        pnRight.add(btnExportBillExcel);

        pnRight.add(txtAreaBillDetail);
        pnRight.add(cthd);

        add(pnLeft_Bottom);
        add(pnLeft);
        add(pnMain);
        add(pnRight);

        loadData();
        table.addMouseListener(this);
        checkBoxSearchByDate.addItemListener(this);
        btnRefresh.addActionListener(this);
        btnExportExcel.addActionListener(this);
        btnExportBillExcel.addActionListener(this);
        txtGetSearchBill.addKeyListener(this);
        getTxtGetSearchProduct.addKeyListener(this);
        btnSearchByDate.addActionListener(this);
    }

    @SneakyThrows
    public int calculateTotalBill() {

        return controller.sumTotalBill();
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
            String idHD = lblTxtMaHoaDon.getText();
            Bill bill = controller.getBillById(idHD);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateTimeString = now.format(formatter);
            // Writing content
            String filePath2 = System.getProperty("user.dir") + "/src/main/resources/database/vuArial.ttf";
            BaseFont bf = BaseFont.createFont(filePath2, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Paragraph tieuDe = new Paragraph("HÓA ĐƠN", new com.itextpdf.text.Font(bf, 30, 1, BaseColor.BLUE));
            Paragraph tenKH = new Paragraph("Tên khách hàng : " + bill.getCustomer().getName(), new com.itextpdf.text.Font(bf, 15));
            Paragraph idKH2 = new Paragraph("ID khách hàng   : " + bill.getCustomer().getIdCustomer(), new com.itextpdf.text.Font(bf, 15));
            Paragraph idHD2 = new Paragraph("ID hóa đơn      : " + lblTxtMaHoaDon.getText().toString(), new com.itextpdf.text.Font(bf, 15, Font.BOLD));
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
            document.add(new Paragraph("SDT khách hàng: " + bill.getCustomer().getPhone(), new com.itextpdf.text.Font(bf, 15)));
            document.add(new Paragraph("Tên nhân viên    : " + lblTxtNhanVien.getText(), new com.itextpdf.text.Font(bf, 15)));
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


            for (DetailsBill cthd : controller.findDetailsBillByBillId(idHD)) {
                String idSP = cthd.getProduct().getProductId();
                ProductSale productSale = controller.getProductSale(idSP);
                Product product = controller.getProductById(idSP);
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

            Paragraph tongTien = new Paragraph("TỔNG TIỀN : " + lblTxtTongTien.getText(), new com.itextpdf.text.Font(bf, 25, 1, BaseColor.RED));
            Paragraph tienKhachDua = new Paragraph("Tiền khách đưa  : " + lblTxtKhachDaDua.getText(), new com.itextpdf.text.Font(bf, 20));
            Paragraph tienTraKhach = new Paragraph("Tiền trả khách    : ", new com.itextpdf.text.Font(bf, 20));
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

    @SneakyThrows
    public void loadData() {
        // Load data from database
        tableModel.setRowCount(0);
        controller.findBillByEmployee(employee.getIdEmployee()).forEach(bill -> {
            Object[] rowData = new Object[]{
                    bill.getBillId(), bill.getBillDate(), bill.getEmployee().getName(), bill.getCustomer().getName(), currencyFormat.format(bill.getAmountReceived()), currencyFormat.format(bill.getAmounttotal())
            };
            tableModel.addRow(rowData);
        });
    }

    @SneakyThrows
    public void loadDataByDate(LocalDate dateFrom, LocalDate dateTo) {
        // Load data from database
        tableModel.setRowCount(0);
        controller.findBillByDate(dateFrom, dateTo).forEach(bill -> {
            Object[] rowData = new Object[]{
                    bill.getBillId(), bill.getBillDate(), bill.getEmployee().getName(), bill.getCustomer().getName(), currencyFormat.format(bill.getAmountReceived()), currencyFormat.format(bill.getAmounttotal())
            };
            tableModel.addRow(rowData);
        });
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnExportExcel) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = System.getProperty("user.dir") + "/src/main/resources/DataExports/Bill/B_" + timeStamp
                    + ".xlsx";
            exportExcel(filePath);
        } else if (e.getSource() == btnRefresh) {
            refreshForm();
        } else if (e.getSource() == btnExportBillExcel) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String selectedFilePath = selectedFile.getAbsolutePath();
                if (!selectedFilePath.toLowerCase().endsWith(".pdf")) {
                    selectedFilePath += ".pdf";
                }
                try {
                    exportToPdf(selectedFilePath);
                } catch (DocumentException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (e.getSource() == btnSearchByDate) {
            Date dateFrom = dateChooserStart.getDate();
            Date dateTo = dateChooserEnd.getDate();
            if (dateFrom.after(dateTo)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không được trước ngày bắt đầu");
                dateChooserStart.setDate(new Date());
                dateChooserEnd.setDate(new Date());
            } else {
                LocalDate localDateFrom = dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate localDateTo = dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                loadDataByDate(localDateFrom, localDateTo);
            }

        }
    }

    private void exportExcel(String filePath) {
        FileOutputStream fileOutputStream = null;
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Bills");
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(tableModel.getColumnName(i));
            }

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    Cell cell = row.createCell(j);
                    Object value = tableModel.getValueAt(i, j);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    } else {
                        cell.setCellValue("");
                    }
                }
            }

            fileOutputStream = new FileOutputStream(new File(filePath));
            workbook.write(fileOutputStream);
            DialogUtils.showSuccessMessage(this, "Export excel successfully");
        } catch (IOException e) {
            e.printStackTrace();
            DialogUtils.showErrorMessage(this, "Export excel failed");
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void refreshForm() {
        loadData();
        txtGetSearchBill.setText("");
        getTxtGetSearchProduct.setText("");
        txtGetSearchBill.requestFocus();
        lblTxtMaHoaDon.setText("");
        lblTxtThoiGian.setText("");
        lblTxtNhanVien.setText("");
        lblTxtKhachHang.setText("");
        lblTxtKhachDaDua.setText("");
        lblTxtTongTien.setText("");
        lbTxtTienTraKhach.setText("");
        tableModel1.setRowCount(0);
        table.clearSelection();
        dateChooserEnd.setDate(new Date());
        dateChooserStart.setDate(new Date());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    private void searchByBill() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        table.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("(?i)" + txtGetSearchBill.getText().trim(), 0, 2, 3));
    }

    private void searchByProduct() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        table.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter("(?i)" + getTxtGetSearchProduct.getText().trim(), 3));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        SwingUtilities.invokeLater(() -> {
            Object o = e.getSource();
            if (o.equals(txtGetSearchBill)) {
                searchByBill();
            } else if (o.equals(getTxtGetSearchProduct)) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tableModel.setRowCount(0);
                    try {
                        controller.findBillByCustomerSDT(getTxtGetSearchProduct.getText().trim()).forEach(
                                bill -> {
                                    Object[] rowData = new Object[]{
                                            bill[0],
                                            bill[1],
                                            bill[2],
                                            bill[3],
                                            bill[4],
                                            bill[5],
                                            bill[6]
                                    };
                                    tableModel.addRow(rowData);
                                }
                        );
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    searchByProduct();
                }
            }
        });
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @SneakyThrows
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        lblTxtMaHoaDon.setText(tableModel.getValueAt(row, 0).toString());
        lblTxtThoiGian.setText(tableModel.getValueAt(row, 1).toString());
        lblTxtNhanVien.setText(tableModel.getValueAt(row, 2).toString());
        lblTxtKhachHang.setText(tableModel.getValueAt(row, 3).toString());
        lblTxtKhachDaDua.setText(tableModel.getValueAt(row, 4).toString());
        lblTxtTongTien.setText(tableModel.getValueAt(row, 5).toString());
        double tienKhachDua = Double.parseDouble(tableModel.getValueAt(row, 4).toString().trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
        double tienHoaDon = Double.parseDouble(tableModel.getValueAt(row, 5).toString().trim().replace("\u00A0", "").replaceAll("[.,₫]", ""));
        double tienTraKhach = tienKhachDua - tienHoaDon;
        lbTxtTienTraKhach.setText(currencyFormat.format(tienTraKhach));
        tableModel1.setRowCount(0);
        for (DetailsBill detailsBill : controller.findDetailsBillByBillId(lblTxtMaHoaDon.getText())) {
            tableModel1.addRow(new Object[]{
                    detailsBill.getBill().getBillId(), detailsBill.getProduct().getProductName(), detailsBill.getQuantity(), currencyFormat.format(detailsBill.getPrice()), currencyFormat.format(detailsBill.getTotal())
            });
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
