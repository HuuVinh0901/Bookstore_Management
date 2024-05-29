package controller;

import models.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface MainControllerInterface extends Remote {
    List<BillPending> findBillPendingByText(String text) throws RemoteException;

    boolean addBillPending(BillPending billPending) throws RemoteException;

    boolean addDetailBillPending(DetailsBillPending detailsBillPending) throws RemoteException;

    List<DetailsBillPending> getDetailBillPendingByIdBill(String idBill) throws RemoteException;

    Promotion findPromotionById(String id) throws RemoteException;

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

    List<DetailsBill> findDetailsBillByBillId(String id) throws RemoteException;

    List<Bill> findBillByEmployee(String id) throws RemoteException;

    List<Bill> findBillByDate(LocalDate from, LocalDate to) throws RemoteException;

    List<ProductSale> getAllAProductSale() throws RemoteException;

    boolean addProductSale(ProductSale productSale) throws RemoteException;

    boolean deleteProductSale(String id) throws RemoteException;

    ProductSale getProductSale(String productSaleId) throws RemoteException;

    boolean deleteProductSaleByPromotionId(String promotionId) throws RemoteException;

    List<ProductSale> getProductSaleByPromotionId(String productSaleId) throws RemoteException;

    List<ProductSale> findProductSaleByText(String text) throws RemoteException;

    Product getProductById(String id) throws RemoteException;

    List<Product> getProductByText(String text) throws RemoteException;

    boolean updateProductQuantity(String id, int quantity) throws RemoteException;

    List<Customer> getAllCustomers() throws RemoteException;

    boolean addCustomer(Customer customer) throws RemoteException;

    boolean updateCustomer(Customer customer) throws RemoteException;

    boolean deleteCustomer(String id) throws RemoteException;

    List<Customer> findCustomerByText(String text) throws RemoteException;

    Customer findCustomerById(String id) throws RemoteException;

    List<Promotion> getAllPromotion() throws RemoteException;

    boolean addPromotion(Promotion promotion) throws RemoteException;

    boolean deletePromotion(String id) throws RemoteException;

    List<Promotion> findPromotionByText(String text) throws RemoteException;

    boolean updatePromotion(Promotion promotion) throws RemoteException;

    List<Account> getAllAccount() throws RemoteException;

    boolean addAccount(Account account) throws RemoteException;

    boolean updateAccount(Account account) throws RemoteException;

    boolean deleteAccount(String id) throws RemoteException;

    Account getAccountById(String id) throws RemoteException;

    String findPasswordByEmployeeId(String employeeId) throws RemoteException;

    List<Role> getAllRole() throws RemoteException;

    boolean addRole(Role role) throws RemoteException;

    boolean deleteRole(String id) throws RemoteException;

    Role findRoleByText(String text) throws RemoteException;

    List<Role> getRolesByRoleCode(int roleCode) throws RemoteException;

    boolean updateRole(String id) throws RemoteException;

    List<Employee> getAllEmployees() throws RemoteException;

    boolean addEmployee(Employee employee) throws RemoteException;

    boolean updateEmployee(Employee employee) throws RemoteException;

    boolean deleteEmployee(String id) throws RemoteException;

    List<Employee> findEmployeeByText(String text) throws RemoteException;

    List<Employee> findEmployeeByRoleCode(int roleCode) throws RemoteException;

    Employee findEmployeeById(String empId) throws RemoteException;

    List<Author> getAllAuthor() throws RemoteException;

    boolean addAuthor(Author author) throws RemoteException;

    boolean updateAuthor(Author author) throws RemoteException;

    boolean deleteAuthor(String idAuthor) throws RemoteException;

    boolean checkIdAuthor(String idAuthor) throws RemoteException;

    List<Author> getLatestAuthorID() throws RemoteException;

    boolean decreaseNumberOfBooks(String idAuthor) throws RemoteException;

    boolean increaseNumberOfBooks(String idAuthor) throws RemoteException;

    List<Category> getAllCategory() throws RemoteException;

    boolean addCategory(Category category) throws RemoteException;

    boolean updateCategory(Category category) throws RemoteException;

    boolean deleteCategory(String idCategory) throws RemoteException;

    boolean checkIdCategory(String idCategory) throws RemoteException;

    List<String> getLatestCategoryID() throws RemoteException;

    boolean decreaseNumberOfCategory(String idCategory) throws RemoteException;

    boolean increaseNumberOfCategory(String idCategory) throws RemoteException;

    List<ProductType> getAllProductType() throws RemoteException;

    ProductType getProductTypeById(String id) throws RemoteException;

    boolean addProductType(ProductType productType) throws RemoteException;

    boolean updateProductType(ProductType productType) throws RemoteException;

    boolean deleteProductType(String id) throws RemoteException;

    List<String> getLastestProductType() throws RemoteException;

    boolean checkProductTypeExist(String id) throws RemoteException;

    List<Supplier> getAllSuppliers() throws RemoteException;

    boolean addSupplier(Supplier supplier) throws RemoteException;

    boolean updateSupplier(Supplier supplier) throws RemoteException;

    boolean deleteSupplier(String id) throws RemoteException;

    boolean checkSupplierId(String id) throws RemoteException;

    List<Supplier> getLatestSupplierId() throws RemoteException;

    List<Book> getAllBook() throws RemoteException;

    boolean addBook(Book book) throws RemoteException;

    boolean updateBook(Book book) throws RemoteException;

    boolean deleteBook(String idBook) throws RemoteException;

    boolean checkIdBook(String idBook) throws RemoteException;

    List<String> getLatestBookID() throws RemoteException;

    List<Book> loadComboBoxByProductType(String productTypeId) throws RemoteException;

    List<Book> loadComboBoxByPublisher(String publisherId) throws RemoteException;

    List<Book> loadComboBoxByAuthor(String authorId) throws RemoteException;

    List<Book> loadComboBoxByCategory(String categoryId) throws RemoteException;

    List<Book> searchBook(String search) throws RemoteException;

    List<Book> findCategoryIdByName(String name) throws RemoteException;

    List<Book> findSupplierByName(String name) throws RemoteException;

    List<Book> findBookByProductType(String name) throws RemoteException;

    List<Book> findBookByAuthor(String name) throws RemoteException;

    List<Merchandise> getAllMerchandise() throws RemoteException;

    boolean addMerchandise(Merchandise merchandise) throws RemoteException;

    boolean checkIdExist(String id) throws RemoteException;

    boolean updateMerchandise(Merchandise merchandise) throws RemoteException;

    boolean deleteMerchandise(String id) throws RemoteException;

    List<Merchandise> findBookByProductTypeMerchandise(String name) throws RemoteException;

    List<Merchandise> findSupplierByNameMerchandise(String name) throws RemoteException;

    void notifyServer(String message) throws RemoteException;

    boolean findBillExist(Date dateFrom, Date dateTo) throws RemoteException;

    public List<Object[]> dialogThongNhanVien(Date dateFrom, Date dateTo) throws RemoteException;

    public List<Object[]> dialogLoiNhuanDoanhThu(Date dateFrom, Date dateTo) throws RemoteException;
}