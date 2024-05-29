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
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.List;

@Table(name = "Category")
@Entity
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class Category implements Serializable {
    private static final long serialVersionUID = 8443636018907976118L;
    @Id
    private String idCategory;
    @Column(name = "categoryName", columnDefinition = "NVARCHAR(255)")
    private String categoryName;
    private int bookQuantity;
    private String description;
    @OneToMany(mappedBy = "categoryId")
    private List<Book> books;

    public Category(String idCategory) {
        this.idCategory = idCategory;
    }

    public Category() {

    }

    public Category(String idCategory, String categoryName, int bookQuantity, String description) {
        this.idCategory = idCategory;
        this.categoryName = categoryName;
        this.bookQuantity = bookQuantity;
        this.description = description;

    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory='" + idCategory + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", bookQuantity=" + bookQuantity +
                ", description='" + description + '\'' +
                '}';
    }
}
