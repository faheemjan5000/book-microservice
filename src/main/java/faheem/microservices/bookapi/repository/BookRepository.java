package faheem.microservices.bookapi.repository;

import faheem.microservices.bookapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    Book findByBookName(String name);

}
