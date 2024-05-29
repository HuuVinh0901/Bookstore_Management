package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;


import controller.MainController;
import controller.MainControllerInterface;
import dao.AuthorDao;
import dao.impl.AuthorDaoImpl;
import lombok.SneakyThrows;
import models.Author;
import util.GeneratorIDAuto;

public class AuthorView extends JPanel implements ActionListener, MouseListener, KeyListener {
    private JPanel pnMain;
    private JPanel pnHeading;
    private JPanel pnThongTinMain;
    private JPanel pnThongTin;
    private JPanel pnChucNangThongTin;
    private JPanel pnChucNang;
    private JPanel pnTimKiem;
    private JPanel pnMainCenter;
    private JPanel pnTable;

    private JLabel lblIdTacGia;
    private JLabel lblTenTacGia;
    private JLabel lblNgaySinh;
    private JLabel lblTitle;
    private JLabel lblSoLuongTacPham;
    private JLabel lblTuKhoa;

    private JTextField txtIdTacGia;
    private JTextField txtTenTacGia;
    private JTextField txtSoLuongTacPham;
    private SimpleDateFormat dfNgaySinh;
    private JDateChooser chooserNgaySinh;
    private JTextField txtTuKhoa;

    private JButton btnThem;
    private JButton btnCapNhat;
    private JButton btnXoa;
    private JButton btnLamMoi;
    private JButton btnXemTatCa;

    private JTable table;
    private DefaultTableModel model;

    // boc cac text
    private JPanel pnIdTacGia;
    private JPanel pnTenTacGia;
    private JPanel pnNgaySinh;
    private JPanel pnSoLuong;

    private AuthorDao authorDao;
    private Date date;
    private boolean selectingRow = false;
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

//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//                AuthorView frame = new AuthorView(mainController);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setSize(800, 600);
//                frame.setLocationRelativeTo(null);
//                frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

    @SneakyThrows
    public AuthorView(MainControllerInterface mainController) {
        setLayout(new BorderLayout(8, 6));
        autoID = new GeneratorIDAuto();
        this.mainController = mainController;
        init();

    }


