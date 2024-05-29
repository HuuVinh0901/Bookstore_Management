package models;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Bill")
@Inheritance(strategy = InheritanceType.JOINED)
public class Bill implements Serializable {
    private static final long serialVersionUID = -3867076602662839096L;
    @Id
    @Column(name = "billId", nullable = false)
    private String billId;
    @Column(name = "billDate")
    @Temporal(TemporalType.DATE)
    private LocalDate billDate;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId")
    private Employee employee;
    @EqualsAndHashCode.Exclude
    private double amountReceived;
    @EqualsAndHashCode.Exclude
    private double amounttotal;
    @EqualsAndHashCode.Exclude
    private double profitTotal;
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private Set<DetailsBill> detailsBills;

    public Bill(String billId, LocalDate billDate, Customer customer, Employee employee, double amountReceived, double amounttotal, double profitTotal) {
        this.billId = billId;
        this.billDate = billDate;
        this.customer = customer;
        this.employee = employee;
        this.amountReceived = amountReceived;
        this.amounttotal = amounttotal;
        this.profitTotal = profitTotal;
    }

    public Bill(String billId) {
        super();
        this.billId = billId;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billId='" + billId + '\'' +
                ", billDate=" + billDate +
                ", customer=" + customer +
                ", employee=" + employee +
                ", amountReceived=" + amountReceived +
                ", amounttotal=" + amounttotal +
                ", profitTotal=" + profitTotal +
                '}';
    }
}
