package faheem.microservices.bookapi;

import faheem.microservices.bookapi.jdbcRepository.BookJdbcRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BookApiApplication {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(BookApiApplication.class, args);
    }

    @Bean
    public BookJdbcRepository getBookJdbcRepositoryBean(){
        return new BookJdbcRepository();
    }

}
