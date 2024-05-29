package service;

import dao.SaleManagementDao;
import dao.impl.SaleManagementDaoImpl;
import models.Bill;
import models.BillPending;
import models.DetailsBill;
import models.DetailsBillPending;
import service.serviceImpl.RoleDaoService;
import service.serviceImpl.SaleManagementDaoService1;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class SaleManagementDaoService extends UnicastRemoteObject implements SaleManagementDaoService1 {
    private final SaleManagementDao dao = new SaleManagementDaoImpl();

    public SaleManagementDaoService() throws RemoteException {
    }

    public boolean addBillPending(BillPending billPending) throws RemoteException {
        return dao.addBillPending(billPending);
    }

    public boolean addDetailBillPending(DetailsBillPending detailsBillPending) throws RemoteException {
        return dao.addDetailsBillPending(detailsBillPending);
    }

    public List<DetailsBillPending> getDetailBillPendingByIdBill(String idBill) throws RemoteException {
        return dao.findDetailsBillPendingById(idBill);
    }

    public boolean deleteDetailsBillPendingById(String idBill) throws RemoteException {
        return dao.deleteDetailsBillPendingById(idBill);
    }

    public boolean deleteBillPendingById(String idBill) throws RemoteException {
        return dao.deleteBillPendingById(idBill);
    }

    public List<BillPending> getAllBillPending() throws RemoteException {
        return dao.getAllBillPending();
    }

    public List<DetailsBillPending> getAllDetailBillPending() throws RemoteException {
        return dao.getAllDetailsBillPending();
    }

    public boolean deleteAllBillPending() throws RemoteException {
        return dao.deleteAllBillPending();
    }

    public boolean deleteAllDetailBillPending() throws RemoteException {
        return dao.deleteAllDetailBillPending();
    }

    public boolean addBill(Bill bill) throws RemoteException {
        return dao.addBill(bill);
    }

    public Bill getBillById(String idBill) throws RemoteException {
        return dao.findBillById(idBill);
    }

    public boolean addDetailsBill(DetailsBill detailsBill) throws RemoteException {
        return dao.addDetailBill(detailsBill);
    }

    public List<Object[]> getAllBill() throws RemoteException {
        return dao.getAllBill();
    }

    public List<Object[]> loadDataProduct(String id) throws RemoteException {
        return dao.loadDataProduct(id);
    }

    public int sumTotalBill() throws RemoteException {
        return dao.sumTotalBill();
    }

    public List<Object[]> findBillByCustomerSDT(String sdt) throws RemoteException {
        return dao.findBillByCustomerSDT(sdt);
    }

    public List<Object[]> findProductBestSeller(Date from, Date to) throws RemoteException {
        return dao.findProductBestSeller(from, to);
    }

    public double sumTotalAmount(Date from, Date to) throws RemoteException {
        return dao.sumTotalAmount(from, to);
    }

    public int sumTotalBillDate(Date from, Date to) throws RemoteException {
        return dao.sumTotalBillDate(from, to);
    }

    public double sumProfit(Date from, Date to) throws RemoteException {
        return dao.sumProfit(from, to);
    }

    public double sumTotalBillValue(Date from, Date to) throws RemoteException {
        return dao.sumTotalBillValue(from, to);
    }

    public List<Object[]> sumTotalBillValueByProduct(Date from, Date to) throws RemoteException {
        return dao.sumTotalBillValueByProduct(from, to);
    }

    public List<Object[]> sumTotalBillValueDoanhThu(Date from, Date to) throws RemoteException {
        return dao.sumTotalBillValueDoanhThu(from, to);
    }

    public List<Object[]> sumTotalBillValueByDate(Date from, Date to) throws RemoteException {
        return dao.sumTotalBillValueByDate(from, to);
    }

    public List<Object[]> sumTotalBillValueByDateLoiNhuan(Date from, Date to) throws RemoteException {
        return dao.sumTotalBillValueByDateLoiNhuan(from, to);
    }

    public List<Object[]> findProductWorstSeller(Date from, Date to) throws RemoteException {
        return dao.findProductWorstSeller(from, to);
    }

    public List<Object[]> findEmployeeBestSeller(Date from, Date to) throws RemoteException {
        return dao.findEmployeeBestSeller(from, to);
    }

    public List<BillPending> findBillPendingByText(String text) throws RemoteException {
        return dao.findBillPendingByText(text);
    }

    public List<DetailsBill> findDetailsBillByBillId(String id) throws RemoteException {
        return dao.findDetailsBillById(id);
    }

    public List<Bill> findBillByEmployee(String id) throws RemoteException {
        return dao.findBillByEmployee(id);
    }

    public List<Bill> findBillByDate(LocalDate from, LocalDate to) throws RemoteException {
        return dao.findBillByDate(from, to);
    }

    public boolean findBillExist(Date dateFrom, Date dateTo) throws RemoteException {
        return dao.findBillExist(dateFrom, dateTo);
    }

    public List<Object[]> dialogThongNhanVien(Date dateFrom, Date dateTo) throws RemoteException {
        return dao.dialogThongNhanVien(dateFrom, dateTo);
    }

    public List<Object[]> dialogLoiNhuanDoanhThu(Date dateFrom, Date dateTo) throws RemoteException {
        return dao.dialogLoiNhuanDoanhThu(dateFrom, dateTo);
    }
}
