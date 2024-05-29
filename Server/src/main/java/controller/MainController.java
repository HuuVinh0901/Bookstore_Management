
package controller;

import models.*;
import service.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MainController extends UnicastRemoteObject implements MainControllerInterface {
    private static final long serialVersionUID = -2148570638038541964L;
    private final AuthorDaoImplService authorDaoImplService;
    private final CategoryDaoImplService categoryDaoImplService;
    private final ProductTypeDaoImplService productTypeDaoImplService;
    private final SupplierDaoImplService supplierDaoImplService;
    private final BookDaoImplService bookDaoImplService;
    private final MerchandiseDaoImplService merchandiseDaoImplService;

    private final CustomerDaoImplService customerDaoImplService;
    private final EmployeeDaoImplService employeeDaoImplService;
    private final AccountDaoImplService accountDaoImplService;
    private final RoleDaoImplService roleDaoImplService;
    private final PromotionDaoImplService promotionDaoImplService;
    private final ProductSaleDaoImplService productSaleDaoImplService;
    private final ProductDaoImplService productDaoImplService;
    private final SaleManagementDaoService saleManagementDaoImplService;

    public MainController(AuthorDaoImplService authorDaoImplService, CategoryDaoImplService categoryDaoImplService, ProductTypeDaoImplService productTypeDaoImplService, SupplierDaoImplService supplierDaoImplService, BookDaoImplService bookDaoImplService, MerchandiseDaoImplService merchandiseDaoImplService, CustomerDaoImplService customerDaoImplService, EmployeeDaoImplService employeeDaoImplService, AccountDaoImplService accountDaoImplService, RoleDaoImplService roleDaoImplService, PromotionDaoImplService promotionDaoImplService, ProductSaleDaoImplService productSaleDaoImplService, ProductDaoImplService productDaoImplService, SaleManagementDaoService saleManagementDaoImplService) throws RemoteException {
        this.authorDaoImplService = authorDaoImplService;
        this.categoryDaoImplService = categoryDaoImplService;
        this.productTypeDaoImplService = productTypeDaoImplService;
        this.supplierDaoImplService = supplierDaoImplService;
        this.bookDaoImplService = bookDaoImplService;
        this.merchandiseDaoImplService = merchandiseDaoImplService;
        this.customerDaoImplService = customerDaoImplService;
        this.employeeDaoImplService = employeeDaoImplService;
        this.accountDaoImplService = accountDaoImplService;
        this.roleDaoImplService = roleDaoImplService;
        this.promotionDaoImplService = promotionDaoImplService;
        this.productSaleDaoImplService = productSaleDaoImplService;
        this.productDaoImplService = productDaoImplService;
        this.saleManagementDaoImplService = saleManagementDaoImplService;
    }

    public MainController() throws RemoteException {
        this(
                new AuthorDaoImplService(),
                new CategoryDaoImplService(),
                new ProductTypeDaoImplService(),
                new SupplierDaoImplService(),
                new BookDaoImplService(),
                new MerchandiseDaoImplService(),
                new CustomerDaoImplService(),
                new EmployeeDaoImplService(),
                new AccountDaoImplService(),
                new RoleDaoImplService(),
                new PromotionDaoImplService(),
                new ProductSaleDaoImplService(),
                new ProductDaoImplService(),
                new SaleManagementDaoService()
        );
    }

    //BillPending,Bill
    public List<BillPending> findBillPendingByText(String text) throws RemoteException {
        return saleManagementDaoImplService.findBillPendingByText(text);
    }

    public boolean addBillPending(BillPending billPending) throws RemoteException {
        return saleManagementDaoImplService.addBillPending(billPending);
    }

    public boolean addDetailBillPending(DetailsBillPending detailsBillPending) throws RemoteException {
        return saleManagementDaoImplService.addDetailBillPending(detailsBillPending);
    }

    public List<DetailsBillPending> getDetailBillPendingByIdBill(String idBill) throws RemoteException {
        return saleManagementDaoImplService.getDetailBillPendingByIdBill(idBill);
    }

    public boolean deleteDetailsBillPendingById(String idBill) throws RemoteException {
        return saleManagementDaoImplService.deleteDetailsBillPendingById(idBill);
    }

    public boolean deleteBillPendingById(String idBill) throws RemoteException {
        return saleManagementDaoImplService.deleteBillPendingById(idBill);
    }

    public List<BillPending> getAllBillPending() throws RemoteException {
        return saleManagementDaoImplService.getAllBillPending();
    }

    public List<DetailsBillPending> getAllDetailBillPending() throws RemoteException {
        return saleManagementDaoImplService.getAllDetailBillPending();
    }

    public boolean deleteAllBillPending() throws RemoteException {
        return saleManagementDaoImplService.deleteAllBillPending();
    }

    public boolean deleteAllDetailBillPending() throws RemoteException {
        return saleManagementDaoImplService.deleteAllDetailBillPending();
    }

    public boolean addBill(Bill bill) throws RemoteException {
        return saleManagementDaoImplService.addBill(bill);
    }

    public Bill getBillById(String idBill) throws RemoteException {
        return saleManagementDaoImplService.getBillById(idBill);
    }

    public boolean addDetailsBill(DetailsBill detailsBill) throws RemoteException {
        return saleManagementDaoImplService.addDetailsBill(detailsBill);
    }

    public List<Object[]> getAllBill() throws RemoteException {
        return saleManagementDaoImplService.getAllBill();
    }

    public List<Object[]> loadDataProduct(String id) throws RemoteException {
        return saleManagementDaoImplService.loadDataProduct(id);
    }

    public int sumTotalBill() throws RemoteException {
        return saleManagementDaoImplService.sumTotalBill();
    }

    public List<Object[]> findBillByCustomerSDT(String sdt) throws RemoteException {
        return saleManagementDaoImplService.findBillByCustomerSDT(sdt);
    }

    public List<Object[]> findProductBestSeller(Date from, Date to) throws RemoteException {
        return saleManagementDaoImplService.findProductBestSeller(from, to);
    }

    public double sumTotalAmount(Date from, Date to) throws RemoteException {
        return saleManagementDaoImplService.sumTotalAmount(from, to);
    }

    public int sumTotalBillDate(Date from, Date to) throws RemoteException {
        return saleManagementDaoImplService.sumTotalBillDate(from, to);
    }

    public double sumProfit(Date from, Date to) throws RemoteException {
        return saleManagementDaoImplService.sumProfit(from, to);
    }

    public double sumTotalBillValue(Date from, Date to) throws RemoteException {
        return saleManagementDaoImplService.sumTotalBillValue(from, to);
    }

    public List<Object[]> sumTotalBillValueByProduct(Date from, Date to) throws RemoteException {
        return saleManagementDaoImplService.sumTotalBillValueByProduct(from, to);
    }

    public List<Object[]> sumTotalBillValueDoanhThu(Date from, Date to) throws RemoteException {
        return saleManagementDaoImplService.sumTotalBillValueDoanhThu(from, to);
    }

    public List<Object[]> sumTotalBillValueByDate(Date from, Date to) throws RemoteException {
        return saleManagementDaoImplService.sumTotalBillValueByDate(from, to);
    }

    public List<Object[]> sumTotalBillValueByDateLoiNhuan(Date from, Date to) throws RemoteException {
        return saleManagementDaoImplService.sumTotalBillValueByDateLoiNhuan(from, to);
    }

    public List<Object[]> findProductWorstSeller(Date from, Date to) throws RemoteException {
        return saleManagementDaoImplService.findProductWorstSeller(from, to);
    }

    public List<Object[]> findEmployeeBestSeller(Date from, Date to) throws RemoteException {
        return saleManagementDaoImplService.findEmployeeBestSeller(from, to);
    }

    public boolean updatePassword(String employeeId, String passNew) throws RemoteException {
        return accountDaoImplService.updatePassword(employeeId, passNew);
    }

    public List<DetailsBill> findDetailsBillByBillId(String id) throws RemoteException {
        return saleManagementDaoImplService.findDetailsBillByBillId(id);
    }

    public List<Bill> findBillByEmployee(String id) throws RemoteException {
        return saleManagementDaoImplService.findBillByEmployee(id);
    }

    public List<Bill> findBillByDate(LocalDate from, LocalDate to) throws RemoteException {
        return saleManagementDaoImplService.findBillByDate(from, to);
    }

    //ProductSale
    public List<ProductSale> getAllAProductSale() throws RemoteException {
        return productSaleDaoImplService.getAllAProductSale();
    }

    public boolean addProductSale(ProductSale productSale) throws RemoteException {
        return productSaleDaoImplService.addProductSale(productSale);
    }

    public boolean deleteProductSale(String id) throws RemoteException {
        return productSaleDaoImplService.deleteProductSale(id);
    }

    public ProductSale getProductSale(String productSaleId) throws RemoteException {
        return productSaleDaoImplService.getProductSale(productSaleId);
    }

    public boolean deleteProductSaleByPromotionId(String promotionId) throws RemoteException {
        return productSaleDaoImplService.deleteProductSaleByPromotionId(promotionId);
    }

    public List<ProductSale> getProductSaleByPromotionId(String productSaleId) throws RemoteException {
        return productSaleDaoImplService.getProductSaleByPromotionId(productSaleId);
    }

    public List<ProductSale> findProductSaleByText(String text) throws RemoteException {
        return productSaleDaoImplService.findProductSaleByText(text);
    }

    //product
    public Product getProductById(String id) throws RemoteException {
        return productDaoImplService.getById(id);
    }


    public List<Product> getProductByText(String text) throws RemoteException {
        return productDaoImplService.getProductByText(text);
    }

    public boolean updateProductQuantity(String id, int quantity) throws RemoteException {
        return productDaoImplService.updateProduct(id, quantity);
    }

    //Customer
    public List<Customer> getAllCustomers() throws RemoteException {
        return customerDaoImplService.getAllCustomers();
    }

    public boolean addCustomer(Customer customer) throws RemoteException {
        return customerDaoImplService.addCustomer(customer);
    }

    public boolean updateCustomer(Customer customer) throws RemoteException {
        return customerDaoImplService.updateCustomer(customer);
    }

    public boolean deleteCustomer(String id) throws RemoteException {
        return customerDaoImplService.deleteCustomer(id);
    }

    public List<Customer> findCustomerByText(String text) throws RemoteException {
        return customerDaoImplService.findCustomerByText(text);
    }

    public Customer findCustomerById(String id) throws RemoteException {
        return customerDaoImplService.findCustomerById(id);
    }

    //Promotion
    public List<Promotion> getAllPromotion() throws RemoteException {
        return promotionDaoImplService.getAllPromotion();
    }

    public boolean addPromotion(Promotion promotion) throws RemoteException {
        return promotionDaoImplService.addPromotion(promotion);
    }

    public boolean deletePromotion(String id) throws RemoteException {
        return promotionDaoImplService.deletePromotion(id);
    }

    public Promotion findPromotionById(String id) throws RemoteException {
        return promotionDaoImplService.findPromotionById(id);
    }

    public List<Promotion> findPromotionByText(String text) throws RemoteException {
        return promotionDaoImplService.findPromotionByText(text);
    }

    public boolean updatePromotion(Promotion promotion) throws RemoteException {
        return promotionDaoImplService.updatePromotion(promotion);
    }

    //Account
    public List<Account> getAllAccount() throws RemoteException {
        return accountDaoImplService.getAllAccount();
    }

    public boolean addAccount(Account account) throws RemoteException {
        return accountDaoImplService.addAccount(account);
    }

    public boolean updateAccount(Account account) throws RemoteException {
        return accountDaoImplService.updateAccount(account);
    }

    public boolean deleteAccount(String id) throws RemoteException {
        return accountDaoImplService.deleteAccount(id);
    }

    public Account getAccountById(String id) throws RemoteException {
        return accountDaoImplService.getAccountById(id);
    }

    public String findPasswordByEmployeeId(String employeeId) throws RemoteException {
        return accountDaoImplService.findPasswordByEmployeeId(employeeId);
    }

    //Role
    public List<Role> getAllRole() throws RemoteException {
        return roleDaoImplService.getAllRole();
    }

    public boolean addRole(Role role) throws RemoteException {
        return roleDaoImplService.addRole(role);
    }

    public boolean deleteRole(String id) throws RemoteException {
        return roleDaoImplService.deleteRole(id);
    }

    public Role findRoleByText(String text) throws RemoteException {
        return roleDaoImplService.findRoleByText(text);
    }

    public List<Role> getRolesByRoleCode(int roleCode) throws RemoteException {
        return roleDaoImplService.getRolesByRoleCode(roleCode);
    }

    public boolean updateRole(String id) throws RemoteException {
        return roleDaoImplService.updateRole(id);
    }

    //Employee
    public List<Employee> getAllEmployees() throws RemoteException {
        return employeeDaoImplService.getAllEmployees();
    }

    public boolean addEmployee(Employee employee) throws RemoteException {
        return employeeDaoImplService.addEmployee(employee);
    }

    public boolean updateEmployee(Employee employee) throws RemoteException {
        return employeeDaoImplService.updateEmployee(employee);
    }

    public boolean deleteEmployee(String id) throws RemoteException {
        return employeeDaoImplService.deleteEmployee(id);
    }

    public List<Employee> findEmployeeByText(String text) throws RemoteException {
        return employeeDaoImplService.findEmployeeByText(text);
    }

    public List<Employee> findEmployeeByRoleCode(int roleCode) throws RemoteException {
        return employeeDaoImplService.findEmployeeByRoleCode(roleCode);
    }

    public Employee findEmployeeById(String empId) throws RemoteException {
        return employeeDaoImplService.findEmployeeById(empId);
    }

    // author
    public List<Author> getAllAuthor() throws RemoteException {
        return authorDaoImplService.getAllAuthor();
    }

    public boolean addAuthor(Author author) throws RemoteException {
        return authorDaoImplService.addAuthor(author);
    }

    public boolean updateAuthor(Author author) throws RemoteException {
        return authorDaoImplService.updateAuthor(author);
    }

    public boolean deleteAuthor(String idAuthor) throws RemoteException {
        return authorDaoImplService.deleteAuthor(idAuthor);
    }

    public boolean checkIdAuthor(String idAuthor) throws RemoteException {
        return authorDaoImplService.checkIdAuthor(idAuthor);
    }

    public List<Author> getLatestAuthorID() throws RemoteException {
        return authorDaoImplService.getLatestAuthorID();
    }

    public boolean decreaseNumberOfBooks(String idAuthor) throws RemoteException {
        return authorDaoImplService.decreaseNumberOfBooks(idAuthor);
    }

    public boolean increaseNumberOfBooks(String idAuthor) throws RemoteException {
        return authorDaoImplService.increaseNumberOfBooks(idAuthor);
    }

    // category
    public List<Category> getAllCategory() throws RemoteException {
        return categoryDaoImplService.getAllCategory();
    }

    public boolean addCategory(Category category) throws RemoteException {
        return categoryDaoImplService.addCategory(category);
    }

    public boolean updateCategory(Category category) throws RemoteException {
        return categoryDaoImplService.updateCategory(category);
    }

    public boolean deleteCategory(String idCategory) throws RemoteException {
        return categoryDaoImplService.deleteCategory(idCategory);
    }

    public boolean checkIdCategory(String idCategory) throws RemoteException {
        return categoryDaoImplService.checkIdCategory(idCategory);
    }

    public List<String> getLatestCategoryID() throws RemoteException {
        return categoryDaoImplService.getLatestCategoryID();
    }

    public boolean decreaseNumberOfCategory(String idCategory) throws RemoteException {
        return categoryDaoImplService.decreaseNumberOfCategory(idCategory);
    }

    public boolean increaseNumberOfCategory(String idCategory) throws RemoteException {
        return categoryDaoImplService.increaseNumberOfCategory(idCategory);
    }

    // product type
    public List<ProductType> getAllProductType() throws RemoteException {
        return productTypeDaoImplService.getAllProductType();
    }

    public ProductType getProductTypeById(String id) throws RemoteException {
        return productTypeDaoImplService.getProductTypeById(id);
    }

    public boolean addProductType(ProductType productType) throws RemoteException {
        return productTypeDaoImplService.addProductType(productType);
    }

    public boolean updateProductType(ProductType productType) throws RemoteException {
        return productTypeDaoImplService.updateProductType(productType);
    }

    public boolean deleteProductType(String id) throws RemoteException {
        return productTypeDaoImplService.deleteProductType(id);
    }

    public List<String> getLastestProductType() throws RemoteException {
        return productTypeDaoImplService.getLastestProductType();
    }

    public boolean checkProductTypeExist(String id) throws RemoteException {
        return productTypeDaoImplService.checkProductTypeExist(id);
    }

    // supplier
    public List<Supplier> getAllSuppliers() throws RemoteException {
        return supplierDaoImplService.getAllSuppliers();
    }

    public boolean addSupplier(Supplier supplier) throws RemoteException {
        return supplierDaoImplService.addSupplier(supplier);
    }

    public boolean updateSupplier(Supplier supplier) throws RemoteException {
        return supplierDaoImplService.updateSupplier(supplier);
    }

    public boolean deleteSupplier(String id) throws RemoteException {
        return supplierDaoImplService.deleteSupplier(id);
    }

    public boolean checkSupplierId(String id) throws RemoteException {
        return supplierDaoImplService.checkSupplierId(id);
    }

    public List<Supplier> getLatestSupplierId() throws RemoteException {
        return supplierDaoImplService.getLatestSupplierId();
    }

    // book
    public List<Book> getAllBook() throws RemoteException {
        return bookDaoImplService.getAllBook();
    }

    public boolean addBook(Book book) throws RemoteException {
        return bookDaoImplService.addBook(book);
    }

    public boolean updateBook(Book book) throws RemoteException {
        return bookDaoImplService.updateBook(book);
    }

    public boolean deleteBook(String idBook) throws RemoteException {
        return bookDaoImplService.deleteBook(idBook);
    }

    public boolean checkIdBook(String idBook) throws RemoteException {
        return bookDaoImplService.checkIdBook(idBook);
    }

    public List<String> getLatestBookID() throws RemoteException {
        return bookDaoImplService.getLatestBookID();
    }

    public List<Book> loadComboBoxByProductType(String productTypeId) throws RemoteException {
        return bookDaoImplService.loadComboBoxByProductType(productTypeId);
    }

    public List<Book> loadComboBoxByPublisher(String publisherId) throws RemoteException {
        return bookDaoImplService.loadComboBoxByPublisher(publisherId);
    }

    public List<Book> loadComboBoxByAuthor(String authorId) throws RemoteException {
        return bookDaoImplService.loadComboBoxByAuthor(authorId);
    }

    public List<Book> loadComboBoxByCategory(String categoryId) throws RemoteException {
        return bookDaoImplService.loadComboBoxByCategory(categoryId);
    }

    public List<Book> searchBook(String search) throws RemoteException {
        return bookDaoImplService.searchBook(search);
    }


    public List<Book> findCategoryIdByName(String name) throws RemoteException {
        return bookDaoImplService.findCategoryIdByName(name);
    }

    public List<Book> findSupplierByName(String name) throws RemoteException {
        return bookDaoImplService.findSupplierByName(name);
    }

    public List<Book> findBookByProductType(String name) throws RemoteException {
        return bookDaoImplService.findBookByProductType(name);
    }

    public List<Book> findBookByAuthor(String name) throws RemoteException {
        return bookDaoImplService.findBookByAuthor(name);
    }


    // Merchandise
    public List<Merchandise> getAllMerchandise() throws RemoteException {
        return merchandiseDaoImplService.getAllMerchandise();
    }

    public boolean addMerchandise(Merchandise merchandise) throws RemoteException {
        return merchandiseDaoImplService.addMerchandise(merchandise);
    }

    public boolean checkIdExist(String id) throws RemoteException {
        return merchandiseDaoImplService.checkIdExist(id);
    }

    public boolean updateMerchandise(Merchandise merchandise) throws RemoteException {
        return merchandiseDaoImplService.updateMerchandise(merchandise);
    }

    public boolean deleteMerchandise(String id) throws RemoteException {
        return merchandiseDaoImplService.deleteMerchandise(id);
    }

    public List<Merchandise> findBookByProductTypeMerchandise(String name) throws RemoteException {
        return merchandiseDaoImplService.findByProductTypeMerchandise(name);
    }

    public List<Merchandise> findSupplierByNameMerchandise(String name) throws RemoteException {
        return merchandiseDaoImplService.findSupplierByNameMerchandise(name);
    }

    @Override
    public void notifyServer(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public boolean findBillExist(Date dateFrom, Date dateTo) throws RemoteException {
        return saleManagementDaoImplService.findBillExist(dateFrom, dateTo);
    }

    @Override
    public List<Object[]> dialogThongNhanVien(Date dateFrom, Date dateTo) throws RemoteException {
        return saleManagementDaoImplService.dialogThongNhanVien(dateFrom, dateTo);
    }

    @Override
    public List<Object[]> dialogLoiNhuanDoanhThu(Date dateFrom, Date dateTo) throws RemoteException {
        return saleManagementDaoImplService.dialogLoiNhuanDoanhThu(dateFrom, dateTo);
    }
}
