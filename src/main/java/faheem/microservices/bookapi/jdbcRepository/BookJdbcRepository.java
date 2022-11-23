package faheem.microservices.bookapi.jdbcRepository;

import faheem.microservices.bookapi.model.BookJDBC;
import faheem.microservices.bookapi.model.BookJDBCrowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class BookJdbcRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> getAllBookNamesJDBC(){
        log.info("getAllBookNamesJDBC() method is called...");
        List<String> allBookNames = new ArrayList<>();
        String query = "select book_name from book ";
        allBookNames.addAll(jdbcTemplate.queryForList(query,String.class));
        log.info("all books names are : {} ",allBookNames);
        return allBookNames;
    }

    @Transactional()
        public List<BookJDBC> getAllBooksJDBC(){
        log.info("getAllBooksJDBC() method is called...");
        List<BookJDBC> books = new ArrayList<>();
        String query = "select * from book ";

      return jdbcTemplate.query(query,new BookJDBCrowMapper());

    }

     //update method is used to insert a record in database.
    public BookJDBC insertBookJDBC(BookJDBC book){
        log.info("BookJdbcRepository.insertBookJDBC() method is  called...");
        String insertSql = "INSERT INTO book" + " (book_id,book_name,book_cost)"+"VALUES(?,?,?)";
        //jdbcTemplate.update(query);
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, book.getBookId());
            preparedStatement.setString(2, book.getBookName());
            preparedStatement.setDouble(3, book.getBookCost());
            return preparedStatement;
        });
        log.info("book inserted successfully. {}" ,book);
        return book;
    }
}
