/*
 * @ (#) .java   1.0    03/04/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */

package app;/*
 * @description:
 * @author:     Hoang Le
 * @date:       03/04/2024
 * @version:    1.0
 */

import controller.MainController;
import controller.MainControllerInterface;
import jakarta.persistence.EntityManager;
import lombok.SneakyThrows;
import models.*;
import util.HibernateUtil;
import views.LoginView;

import java.awt.*;

public class Main {
    private MainControllerInterface maincontroller;

    public static void main(String[] args) {
        EntityManager em = null;
        try {
            HibernateUtil hibernateUtil = HibernateUtil.getInstance();
            em = hibernateUtil.getEntityManager();
            em.getTransaction().begin();
            Author author = new Author();
            Category category = new Category();
            ProductType productType = new ProductType();
            Merchandise merchandise = new Merchandise();
            Supplier supplier = new Supplier();
            Book book = new Book();
            Bill bill = new Bill();
            Customer customer = new Customer();
            Employee employee = new Employee();
            Account account = new Account();
            Role role = new Role();
            Promotion promotion = new Promotion();
            DetailsBill detailsBill = new DetailsBill();
            if (em.contains(author) || em.contains(category) || em.contains(productType) || em.contains(merchandise) || em.contains(supplier) || em.contains(book) || em.contains(bill) || em.contains(customer) || em.contains(employee) || em.contains(account) || em.contains(role) || em.contains(promotion) || em.contains(detailsBill)) {
                em.merge(author);
                em.merge(category);
                em.merge(productType);
                em.merge(merchandise);
                em.merge(supplier);
                em.merge(book);
                em.merge(bill);
                em.merge(customer);
                em.merge(employee);
                em.merge(account);
                em.merge(role);
                em.merge(promotion);
                em.merge(detailsBill);
                em.getTransaction().commit();
            } else {
                em.persist(author);
                em.persist(category);
                em.persist(productType);
                em.persist(merchandise);
                em.persist(supplier);
                em.persist(book);
                em.persist(bill);
                em.persist(customer);
                em.persist(employee);
                em.persist(account);
                em.persist(role);
                em.persist(promotion);
                em.persist(detailsBill);
                em.getTransaction().commit();
            }
        } catch (
                Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        EventQueue.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                MainControllerInterface mainController = new MainController();
                LoginView view = new LoginView(mainController);
                view.setVisible(true);
            }
        });
    }
}