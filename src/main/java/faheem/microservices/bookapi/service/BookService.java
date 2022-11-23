package faheem.microservices.bookapi.service;

import faheem.microservices.bookapi.jdbcRepository.BookJdbcRepository;
import faheem.microservices.bookapi.model.Book;
import faheem.microservices.bookapi.model.BookJDBC;
import faheem.microservices.bookapi.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookJdbcRepository bookJdbcRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(Integer bookId){
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isPresent()) {
            return optionalBook.get();
        }
            else {
               log.error("book with this id is not present {} ",bookId);
            throw new NoSuchElementException("Book not found!");
        }
    }

    public Book insertBook(Book newBook){
        return bookRepository.save(newBook);
    }

    public Book getBookByName(String name){
        Book book = bookRepository.findByBookName(name);
        if(book!=null) {
            return book;
        }
        else {
            log.info("book with this name is not present {} ",name);
            throw new NoSuchElementException("book with this book name not found!");
        }
    }

    public void deleteBook(Integer bookId) {
        log.info("deleteBook() method in service called...");
        bookRepository.deleteById(bookId);
    }
  // ******************************* JDBC ********************************************

    public List<String> getAllBooksNamesJDBC(){
       return bookJdbcRepository.getAllBookNamesJDBC();
    }

    public List<BookJDBC> getAllBooksJDBC(){
        return bookJdbcRepository.getAllBooksJDBC();
    }

    public BookJDBC insertBook(BookJDBC book){
        log.info("BookService.insertBook() method is called...");
        return bookJdbcRepository.insertBookJDBC(book);
    }
}
