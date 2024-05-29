package models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Employee")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1931163622794101414L;
    @Id
    @Column(name = "employeeId", nullable = false)
    protected String idEmployee;
    @Column(name = "employeeName", columnDefinition = "NVARCHAR(255)")
    protected String name;
    protected String phone;
    @Column(name = "address", columnDefinition = "NVARCHAR(255)")
    protected String address;
    protected String email;
    protected Date birth;

    @Column(name = "gender", columnDefinition = "NVARCHAR(5)")
    protected String gender;
    @Column(name = "status", columnDefinition = "NVARCHAR(20)")
    protected String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    protected Role role;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Bill> bill;

    public Employee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Employee(String idEmployee, String name, String phone, String address, String email, Date birth, String gender, String status, Role role) {
        this.idEmployee = idEmployee;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
        this.status = status;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "idEmployee='" + idEmployee + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", gender='" + gender + '\'' +
                ", status=" + status +
                '}';
    }
}