    private void init() {
        dfNgaySinh = new SimpleDateFormat("dd/MM/yyyy");
        pnMain = new JPanel(new BorderLayout());

        pnHeading = new JPanel();
        lblTitle = new JLabel("Quản Lý Tác Giả");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTitle.setForeground(new Color(26, 102, 227));
        pnHeading.add(lblTitle);

        pnThongTinMain = new JPanel(new BorderLayout());
        pnThongTinMain.setBorder(BorderFactory.createTitledBorder("Thông tin tác giả"));
        pnThongTin = new JPanel(new GridLayout(2, 4, 20, 20));

        lblIdTacGia = new JLabel("Mã tác giả:");
        lblTenTacGia = new JLabel("Tên tác giả:");
        lblSoLuongTacPham = new JLabel("Số lượng tác phẩm:");
        lblNgaySinh = new JLabel("Ngày sinh:");

        txtIdTacGia = new JTextField();
        txtIdTacGia.setEditable(false);
        txtTenTacGia = new JTextField();
        txtSoLuongTacPham = new JTextField();
        txtSoLuongTacPham.setEditable(false);
        chooserNgaySinh = new JDateChooser();
        chooserNgaySinh.setPreferredSize(new Dimension(200, 22));
        chooserNgaySinh.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chooserNgaySinh.setDateFormatString("dd/MM/yyyy");
        chooserNgaySinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
        chooserNgaySinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
        chooserNgaySinh.getCalendarButton().setPreferredSize(new Dimension(30, 24));
        chooserNgaySinh.getCalendarButton().setBackground(new Color(102, 0, 153));
        chooserNgaySinh.getCalendarButton().setToolTipText("Chọn ngày sinh");
        date = new Date();
        chooserNgaySinh.setDate(date);

        Insets labelInsets = new Insets(0, 70, 0, 10);
        lblIdTacGia.setBorder(new EmptyBorder(labelInsets));
        lblTenTacGia.setBorder(new EmptyBorder(labelInsets));
        lblSoLuongTacPham.setBorder(new EmptyBorder(labelInsets));
        lblNgaySinh.setBorder(new EmptyBorder(labelInsets));

        pnThongTin.add(lblIdTacGia);
        pnThongTin.add(txtIdTacGia);
        pnThongTin.add(lblTenTacGia);
        pnThongTin.add(txtTenTacGia);
        pnThongTin.add(lblSoLuongTacPham);
        pnThongTin.add(txtSoLuongTacPham);
        pnThongTin.add(lblNgaySinh);
        pnThongTin.add(chooserNgaySinh);

        pnChucNangThongTin = new JPanel(new BorderLayout());
        pnChucNang = new JPanel(new FlowLayout(5));
        btnCapNhat = new JButton("Cập nhật");
        btnLamMoi = new JButton("Làm mới");
        btnThem = new JButton("Thêm");
        btnXemTatCa = new JButton("Xem tất cả");
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
        Insets btnInsert = new Insets(0, 70, 0, 0);
        pnChucNang.setBorder(new EmptyBorder(btnInsert));
        pnChucNangThongTin.add(pnChucNang);

        pnThongTinMain.add(pnThongTin, BorderLayout.NORTH);
        pnThongTinMain.add(pnChucNangThongTin, BorderLayout.SOUTH);

        pnMainCenter = new JPanel(new BorderLayout());
        pnTimKiem = new JPanel(new FlowLayout(5));
        lblTuKhoa = new JLabel("Từ khóa:");
        txtTuKhoa = new JTextField(20);
        btnXemTatCa = new JButton("Xem tất cả");
        pnTimKiem.add(lblTuKhoa);
        pnTimKiem.add(txtTuKhoa);
        pnTimKiem.add(btnXemTatCa);

        pnTable = new JPanel(new BorderLayout());
        pnTable.setBorder(BorderFactory.createTitledBorder("Danh mục"));
        model = new DefaultTableModel();
        table = new JTable();
        model.addColumn("Mã tác giả");
        model.addColumn("Tên tác giả");
        model.addColumn("Ngày sinh");
        model.addColumn("Số lượng tác phẩm");
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        pnTable.add(scrollPane);

        pnMainCenter.add(pnTimKiem, BorderLayout.NORTH);
        pnMainCenter.add(pnTable, BorderLayout.SOUTH);

        pnMain.add(pnHeading, BorderLayout.NORTH);
        pnMain.add(pnThongTinMain, BorderLayout.CENTER);
        pnMain.add(pnMainCenter, BorderLayout.SOUTH);
        add(pnMain);

        loadData();


        btnThem.addActionListener(this);
        btnCapNhat.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnXemTatCa.addActionListener(this);
//		btnXoa.addActionListener(this);
        table.addMouseListener(this);
        txtTuKhoa.addKeyListener(this);
        txtTenTacGia.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                int row = table.getSelectedRow();
                if (row == -1)
                    txtIdTacGia.setText(autoID.autoID("TG"));
                else
                    txtIdTacGia.setText(model.getValueAt(row, 0).toString());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    @SneakyThrows
    private void loadData() {
        model.setRowCount(0);
        java.util.List<Author> authors = mainController.getAllAuthor();

        if (authors != null) {
            for (Author author : authors) {
                Object[] rowData = {author.getAuthorId(), author.getAuthorName(), author.getDate(), author.getNumberOfWorks()};
                model.addRow(rowData);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Load data failed");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThem)) {
            addAuthor();
        } else if (o.equals(btnLamMoi)) {
            lamMoi();
        } else if (o.equals(btnCapNhat)) {
            updateAuhtor();
        } else if (o.equals(btnXoa)) {
            deleteAuthor();
        } else if (o.equals(btnXemTatCa)) {
            lamMoi();
            loadData();
        }

    }

    @SneakyThrows
    private void addAuthor() {
        String authorId = txtIdTacGia.getText();
        String authorName = txtTenTacGia.getText();
        LocalDate date = chooserNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (authorId.isEmpty() || authorName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin tác giả.");
            return;
        }

        Author author = new Author(authorId, authorName, date);
        boolean success = mainController.addAuthor(author);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm tác giả thành công");
            loadData();
            lamMoi();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm tác giả không thành công");
        }
    }


    private void deleteAuthor() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để xóa");
        } else {
            try {
                int confirmDialogResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Cảnh báo",
                        JOptionPane.YES_NO_OPTION);
                if (confirmDialogResult == JOptionPane.YES_OPTION) {
                    String authorId = txtIdTacGia.getText();
                    boolean deletionResult = mainController.deleteAuthor(authorId);
                    if (deletionResult) {
                        JOptionPane.showMessageDialog(this, "Xóa thông tin thành công");
                        loadData();
                        lamMoi();
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa thông tin");
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Không thể xóa thông tin");
            }
        }
    }


