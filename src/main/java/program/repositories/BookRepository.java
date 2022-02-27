package program.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import program.entities.Book;


import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List <Book> findByName(String name);
}
