package program.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.nio.file.Path;
import java.util.stream.Stream;
// створюємо інтерфейс для завантаження, збереження та забезпечення подальшої роботи з файлами
public interface StorageService {
    void init(); // метод ініціалізації папки - створює папку для зберігання файлів
    void store(MultipartFile file); // метод завантаження файла з використанням інтерфейсу MultipartFile
    Stream<Path> loadAll(); // метод відображення усіх файлів в папці
    Path load(String filename); // метод повернення шляху файла за його ім'ям
    String store(String base64);//метод зберігання файла та повернення його ім'я
    Resource loadAsResource(String filename);// метод отримання ресурсів запитуваного файлу
    void deleteAll(); // метод видалення всіх файлів
}