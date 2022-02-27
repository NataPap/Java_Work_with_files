package program.dto.book;

import lombok.Data;
import program.dto.image.ImageDto;


import java.util.List;


@Data
public class BookAddDto {
    private String name;
    private int authorId;
    private List<String> images;
}
