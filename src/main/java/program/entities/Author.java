package program.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name="tbl_authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="full_name", length = 200, nullable = false)
    private String fullName;

    @Column(length = 200, nullable = true)
    private String image;

    @OneToMany(mappedBy="author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;
}
