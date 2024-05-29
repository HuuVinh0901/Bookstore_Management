package dao.impl;


import controller.MainController;
import dao.CategoryDao;
import dao.ProductSaleDao;
import lombok.SneakyThrows;
import models.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import util.log.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryDaoImplTest {

    private MainController mainController;
    private ProductSaleDao productSaleDao;
    @SneakyThrows
    @BeforeAll
    void setUp() {
        mainController = new MainController();
//        productSaleDao = new ProductSaleDaoImpl();
    }
    @SneakyThrows
    @Test
    void getProductSales() {


        String merchandises = mainController.findPasswordByEmployeeId("E20240418183024");
        System.out.println(merchandises);
    }

    @AfterAll
    void tearDown() {
        mainController = null;

    }


}
