package models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ProductSale")
public class ProductSale implements Serializable {
    private static final long serialVersionUID = -8099945809285369885L;
    @Id
    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotionId")
    private Promotion promotion;
    @Column(name = "description", columnDefinition = "NVARCHAR(255)")
    private String description;
    private double giaBan;

    public ProductSale(Product product, Promotion promotion) {
        this.product = product;
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return "ProductSale{" +
                "product=" + product +
                ", promotion=" + promotion +
                ", description='" + description + '\'' +
                ", giaBan=" + giaBan +
                '}';
    }
}
