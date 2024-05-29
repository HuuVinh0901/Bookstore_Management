package models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Account")
public class Account implements Serializable {

    private static final long serialVersionUID = -4326764061025244984L;
    @Id
    @OneToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    private String password;
    private Date starDate;

    public Account(Employee employee, String password, Date starDate) {
        this.employee = employee;
        this.password = password;
        this.starDate = starDate;
    }

    public Account(String password, Date starDate) {
        this.password = password;
        this.starDate = starDate;
    }

    @Override
    public String toString() {
        return "Account [password=" + password + ", starDate=" + starDate + "]";
    }


}
