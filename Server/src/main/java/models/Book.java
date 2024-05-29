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

import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "Book")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class Book extends Product implements Serializable {
    private static final long serialVersionUID = -3872235661275604880L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId")
    private Author authorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category categoryId;
    private LocalDate publicationYear;
    private String ISBN;
    private int numberOfPages;
    private int quantitySold;
    private double revenue;
    private double profit;

    public Book(String productId) {
        super(productId);
    }

    @Override
    public double tax() {
        return super.importPrice * 0.05;
    }

    @Override
    public double sellingPrice() {
        return super.importPrice + (super.importPrice * 0.55) + tax();
    }
}