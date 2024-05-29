package models;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import util.GenderEnum;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = -4196108436804202396L;
    @Id
    @Column(name = "customerId", nullable = false, columnDefinition = "NVARCHAR(255)", unique = true)
    private String idCustomer;
    @Column(name = "customerName", columnDefinition = "NVARCHAR(255)")
    private String name;
    @Column(name = "phone")
    private String phone;
    private String email;
    @Column(name = "address", columnDefinition = "NVARCHAR(255)")
    private String address;
    @Column(name = "gender", columnDefinition = "NVARCHAR(5)")
    private String gender;
    private Date birth;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Bill> bill;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<BillPending> billPendings;

    public Customer(String idCustomer, String name, String phone, String email, String address, String gender, Date birth) {
        this.birth = birth;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.idCustomer = idCustomer;
    }

    @Override
    public String toString() {
        return "Customer [idCustomer=" + idCustomer + ", name=" + name + ", phone=" + phone + ", email=" + email
                + ", address=" + address + ", gender=" + gender + ", birth=" + birth + "]";
    }

    public Customer(String idCustomer) {
        super();
        this.idCustomer = idCustomer;
    }


}