package views;

import com.toedter.calendar.JDateChooser;
import controller.MainController;
import controller.MainControllerInterface;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import util.DialogUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class ManagerHomeStatistics extends JPanel implements ItemListener, ActionListener, MouseListener {
    private JPanel pnTop, pnTop_1, pnTop_2;
    private JPanel pnCenter;
    private JPanel pnBottom;
    private JLabel lblFilter;
    private JLabel lblOverview;
    private JDateChooser dcFrom;
    private JDateChooser dcTo;
    private JComboBox cbFilter;

    private static MainControllerInterface mainController;

    static {
        try {
            mainController = new MainController();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private JButton btnRefresh;
    private JTable table, table1;
    private DefaultTableModel model, model1;
    private JPanel pnCenter_2;
    private JPanel pnCenter_3;
    private JLabel lblDonHangValue;
    private JLabel lblDoanhThu, lblDoanhThuValue, lblGiaTriDonHangValue, lblThucThuValue;
    private JButton btntimKiem;
    private DefaultCategoryDataset barDataset;
    private ChartPanel pieDataset;
    private JPanel pnCenter_4;
    private JPanel pnCenter_5;
    private NumberFormat currencyFormat;
    private JButton btnLuu;
    private ChartPanel chartPanel, panel, panel1;
    private List<Object[]> productData, employeeData, valueByProduct;

    public ManagerHomeStatistics(MainControllerInterface mainController) {
        this.mainController = mainController;
        initComponents();
    }

    @SneakyThrows
    private void initComponents() {

        mainController = new MainController();
        this.barDataset = new DefaultCategoryDataset();
        table = new JTable();
        model = new DefaultTableModel();
        setLayout(null);
        pnTop = new JPanel(null);
        pnCenter_3 = new JPanel();
        pnCenter_4 = new JPanel();
        pnCenter_5 = new JPanel();
        btnLuu = new JButton("Lưu trữ");
        pnTop.setBounds(10, 50, 1650, 100);
        btntimKiem = new JButton("Tìm kiếm");
        btntimKiem.setBounds(550, 10, 100, 30);
        btnRefresh = new JButton("Làm mới");
        btnRefresh.setBounds(660, 10, 100, 30);
        lblOverview = new JLabel("Tổng quan");
        lblFilter = new JLabel("Lọc theo:");
        cbFilter = new JComboBox();
        cbFilter.addItem("Hôm nay");
        cbFilter.addItem("1 tuần");
        cbFilter.addItem("1 tháng");
        cbFilter.addItem("3 tháng");
        cbFilter.addItem("Tuỳ chọn");
        dcFrom = new JDateChooser();
        dcFrom.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dcFrom.setBounds(100, 310, 200, 40);
        dcFrom.setDateFormatString("dd/MM/yyyy");
        dcFrom.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
        dcFrom.setFont(new Font("SansSerif", Font.PLAIN, 15));
        dcFrom.getCalendarButton().setPreferredSize(new Dimension(30, 24));
        dcFrom.getCalendarButton().setBackground(new Color(102, 0, 153));
        dcTo = new JDateChooser();
        dcTo.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dcTo.setBounds(310, 310, 200, 40);
        dcTo.setDateFormatString("dd/MM/yyyy");
        dcTo.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
        dcTo.setFont(new Font("SansSerif", Font.PLAIN, 15));
        dcTo.getCalendarButton().setPreferredSize(new Dimension(30, 24));
        dcTo.getCalendarButton().setBackground(new Color(102, 0, 153));

        dcFrom.setDate(Calendar.getInstance().getTime());
        dcTo.setDate(Calendar.getInstance().getTime());

        btnLuu.setBounds(770, 10, 100, 30);

        pnTop_1 = new JPanel(null);
        pnTop_1.setBounds(10, 0, 1650, 50);
        lblFilter.setBounds(10, 10, 100, 30);
        cbFilter.setBounds(120, 10, 100, 30);
        dcFrom.setBounds(230, 10, 150, 30);
        dcTo.setBounds(390, 10, 150, 30);
        pnTop_1.add(lblOverview);
        pnTop_1.add(lblFilter);
        pnTop_1.add(cbFilter);
        pnTop_1.add(dcFrom);
        pnTop_1.add(dcTo);
        pnTop_1.add(btntimKiem);
        pnTop_1.add(btnRefresh);
        pnTop_1.add(btnLuu);

        JPanel pn_1, pn_2, pn_3, pn_4;
        pn_1 = new JPanel(null);
        pn_2 = new JPanel(null);
        pn_3 = new JPanel(null);
        pn_4 = new JPanel(null);

        JLabel lblDonHang = new JLabel("Số lượng đơn hàng");
        lblDonHang.setBounds(10, 10, 200, 30);
        lblDonHang.setForeground(Color.WHITE);
        lblDonHangValue = new JLabel();
//        lblDonHangValue.setText(String.valueOf(mainController.sumTotalBill()));
        lblDonHangValue.setBounds(10, 40, 200, 30);
        lblDonHangValue.setForeground(Color.WHITE);
        lblDonHangValue.setFont(new Font("SansSerif", Font.BOLD, 20));
        JLabel lblGiaTriDonHang = new JLabel("Giá trị đơn hàng");
        lblGiaTriDonHangValue = new JLabel("1000000");
        lblGiaTriDonHang.setBounds(10, 10, 200, 30);
        lblGiaTriDonHang.setForeground(Color.WHITE);
        lblGiaTriDonHangValue.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblGiaTriDonHangValue.setBounds(10, 40, 200, 30);
        lblGiaTriDonHangValue.setForeground(Color.WHITE);
        JLabel lblThucThu = new JLabel("Thực thu");
        lblThucThu.setBounds(10, 10, 200, 30);
        lblThucThu.setForeground(Color.WHITE);
        lblThucThuValue = new JLabel("1000000");
        lblThucThuValue.setBounds(10, 40, 200, 30);
        lblThucThuValue.setForeground(Color.WHITE);
        lblThucThuValue.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblDoanhThu = new JLabel("Doanh thu");
        lblDoanhThu.setBounds(10, 10, 200, 30);
        lblDoanhThu.setForeground(Color.WHITE);
        lblDoanhThuValue = new JLabel("1000000");
        lblDoanhThuValue.setBounds(10, 40, 200, 30);
        lblDoanhThuValue.setForeground(Color.WHITE);
        lblDoanhThuValue.setFont(new Font("SansSerif", Font.BOLD, 20));

        pn_1.setBounds(50, 10, 200, 80);
        pn_1.setBackground(new Color(66, 109, 189));
        pn_1.add(lblDonHang);
        pn_1.add(lblDonHangValue);

        pn_2.setBounds(340, 10, 200, 80);
        pn_2.setBackground(new Color(66, 109, 189));
        pn_2.add(lblGiaTriDonHang);
        pn_2.add(lblGiaTriDonHangValue);

        pn_3.setBounds(660, 10, 200, 80);
        pn_3.setBackground(new Color(66, 109, 189));
        pn_3.add(lblThucThu);
        pn_3.add(lblThucThuValue);

        pn_4.setBounds(950, 10, 200, 80);
        pn_4.setBackground(new Color(66, 109, 189));
        pn_4.add(lblDoanhThu);
        pn_4.add(lblDoanhThuValue);

        pnCenter = new JPanel();
        pnCenter.setLayout(null);
        pnCenter.setBorder(BorderFactory.createTitledBorder("Sản phẩm bán chạy"));
        pnCenter.setBounds(50, 160, 350, 290);

        model.addColumn("Mã sản phẩm");
        model.addColumn("Tên sản phẩm");
        model.addColumn("Giá bán");
        model.addColumn("So luong");
//        model.addColumn("Tỷ lệ bán ra");
        table.setModel(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0, 20, 350, 260);
        pnCenter.add(sp);

        pnCenter_2 = new JPanel();
        pnCenter_2.setLayout(null);
        pnCenter_2.setBorder(BorderFactory.createTitledBorder("Doanh số theo hàng hoá"));
        pnCenter_2.setBounds(450, 160, 400, 290);
        Date today = Calendar.getInstance().getTime();
        dcFrom.setDate(today);
        dcTo.setDate(today);
        JFreeChart pieChart = createChart(createDataset(today, today));
        chartPanel = new ChartPanel(pieChart);
        chartPanel.setBounds(0, 20, 400, 270);
        pnCenter_2.add(chartPanel);


        pnCenter_3 = new JPanel();
        pnCenter_3.setLayout(null);
        pnCenter_3.setBorder(BorderFactory.createTitledBorder("Doanh thu và lợi nhuận theo thời gian"));
        pnCenter_3.setBounds(900, 160, 400, 290);
        JFreeChart chart = createChartBar(createBarChartDataset(today, today));
        panel = new ChartPanel(chart);
        panel.setBounds(0, 20, 400, 270);
        pnCenter_3.add(panel);

        pnCenter_4 = new JPanel();
        pnCenter_4.setLayout(null);
        pnCenter_4.setBorder(BorderFactory.createTitledBorder("Sản phẩm bán chậm"));
        pnCenter_4.setBounds(50, 470, 350, 260);
        model1 = new DefaultTableModel();
        model1.addColumn("Mã sản phẩm");
        model1.addColumn("Tên sản phẩm");
        model1.addColumn("Giá bán");
        model1.addColumn("So luong");
//        model1.addColumn("Tỷ lệ bán ra");
        table1 = new JTable();
        table1.setModel(model1);
        JScrollPane sp1 = new JScrollPane(table1);
        sp1.setBounds(0, 20, 350, 240);
        pnCenter_4.add(sp1);

        pnCenter_5 = new JPanel();
        pnCenter_5.setLayout(null);
        pnCenter_5.setBorder(BorderFactory.createTitledBorder("Doanh số theo nhân viên"));
        pnCenter_5.setBounds(450, 470, 800, 260);
        JFreeChart chart1 = createChartBar1(createBarChartDataset1(today, today));
        panel1 = new ChartPanel(chart1);
        panel1.setBounds(0, 20, 800, 240);
        pnCenter_5.add(panel1);


        pnTop.add(pn_1);
        pnTop.add(pn_2);
        pnTop.add(pn_3);
        pnTop.add(pn_4);

        add(pnCenter_2);
        add(pnCenter_3);
        add(pnCenter_4);
        add(pnCenter_5);
        add(pnCenter);
        add(pnTop_1);
        add(pnTop);


        dcFrom.setDate(Calendar.getInstance().getTime());
        dcTo.setDate(Calendar.getInstance().getTime());
        dcTo.setEnabled(false);
        dcFrom.setEnabled(false);
        btntimKiem.setEnabled(false);
        btntimKiem.addActionListener(this);
        btnRefresh.addActionListener(this);
        btnLuu.addActionListener(this);
        panel1.addMouseListener(this);
        panel.addMouseListener(this);
        chartPanel.addMouseListener(this);

        loadDataProductSales(dcFrom.getDate(), dcTo.getDate());
        loadDataProductWorstSeller(dcFrom.getDate(), dcTo.getDate());

        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        lblDonHangValue.setText(String.valueOf(mainController.sumTotalBillDate(dcFrom.getDate(), dcTo.getDate())));

        double doanhThuValue = mainController.sumTotalAmount(dcFrom.getDate(), dcTo.getDate());
        lblDoanhThuValue.setText(currencyFormat.format(doanhThuValue));

        double thucThuValue = mainController.sumProfit(dcFrom.getDate(), dcTo.getDate());
        lblThucThuValue.setText(currencyFormat.format(thucThuValue));

        double giaTriDonHangValue = mainController.sumTotalBillValue(dcFrom.getDate(), dcTo.getDate());
        lblGiaTriDonHangValue.setText(currencyFormat.format(giaTriDonHangValue));


//        lblDonHangValue.setText(String.valueOf(mainController.sumTotalBillDate(dcFrom.getDate(), dcTo.getDate())));
//        lblDoanhThuValue.setText(String.valueOf(mainController.sumTotalAmount(dcFrom.getDate(), dcTo.getDate())));
//        lblThucThuValue.setText(String.valueOf(mainController.sumProfit(dcFrom.getDate(), dcTo.getDate())));
//        lblGiaTriDonHangValue.setText(String.valueOf(mainController.sumTotalBillValue(dcFrom.getDate(), dcTo.getDate())));

        cbFilter.addItemListener(this);
    }

    @SneakyThrows
    public void loadDataProductWorstSeller(Date from, Date to) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        List<Object[]> list = mainController.findProductWorstSeller(from, to);
        model1.setRowCount(0);
        for (Object[] obj : list) {
            if (obj[2] != null) {
                double price = Double.parseDouble(obj[2].toString());
                obj[2] = currencyFormat.format(price);
            }
            model1.addRow(obj);
        }
    }

    @SneakyThrows
    public void loadDataProductSales(Date from, Date to) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        List<Object[]> list = mainController.findProductBestSeller(from, to);
        model.setRowCount(0);
        for (Object[] obj : list) {
            if (obj[2] != null) {
                double price = Double.parseDouble(obj[2].toString());
                obj[2] = currencyFormat.format(price);
            }
            model.addRow(obj);
        }
    }


    @SneakyThrows
    private PieDataset createDataset(Date startDate, Date endDate) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        mainController.sumTotalBillValueByProduct(startDate, endDate).forEach(objects -> {
            dataset.setValue(objects[1].toString(), Double.parseDouble(objects[2].toString()));
        });
        return dataset;
    }

    private static JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "", dataset, true, true, true);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1}({2})"));
        return chart;
    }

    @SneakyThrows
    private DefaultCategoryDataset createBarChartDataset(Date startDate, Date endDate) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        mainController.sumTotalBillValueByDate(startDate, endDate).forEach(objects -> {
            if (objects[0] != null && objects[1] != null) {
                LocalDate localDate = (LocalDate) objects[0];
                Date date = java.sql.Date.valueOf(localDate);
                String time = new SimpleDateFormat("yyyy-MM-dd").format(date);
                double value = Double.parseDouble(objects[1].toString());
                dataset.addValue(value, "Doanh Thu", time);
            }
        });

        mainController.sumTotalBillValueByDateLoiNhuan(startDate, endDate).forEach(objects -> {
            if (objects[0] != null && objects[1] != null) {
                LocalDate localDate = (LocalDate) objects[0];
                Date date = java.sql.Date.valueOf(localDate);
                String time = new SimpleDateFormat("yyyy-MM-dd").format(date);
                double value = Double.parseDouble(objects[1].toString());
                dataset.addValue(value, "Lợi Nhuận", time);
            }
        });

        return dataset;
    }


    private static JFreeChart createChartBar(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "",
                "Thời Gian",
                "Số Tiền",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chart.setBackgroundPaint(Color.WHITE);
        return chart;
    }


    private static JFreeChart createChartBar1(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "",
                "Nhân viên",
                "Số Lượng",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        return chart;
    }

    @SneakyThrows
    private DefaultCategoryDataset createBarChartDataset1(Date startDate, Date endDate) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        mainController.findEmployeeBestSeller(startDate, endDate).forEach(objects -> {
            if (objects[0] != null && objects[1] != null) {
                String name = objects[0].toString();
                double value = Double.parseDouble(objects[1].toString());
                dataset.addValue(value, "Doanh Thu", name);
            }
        });

        return dataset;
    }

    @SneakyThrows
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cbFilter && e.getStateChange() == ItemEvent.SELECTED) {
            String selectedOption = (String) cbFilter.getSelectedItem();
            if ("Hôm nay".equals(selectedOption)) {
                dcTo.setEnabled(false);
                dcFrom.setEnabled(false);
                Date today = new Date();
                if (!dataFound(today, today)) {
                    JOptionPane.showMessageDialog(null, "Không có dữ liệu");
                    return;
                }
                dcFrom.setDate(today);
                dcTo.setDate(today);
                loadDataProductSales(today, today);
                updateUI(today, today);
                updateChartWithNewData(today, today);
                updateBarChart(today, today);
                loadDataProductWorstSeller(today, today);
                updateBarChart1(today, today);
            } else if ("1 tuần".equals(selectedOption)) {
                dcTo.setEnabled(false);
                dcFrom.setEnabled(false);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, -7);
                Date startDate = calendar.getTime();
                Date endDate = new Date();
                if (!dataFound(startDate, endDate)) {
                    JOptionPane.showMessageDialog(null, "Không có dữ liệu");
                    refresh();
                    return;
                }
                dcFrom.setDate(startDate);
                dcTo.setDate(endDate);
                loadDataProductSales(startDate, endDate);
                updateUI(startDate, endDate);
                updateChartWithNewData(startDate, endDate);
                loadDataProductWorstSeller(startDate, endDate);
                updateBarChart(startDate, endDate);
                updateBarChart1(startDate, endDate);
            } else if ("1 tháng".equals(selectedOption)) {
                dcTo.setEnabled(false);
                dcFrom.setEnabled(false);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, -1);
                Date startDate = calendar.getTime();
                Date endDate = new Date();
                if (dataFound(startDate, endDate)) {
                    JOptionPane.showMessageDialog(null, "Không có dữ liệu");
                    refresh();
                    return;
                }
                dcFrom.setDate(startDate);
                dcTo.setDate(new Date());
                loadDataProductSales(startDate, endDate);
                updateUI(startDate, endDate);
                updateChartWithNewData(startDate, endDate);
                loadDataProductWorstSeller(startDate, endDate);
                updateBarChart(startDate, endDate);
                updateBarChart1(startDate, endDate);
            } else if ("3 tháng".equals(selectedOption)) {
                dcTo.setEnabled(false);
                dcFrom.setEnabled(false);
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, -3);
                Date startDate = calendar.getTime();
                Date endDate = new Date();
                if (!dataFound(startDate, endDate)) {
                    JOptionPane.showMessageDialog(null, "Không có dữ liệu");
                    refresh();
                    return;
                }
                dcFrom.setDate(startDate);
                dcTo.setDate(new Date());
                loadDataProductSales(startDate, endDate);
                updateUI(startDate, endDate);
                updateChartWithNewData(startDate, endDate);
                loadDataProductWorstSeller(startDate, endDate);
                updateBarChart(startDate, endDate);
                updateBarChart1(startDate, endDate);
            } else if ("Tuỳ chọn".equals(selectedOption)) {
                btntimKiem.setEnabled(true);
                dcTo.setEnabled(true);
                dcFrom.setEnabled(true);
                dcFrom.setDate(new Date());
                dcTo.setDate(new Date());
                if (!dataFound(dcFrom.getDate(), dcTo.getDate())) {
                    JOptionPane.showMessageDialog(null, "Không có dữ liệu");
                    refresh();
                    return;
                }
                loadDataProductSales(dcFrom.getDate(), dcTo.getDate());
                updateUI(dcFrom.getDate(), dcTo.getDate());
                updateChartWithNewData(dcFrom.getDate(), dcTo.getDate());
                loadDataProductWorstSeller(dcFrom.getDate(), dcTo.getDate());
                updateBarChart(dcFrom.getDate(), dcTo.getDate());
                updateBarChart1(dcFrom.getDate(), dcTo.getDate());
            }
        }
    }

    public boolean dataFound(Date startDate, Date endDate) throws RemoteException {
        boolean check = mainController.findBillExist(startDate, endDate);
        if (check) {
            return true;
        } else {
            return false;
        }
    }

    @SneakyThrows
    public void updateUI(Date startDate, Date endDate) {
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        lblDonHangValue.setText(String.valueOf(mainController.sumTotalBillDate(startDate, endDate)));

        double doanhThuValue = mainController.sumTotalAmount(startDate, endDate);
        lblDoanhThuValue.setText(currencyFormat.format(doanhThuValue));

        double thucThuValue = mainController.sumProfit(startDate, endDate);
        lblThucThuValue.setText(currencyFormat.format(thucThuValue));

        double giaTriDonHangValue = mainController.sumTotalBillValue(startDate, endDate);
        lblGiaTriDonHangValue.setText(currencyFormat.format(giaTriDonHangValue));

    }


    public void updateChartWithNewData(Date startDate, Date endDate) {
        PieDataset dataset = createDataset(startDate, endDate);
        Component[] component = pnCenter_2.getComponents();
        for (Component component1 : component) {
            if (component1 instanceof ChartPanel) {
                ChartPanel chartPanel = (ChartPanel) component1;
                JFreeChart chart = chartPanel.getChart();
                PiePlot plot = (PiePlot) chart.getPlot();
                plot.setDataset(dataset);
                chartPanel.repaint();
            }
        }
    }

    private void updateBarChart(Date startDate, Date endDate) {
        DefaultCategoryDataset updatedDataset = createBarChartDataset(startDate, endDate);
        Component[] components = pnCenter_3.getComponents();
        for (Component component : components) {
            if (component instanceof ChartPanel) {
                ChartPanel chartPanel = (ChartPanel) component;
                JFreeChart chart = createChartBar(updatedDataset);
                chartPanel.setChart(chart);
                chartPanel.repaint();
            }
        }
    }
