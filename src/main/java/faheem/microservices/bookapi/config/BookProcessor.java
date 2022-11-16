package faheem.microservices.bookapi.config;

import faheem.microservices.bookapi.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class BookProcessor implements ItemProcessor<Book,Book> {

    @Override
    public Book process(Book book) {
        log.info("i am in BookProcessor.process() method! ");
        log.info("processing the Book item...");
            log.info("Replacing Math with Algebra!");
            book.setBookName("Algebra");
            book.setBookCost(95.0);


        return book;

    }
}
