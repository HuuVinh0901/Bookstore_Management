/*
 * @ (#) .java   1.0    03/04/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */

package models;/*
 * @description:
 * @author:     Hoang Le
 * @date:       03/04/2024
 * @version:    1.0
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.List;

@Table(name = "Supplier")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier implements Serializable {
    @Id
    private String supplierId;
    @Column(name = "supplierName", columnDefinition = "NVARCHAR(255)")
    private String supplierName;
    private String address;
    private String phoneNumber;
    @OneToMany(mappedBy = "supplierId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Product> products;

    public Supplier(String supplierId, String address, String phoneNumber, String supplierName) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Supplier(String supplierId) {
        this.supplierId = supplierId;
    }
}
