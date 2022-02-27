package program.dto.image;

import lombok.Data;
import program.entities.Book;
import program.entities.Image;

import java.util.Set;

@Data
public class ImageAddDto {
    private String base64;
    private int bookId;
}
