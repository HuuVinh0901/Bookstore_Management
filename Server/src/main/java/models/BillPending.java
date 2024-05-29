package models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BillPending")
public class BillPending implements Serializable {
    private static final long serialVersionUID = -4156963763725866835L;
    @Id
    @Column(name = "billPendingId", nullable = false, unique = true)
    private String billPendingId;
    private String billId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;
    @Column(name = "ngayLap")
    private Date ngayLap;
    @OneToMany(mappedBy = "billPending", cascade = CascadeType.ALL)
    private Set<DetailsBillPending> detailsBillPendings;

    public BillPending(String billPendingId, String billId, Customer customer, Date ngayLap) {
        this.billPendingId = billPendingId;
        this.billId = billId;
        this.customer = customer;
        this.ngayLap = ngayLap;
    }

    @Override
    public String toString() {
        return "BillPending{" +
                "billPendingId='" + billPendingId + '\'' +
                ", billId='" + billId + '\'' +
                ", customer=" + customer +
                ", ngayLap=" + ngayLap +
                ", detailsBillPendings=" + detailsBillPendings +
                '}';
    }
}
