package program.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import program.entities.Image;

import java.util.List;



public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByName(String name);
}
