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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Table(name = "Author")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author implements Serializable {
    private static final long serialVersionUID = -1504106167793275087L;
    @Id
    private String authorId;
    @Column(name = "authorName", columnDefinition = "NVARCHAR(255)")
    private String authorName;
    private LocalDate date;
    private int numberOfWorks;
    @OneToMany(mappedBy = "authorId")
    @ToString.Exclude
    private List<Book> books;

    public Author(String authorId, String authorName, LocalDate date) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.date = date;
    }

    public Author(String authorId) {
        this.authorId = authorId;
    }

}


