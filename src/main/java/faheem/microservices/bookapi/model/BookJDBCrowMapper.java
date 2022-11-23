package faheem.microservices.bookapi.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class BookJDBCrowMapper implements RowMapper<BookJDBC> {
    @Override
    public BookJDBC mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.info("mapRow() method is called...");
        BookJDBC book = new BookJDBC();
        book.setBookId(rs.getInt("book_id"));
        book.setBookName(rs.getString("book_name"));
        book.setBookCost(rs.getDouble("book_cost"));
        log.info("book data : {}",book);
        return book;
    }
}
