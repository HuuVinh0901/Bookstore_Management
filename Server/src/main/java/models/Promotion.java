package models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.SaleTypeEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Promotion")
public class Promotion implements Serializable {
    private static final long serialVersionUID = 7283124247038893245L;
    @Id
    @Column(name = "promotionId", nullable = false)
    private String promotionId;
    @Column(name = "promotionName", nullable = false, columnDefinition = "NVARCHAR(255)")
    private String promotionName;
    @Column(name = "promotionDiscount", columnDefinition = "NVARCHAR(255)")
    private String promotionDiscount;
    private LocalDate promotionStartDate;
    private LocalDate promotionEndDate;
    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL)
    private List<ProductSale> productSales;

    public Promotion(String promotionId) {
        this.promotionId = promotionId;
    }

    public Promotion(String promotionId, String promotionName, String promotionDiscount, LocalDate promotionStartDate, LocalDate promotionEndDate) {
        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.promotionDiscount = promotionDiscount;
        this.promotionStartDate = promotionStartDate;
        this.promotionEndDate = promotionEndDate;

    }

    @Override
    public String toString() {
        return "Promotion{" +
                "promotionId='" + promotionId + '\'' +
                ", promotionName='" + promotionName + '\'' +
                ", promotionDiscount='" + promotionDiscount + '\'' +
                '}';
    }
}