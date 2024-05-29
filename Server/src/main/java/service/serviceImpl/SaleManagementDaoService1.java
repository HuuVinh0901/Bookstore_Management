package service.serviceImpl;

import models.Bill;
import models.BillPending;
import models.DetailsBill;
import models.DetailsBillPending;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SaleManagementDaoService1 extends Remote {
    boolean addBillPending(BillPending billPending) throws RemoteException;

    boolean addDetailBillPending(DetailsBillPending detailsBillPending) throws RemoteException;

    List<DetailsBillPending> getDetailBillPendingByIdBill(String idBill) throws RemoteException;

    boolean deleteDetailsBillPendingById(String idBill) throws RemoteException;

    boolean deleteBillPendingById(String idBill) throws RemoteException;

    List<BillPending> getAllBillPending() throws RemoteException;

    List<DetailsBillPending> getAllDetailBillPending() throws RemoteException;

    boolean deleteAllBillPending() throws RemoteException;

    boolean deleteAllDetailBillPending() throws RemoteException;

    boolean addBill(Bill bill) throws RemoteException;

    Bill getBillById(String idBill) throws RemoteException;

    boolean addDetailsBill(DetailsBill detailsBill) throws RemoteException;

    List<Object[]> getAllBill() throws RemoteException;

    List<Object[]> loadDataProduct(String id) throws RemoteException;

    int sumTotalBill() throws RemoteException;

    List<Object[]> findBillByCustomerSDT(String sdt) throws RemoteException;

    List<Object[]> findProductBestSeller(Date from, Date to) throws RemoteException;

    double sumTotalAmount(Date from, Date to) throws RemoteException;

    int sumTotalBillDate(Date from, Date to) throws RemoteException;

    double sumProfit(Date from, Date to) throws RemoteException;

    double sumTotalBillValue(Date from, Date to) throws RemoteException;

    List<Object[]> sumTotalBillValueByProduct(Date from, Date to) throws RemoteException;

    List<Object[]> sumTotalBillValueDoanhThu(Date from, Date to) throws RemoteException;

    List<Object[]> sumTotalBillValueByDate(Date from, Date to) throws RemoteException;

    List<Object[]> sumTotalBillValueByDateLoiNhuan(Date from, Date to) throws RemoteException;

    List<Object[]> findProductWorstSeller(Date from, Date to) throws RemoteException;

    List<Object[]> findEmployeeBestSeller(Date from, Date to) throws RemoteException;

    List<BillPending> findBillPendingByText(String text) throws RemoteException;

    List<DetailsBill> findDetailsBillByBillId(String id) throws RemoteException;

    List<Bill> findBillByEmployee(String id) throws RemoteException;

    List<Bill> findBillByDate(LocalDate from, LocalDate to) throws RemoteException;

    boolean findBillExist(Date dateFrom, Date dateTo) throws RemoteException;

    List<Object[]> dialogThongNhanVien(Date dateFrom, Date dateTo) throws RemoteException;

    List<Object[]> dialogLoiNhuanDoanhThu(Date dateFrom, Date dateTo) throws RemoteException;

}

