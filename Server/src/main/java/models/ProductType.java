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

import java.io.Serializable;
import java.util.List;

@Table(name = "ProductType")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductType implements Serializable {
    private static final long serialVersionUID = -6502313296453212087L;
    @Id
    private String productTypeId;
    @Column(name = "productTypeName", columnDefinition = "NVARCHAR(255)")
    private String productTypeName;
    @OneToMany(mappedBy = "productTypeId", cascade = CascadeType.ALL)
    private List<Product> products;

    public ProductType(String productTypeId, String productTypeName) {
        this.productTypeId = productTypeId;
        this.productTypeName = productTypeName;
    }

    public ProductType(String productTypeId) {
        this.productTypeId = productTypeId;
    }
}