    @SneakyThrows
    private void updateAuhtor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để cập nhật", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String authorId = txtIdTacGia.getText().trim();
        String authorName = txtTenTacGia.getText().trim();
        LocalDate dateOfBirth = chooserNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int numberOfWorks = 0;

        Author author = new Author(authorId, authorName, dateOfBirth);
        boolean success = mainController.updateAuthor(author);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thông tin tác giả được cập nhật thành công");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Không thể cập nhật thông tin tác giả", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void lamMoi() {
        txtTenTacGia.setText("");
        txtSoLuongTacPham.setText("");
        chooserNgaySinh.setDate(new Date());
        txtTuKhoa.setText("");
        table.clearSelection();

    }

    private boolean validateDateChooser(JDateChooser dateChooser) {
        Date selectedDate = dateChooser.getDate();
        if (selectedDate == null) {
            showErrorDialog("Vui lòng chọn ngày sinh!");
            return false;
        }
        Date currentDate = new Date();
        if (selectedDate.after(currentDate)) {
            showErrorDialog("Ngày sinh không được lớn hơn ngày hiện tại!");
            return false;
        }

        java.sql.Date ngaySinh = new java.sql.Date(selectedDate.getTime());
        return true;
    }

    private boolean validataFields() {
        return validataField(txtTenTacGia, "^[\\p{L}\\s]+$",
                "Tên tác giả không hợp lệ. Phải bắt đầu bằng chữ cái, không chấp nhận ký tự đặc biệt.")
                && validateDateChooser(chooserNgaySinh);
    }

    private boolean validataField(JTextField textField, String regex, String errorMessage) {
        String fieldValue = textField.getText().trim();
        if (fieldValue.isEmpty()) {
            showErrorDialog("Vui lòng nhập giá trị cho " + textField.getName() + "!");
            textField.requestFocus();
            return false;
        }

        if (!fieldValue.matches(regex)) {
            showErrorDialog(errorMessage);
            textField.requestFocus();
            textField.selectAll();
            return false;
        }
        return true;
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Cảnh Báo", JOptionPane.WARNING_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectingRow = true;
            String authorId = model.getValueAt(selectedRow, 0).toString();
            String authorName = model.getValueAt(selectedRow, 1).toString();
            String dateOfBirthString = model.getValueAt(selectedRow, 2).toString();
            String numberOfWorks = model.getValueAt(selectedRow, 3).toString();
            txtIdTacGia.setText(authorId);
            txtTenTacGia.setText(authorName);
            txtSoLuongTacPham.setText(numberOfWorks);

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = dateFormat.parse(dateOfBirthString);
                chooserNgaySinh.setDate(dateOfBirth);
            } catch (ParseException ex) {
                ex.printStackTrace();
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
        SwingUtilities.invokeLater(() -> {
            Object o = e.getSource();
            if (o.equals(txtTuKhoa)) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
                table.setRowSorter(tr);
                tr.setRowFilter(RowFilter.regexFilter("(?i)" + txtTuKhoa.getText().trim(), 0, 1, 2, 3));
            }
        });
    }

}
