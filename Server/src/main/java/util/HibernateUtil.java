/*
 * @ (#) .java   1.0    03/04/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */

package util;/*
 * @description:
 * @author:     Hoang Le
 * @date:       03/04/2024
 * @version:    1.0
 */

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class HibernateUtil {
    private static HibernateUtil instance = null;
    private EntityManager entityManager;

    public HibernateUtil() {
        entityManager = Persistence.createEntityManagerFactory("JPA_ORM_Bookstore MSSQL").createEntityManager();
    }

    public synchronized static HibernateUtil getInstance() {
        if (instance == null)
            instance = new HibernateUtil();
        return instance;

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
