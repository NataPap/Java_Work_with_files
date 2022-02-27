package program.dto.image;

import lombok.Data;
import program.entities.Book;

@Data
public class ImageDto {
    private int id;
    private String name;
    private int bookId;;
}
