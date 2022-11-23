package faheem.microservices.bookapi.jdbcRepository;

import faheem.microservices.bookapi.model.BookJDBC;
import faheem.microservices.bookapi.model.BookJDBCrowMapper;
import faheem.microservices.bookapi.model.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    public BookJDBC getBookByIdJDBC(Integer bookId){
        log.info("getAllBookNamesJDBC.getBookByIdJDBC() method is called. book is passed is : {}",bookId);
        String selectSql = "select * from book where book_id = ?";
        try {
            return jdbcTemplate.queryForObject(selectSql, new Object[]{bookId}, new BookJDBCrowMapper());
        }
        catch (EmptyResultDataAccessException e){
            log.error("book is not present with this id : {}",bookId);
            log.error("Exception Message : {}",e.getMessage());
        }
        return null;
    }
    public List<String> getAllBookNamesJDBC(){
        log.info("getAllBookNamesJDBC() method is called...");
        List<String> allBookNames = new ArrayList<>();
        String query = "select book_name from book ";
            allBookNames.addAll(jdbcTemplate.queryForList(query, String.class));
            log.info("all books names are : {} ", allBookNames);
        return allBookNames;
    }

    @Transactional()
        public List<BookJDBC> getAllBooksJDBC(){
        log.info("getAllBooksJDBC() method is called...");
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

    public void removeBookJDBC(int bookId){
        log.info("BookJdbcRepository.removeBookJDBC() method is  called...");
        log.info("deleting book with id : {}",bookId);
        String deleteSql = "DELETE FROM book WHERE book_id=" +bookId;
        log.info("the query is : {}",deleteSql);
        jdbcTemplate.update(deleteSql);

        log.info("book deleted successfully. {}" ,bookId);

    }

    public void updateBook(BookJDBC book){
        log.info("BookJdbcRepository.updateBook() method is called... {}",book);
        String updateSql = "UPDATE book SET book_name = ? where book_id = ?";
        jdbcTemplate.update(updateSql,book.getBookName(),book.getBookId());
        log.info("update query is : {}",updateSql);
        log.info("updated book successfully!");
    }

    @Transactional()
    public PageInfo getAllBooksJDBCPageable(Pageable pageable) {
        log.info("getAllBooksJDBC() method is called...");
        PageInfo pageInfo = new PageInfo();
        List<BookJDBC> books = new ArrayList<>();
        //String query = "select * from book ";
        String query = "select * from book LIMIT "+pageable.getPageNumber()+","+pageable.getPageSize();

         pageInfo.setBookList(jdbcTemplate.query(query,new BookJDBCrowMapper()));
         pageInfo.setPageNumber(pageable.getPageNumber()); //starting element
         pageInfo.setPageSize(pageable.getPageSize());
         String totalItemsSql = "select * from book";
         int totallNumberElements = jdbcTemplate.query(totalItemsSql,new BookJDBCrowMapper()).size();
        pageInfo.setTotaleElements(totallNumberElements);
        int totalPages = totallNumberElements/ pageInfo.getPageSize();
        pageInfo.setTotalPages(totalPages);

     return pageInfo;
    }

}
