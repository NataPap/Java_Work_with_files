package program.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tbl_images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 200, nullable = false)
    private String name;

    @ManyToOne()
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

}
