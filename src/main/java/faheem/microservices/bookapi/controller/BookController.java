package faheem.microservices.bookapi.controller;

import faheem.microservices.bookapi.model.Book;
import faheem.microservices.bookapi.model.BookJDBC;
import faheem.microservices.bookapi.model.PageInfo;
import faheem.microservices.bookapi.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
   public Book insertBook(@RequestBody Book book){
       log.info("inside insertBook() method in controller, Book : {}",book);
       return bookService.insertBook(book);
   }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Integer id){
       log.info("inside getBookId() method in controller. id passed : {}",id);
        return bookService.getBookById(id);
    }

    @GetMapping("/all")
    public List<Book> getAllBooks(){
        log.info("inside getAllBooks() method in controller");
        List<Book> books = bookService.getAllBooks();
        log.info("books retrieved from database are : {} ",books);
        return books;
    }

    @GetMapping("/name")
    public Book getBookByName(@RequestParam("name") String name){
        log.info("inside getBookByName() method in book-api");
        log.info("inside getBookByName() method. {}",name);
        return bookService.getBookByName(name);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Integer id){
        log.info("deleteBook() in controller is called...");
         bookService.deleteBook(id);
         log.info("deleted book with id succesfully {}",id);
    }
    @GetMapping("/data")
    public String getBookData(){
        return "data of BOOK-SERVICE running on port :"
                + environment.getProperty("local.server.port");
    }
    @GetMapping("/entity")
    public ResponseEntity<String> getEntityData(){
        return new ResponseEntity<String>(
                "hello from bookRestController",
                HttpStatus.OK
        );
    }


    // ************************ in the following endpoints JDBC template is called ******************************

    @GetMapping("/bookNamesJDBC")
    public List<String> getAllBookNamesJDBC(){
        return bookService.getAllBooksNamesJDBC();
    }

    @GetMapping("/booksJDBC")
    public List<BookJDBC> getAllBooksJDBC(){
        return bookService.getAllBooksJDBC();
    }

    @PostMapping("/insertBookJDBC")
    public BookJDBC insertBook(@RequestBody BookJDBC book){
        log.info("BookController.insertBook() method is called...");
        log.info("book to be inserted is : {}",book);
        return bookService.insertBook(book);
    }

    @DeleteMapping("/removeBookJDBC/{id}")
    public void removeBookJDBC(@PathVariable Integer id){
        log.info("BookController.removeBookJDBC() method is called...");
        bookService.removeBookJDBC(id);
    }

    @PutMapping("/updateBookJdbc")
    public void updatedBook(@RequestBody BookJDBC book){
        log.info("BookController.updatedBook() method is called...");
        bookService.updatedBook(book);
    }

    @GetMapping("/bookJdbc/{id}")
    public BookJDBC getBookByIdJDBC(@PathVariable Integer id){
        return bookService.getBookByIdJDBC(id);
    }

    @GetMapping(value ="/pageable")
    public PageInfo pageableExample(Pageable pageable){
         log.info("pageable :",pageable);
        return  bookService.getAllBooksJDBCPageable(pageable);
    }
}
