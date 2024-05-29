package models;

import java.io.Serializable;
import java.util.HashSet;

import org.hibernate.mapping.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Role")
public class Role implements Serializable {
    private static final long serialVersionUID = 6878299406684161524L;
    @Id
    @Column(name = "roleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRole;
    @Column(name = "roleName", columnDefinition = "NVARCHAR(255)", unique = true)
    private String roleName;

    @Column(name = "roleCode")
    private int roleCode;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleName, int roleCode) {
        this.roleName = roleName;
        this.roleCode = roleCode;
    }

    public Role(int idRole) {
        this.idRole = idRole;
    }
}
