package faheem.microservices.consumeapi;

import faheem.microservices.bookapi.jdbcRepository.BookJdbcRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.awt.print.Pageable;

@Slf4j
@SpringBootTest
public class PageableTest {

    @Autowired
    private BookJdbcRepository bookJdbcRepository;

    @Test
    public void shouldDoPagination(){

        }

}
