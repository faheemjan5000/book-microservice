package faheem.microservices.bookapi.controller;

import faheem.microservices.bookapi.model.Book;
import faheem.microservices.bookapi.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
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
}
