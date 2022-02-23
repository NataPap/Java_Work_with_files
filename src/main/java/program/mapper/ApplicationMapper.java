package program.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import program.dto.author.AuthorAddDto;
import program.dto.author.AuthorDto;
import program.dto.book.BookAddDto;
import program.dto.book.BookDto;
import program.dto.image.ImageAddDto;
import program.dto.image.ImageDto;
import program.entities.Author;
import program.entities.Book;
import program.entities.Image;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    @Mapping(source = "fullName", target = "name")

    AuthorDto AuthorByAuthorDto(Author author);
    List<AuthorDto> ListAuthorByListAuthorDto(List<Author> authors);
    Author AuthorByAddAuthorDto(AuthorAddDto dto);
@Mapping(source = "name", target = "name")
    BookDto BookByBookDto (Book book);
    List<BookDto> ListBookByListBookDto (List<Book> books);
    //Book BookByAddBookDto(BookAddDto dto);
    @Mapping(source = "name", target = "name")
    ImageDto ImageByImageDto (Image image);
    List<ImageDto> ListImageByListImageDto(List<Image> images);
    Image ImageByAddImageDto (ImageAddDto dto);

}
