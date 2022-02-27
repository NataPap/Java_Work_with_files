package program.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import program.dto.author.AuthorAddDto;
import program.dto.author.AuthorDto;
import program.dto.book.BookAddDto;
import program.dto.book.BookDto;
import program.dto.image.ImageAddDto;
import program.dto.image.ImageDto;
import program.entities.Author;
import program.entities.Book;
import program.entities.Image;
import program.mapper.ApplicationMapper;
import program.repositories.AuthorRepository;
import program.repositories.BookRepository;
import program.repositories.ImageRepository;
import program.storage.StorageService;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;




@RestController
@RequestMapping
//@RequiredArgsConstructor
public class HomeController {
    private final AuthorRepository authorRepository;
    private final ApplicationMapper applicationMapper;
    private final StorageService storageService;
    private final BookRepository bookRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public HomeController(AuthorRepository authorRepository, ApplicationMapper applicationMapper,
                          StorageService storageService, BookRepository bookRepository, ImageRepository imageRepository) {
        this.authorRepository = authorRepository;
        this.applicationMapper = applicationMapper;
        this.storageService = storageService;
        this.bookRepository = bookRepository;
        this.imageRepository = imageRepository;
    }
//отримання всіх авторів
    @GetMapping("/")
    public List<AuthorDto> index() {
       return  applicationMapper
               .ListAuthorByListAuthorDto(authorRepository.findAll());
    }

//додавання автора
    @PostMapping("/")
    public String create(AuthorAddDto model) {
        Author author = applicationMapper.AuthorByAddAuthorDto(model);
        String fileName=storageService.store(model.getImageBase64());
        author.setImage(fileName);
        authorRepository.save(author);
        return fileName;
    }

//отримання файлу за його ім'ям
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws Exception {

        Resource file = storageService.loadAsResource(filename);
        String urlFileName =  URLEncoder.encode("малишка.jpg", StandardCharsets.UTF_8.toString());
        return ResponseEntity.ok()
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
                .contentType(MediaType.IMAGE_JPEG)

                .header(HttpHeaders.CONTENT_DISPOSITION,"filename=\""+urlFileName+"\"")
                .body(file);
    }

    //отримання книжок
    @GetMapping("/books")
    public List<BookDto> list(){
        List<BookDto> bookDtos = applicationMapper.ListBookByListBookDto(bookRepository.findAll());

        for (BookDto bookDto:bookDtos) {
            Author author =  authorRepository.getById(bookDto.getAuthorId());
            bookDto.setAuthorId(author.getId());
            List<ImageDto> imageDtos = applicationMapper.ListImageByListImageDto(imageRepository.findAll());
            for (ImageDto imageDto:imageDtos) {
                try{
                    String filename = imageDto.getName();
                    String base64 = String.valueOf(storageService.load(filename));
                    imageDto.setName("data:uploaded/jpeg;base64,"+ base64);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return bookDtos;
    }

    //додати нову книжку в базу з колекцією фото.

    @PostMapping( "/addBook")
    public ResponseEntity create(@RequestBody BookAddDto add) throws IOException {

        Book book =new Book();
        book.setName(add.getName());
        Author author =  authorRepository.getById(add.getAuthorId());
        book.setAuthor(author);
        bookRepository.save(book);

        for (String name:add.getImages()) {
            List<Image> images = imageRepository.findByName(name);
            Image image = images.get(0);
            image.setBook(book);
            imageRepository.save(image);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //додавання image
    @PostMapping("/books/addImage")
    public String createImage(@RequestBody ImageAddDto model) {
        String fileName=storageService.store(model.getBase64());
        try {
            Image image = applicationMapper.ImageByAddImageDto(model);
            image.setName(fileName);
            Book book = bookRepository.getById(model.getBookId());
            image.setBook(book);
            imageRepository.save(image);
        }catch (Exception ex)
        {
            System.out.println("Error "+ ex.getMessage());
        }
        return fileName;
    }

}
