package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ui.Model;

import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "DetailsBill")
public class DetailsBill implements Serializable {
    private static final long serialVersionUID = 751186654795557261L;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billId")
    private Bill bill;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    private int quantity;
    private double price;
    private double total;

    public DetailsBill(Bill bill, Product product, int quantity, double price, double total) {
        this.bill = bill;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }
}