//    JFreeChart chart = createChartBar(createBarChartDataset(today, today));
//    ChartPanel panel = new ChartPanel(chart);

    private void updateBarChart1(Date startDate, Date endDate) {
        DefaultCategoryDataset updatedDataset = createBarChartDataset1(startDate, endDate);
        Component[] components = pnCenter_5.getComponents();
        for (Component component : components) {
            if (component instanceof ChartPanel) {
                ChartPanel chartPanel = (ChartPanel) component;
                JFreeChart chart = createChartBar1(updatedDataset);
                chartPanel.setChart(chart);
                chartPanel.repaint();
            }
        }
    }

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btntimKiem)) {
            Date startDate = dcFrom.getDate();
            Date endDate = dcTo.getDate();
            if (!dataFound(startDate, endDate)) {
                JOptionPane.showMessageDialog(null, "Không có dữ liệu");
                refresh();
                return;
            } else if (startDate.after(endDate)) {
                JOptionPane.showMessageDialog(null, "Ngày bắt đầu không thể sau ngày kết thúc");
                return;
            } else if (startDate.equals(endDate)) {
                JOptionPane.showMessageDialog(null, "Ngày bắt đầu không thể trùng với ngày kết thúc");
                return;
            } else if (startDate.after(Calendar.getInstance().getTime()) || endDate.after(Calendar.getInstance().getTime())) {
                JOptionPane.showMessageDialog(null, "Ngày không thể sau ngày hiện tại");
                return;
            } else if (startDate.before(endDate)) {
                JOptionPane.showMessageDialog(null, "Ngày bắt đầu không thể trước ngày kết thúc");
                return;
            } else {
                JOptionPane.showMessageDialog(this, "Tìm kiếm thành công");
                loadDataProductSales(startDate, endDate);
                updateUI(startDate, endDate);
                updateChartWithNewData(startDate, endDate);
                loadDataProductWorstSeller(startDate, endDate);
                updateBarChart(startDate, endDate);
                updateBarChart1(startDate, endDate);
            }
        } else if (o.equals(btnRefresh)) {
            refresh();
        } else if (o.equals(btnLuu)) {
            exportDataToExcel();
        }
    }

    @SneakyThrows
    private void exportDataToExcel() {
        Date dateFrom = dcFrom.getDate();
        Date dateTo = dcTo.getDate();

        List<Object[]> employeeData = mainController.dialogThongNhanVien(dateFrom, dateTo);
        List<Object[]> productData = mainController.dialogLoiNhuanDoanhThu(dateFrom, dateTo);
        List<Object[]> productWorstSeller = mainController.findProductWorstSeller(dateFrom, dateTo);
        List<Object[]> productBestSeller = mainController.findProductBestSeller(dateFrom, dateTo);
        List<Object[]> productValueByProduct = mainController.sumTotalBillValueByProduct(dateFrom, dateTo);

        Workbook workbook = new XSSFWorkbook();
        try {
            List<String> employeeColumnNames = Arrays.asList("ID", "Name", "ID Khach Hang", "Ten Khach Hang", "Bill Date", "Total Amount", "Total Bill");
            saveToExcel(workbook, employeeColumnNames, employeeData, "EmployeeInformation");

            List<String> productColumnNames = Arrays.asList("Ngày", "Loi nhuan", "Doanh thu");
            saveToExcel(workbook, productColumnNames, productData, "ProductInformation");

            List<String> productWorstSellerColumnNames = Arrays.asList("Mã sản phẩm", "Tên sản phẩm", "Giá bán", "So luong");
            saveToExcel(workbook, productWorstSellerColumnNames, productWorstSeller, "ProductWorstSeller");


            List<String> productDataColumnNames = Arrays.asList("Mã sản phẩm", "Tên sản phẩm", "Giá bán", "So luong");
            saveToExcel(workbook, productDataColumnNames, productBestSeller, "ProductData");


            List<String> valueByProductColumnNames = Arrays.asList("Mã sản phẩm", "Tên sản phẩm", "Giá bán", "So luong");
            saveToExcel(workbook, valueByProductColumnNames, productValueByProduct, "ValueByProduct");

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = System.getProperty("user.dir") + "/src/main/resources/DataStatisticals/S_" + timeStamp + ".xlsx";
            FileOutputStream fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            JOptionPane.showMessageDialog(null, "Xuất Excel thành công.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu dữ liệu vào tệp Excel: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void saveToExcel(Workbook workbook, List<String> columnNames, List<Object[]> data, String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName);


        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnNames.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnNames.get(i));
        }

        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Object[] rowData = data.get(i);
            for (int j = 0; j < rowData.length; j++) {
                Cell cell = row.createCell(j);
                if (rowData[j] instanceof Double) {
                    cell.setCellValue(Double.parseDouble(rowData[j].toString()));
                } else {
                    cell.setCellValue(rowData[j].toString());
                }
            }
        }
    }


    public void refresh() throws RemoteException {
        cbFilter.setSelectedIndex(0);
        dcFrom.setDate(Calendar.getInstance().getTime());
        dcTo.setDate(Calendar.getInstance().getTime());
        loadDataProductSales(dcFrom.getDate(), dcTo.getDate());
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        lblDonHangValue.setText(String.valueOf(mainController.sumTotalBillDate(dcFrom.getDate(), dcTo.getDate())));

        double doanhThuValue = mainController.sumTotalAmount(dcFrom.getDate(), dcTo.getDate());
        lblDoanhThuValue.setText(currencyFormat.format(doanhThuValue));

        double thucThuValue = mainController.sumProfit(dcFrom.getDate(), dcTo.getDate());
        lblThucThuValue.setText(currencyFormat.format(thucThuValue));

        double giaTriDonHangValue = mainController.sumTotalBillValue(dcFrom.getDate(), dcTo.getDate());
        lblGiaTriDonHangValue.setText(currencyFormat.format(giaTriDonHangValue));
        Date today = Calendar.getInstance().getTime();
        dcFrom.setDate(today);
        dcTo.setDate(today);
        loadDataProductSales(today, today);
        updateUI(today, today);
        updateChartWithNewData(today, today);
        updateBarChart(today, today);
        loadDataProductWorstSeller(today, today);
        updateBarChart1(today, today);
        table.clearSelection();
    }

    @SneakyThrows
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == panel1 && e.getClickCount() == 2) {
            Date dateFrom = dcFrom.getDate();
            Date dateTo = dcTo.getDate();
            List<Object[]> employeeData = mainController.dialogThongNhanVien(dateFrom, dateTo);
            showEmployeeDialog(employeeData);
        } else if (e.getSource() == panel && e.getClickCount() == 2) {
            Date dateFrom = dcFrom.getDate();
            Date dateTo = dcTo.getDate();
            List<Object[]> productData = mainController.dialogLoiNhuanDoanhThu(dateFrom, dateTo);
            showProductDialog(productData);
        } else if (e.getSource() == chartPanel && e.getClickCount() == 2) {
            Date dateFrom = dcFrom.getDate();
            Date dateTo = dcTo.getDate();
            List<Object[]> valueByProduct = mainController.sumTotalBillValueByProduct(dateFrom, dateTo);
            showSumTotalBillValueByProductDialog(valueByProduct);
        }
    }

    public void showProductDialog(List<Object[]> productData) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        JDialog dialog = new JDialog();
        dialog.setTitle("Product Information");

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Ngày");
        model.addColumn("Loi nhuan");
        model.addColumn("Doanh thu");

        for (Object[] row : productData) {
            if (row[1] != null) {
                double loinhuan = Double.parseDouble(row[1].toString());
                row[1] = currencyFormat.format(loinhuan);
            }
            if (row[2] != null) {
                double totalAmount = Double.parseDouble(row[2].toString());
                row[2] = currencyFormat.format(totalAmount);
            }
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void showEmployeeDialog(List<Object[]> employeeData) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        JDialog dialog = new JDialog();
        dialog.setTitle("Employee Information");

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("ID Khach Hang");
        model.addColumn("Ten Khach Hang");
        model.addColumn("Bill Date");
        model.addColumn("Total Amount");
        model.addColumn("Total Bill");

        for (Object[] row : employeeData) {
            if (row[5] != null) {
                double price = Double.parseDouble(row[5].toString());
                row[5] = currencyFormat.format(price);
            }
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void showSumTotalBillValueByProductDialog(List<Object[]> valueByProduct) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        JDialog dialog = new JDialog();
        dialog.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Mã sản phẩm");
        model.addColumn("Tên sản phẩm");
        model.addColumn("Giá bán");
        model.addColumn("Số lượng");

        for (Object[] row : valueByProduct) {
            if (row[2] != null) {
                double price = Double.parseDouble(row[2].toString());
                row[2] = currencyFormat.format(price);
            }
            model.addRow(row);
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Đóng");
        closeButton.addActionListener(e -> dialog.dispose());
        JButton exportButton = new JButton("Xuất Excel");
        buttonPanel.add(closeButton);
        buttonPanel.add(exportButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        exportButton.addActionListener(e -> exportToExcelSumTotalBillValueByProduct(model));

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    @SneakyThrows
    private void exportToExcelSumTotalBillValueByProduct(DefaultTableModel model) {
        Date dateFrom = dcFrom.getDate();
        Date dateTo = dcTo.getDate();

        Workbook workbook = new XSSFWorkbook();
        List<Object[]> productValueByProduct = mainController.sumTotalBillValueByProduct(dateFrom, dateTo);
        List<String> columnNames = Arrays.asList("Mã sản phẩm", "Tên sản phẩm", "Giá bán", "Số lượng");
        saveToExcel(workbook, columnNames, productValueByProduct, "ValueByProduct");

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filePath = System.getProperty("user.dir") + "/src/main/resources/DataStatisticals/ValueByProduct/P_" + timeStamp + ".xlsx";
        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

        JOptionPane.showMessageDialog(null, "Xuất Excel thành công.");

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
