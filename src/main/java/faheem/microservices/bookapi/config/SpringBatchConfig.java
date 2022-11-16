package faheem.microservices.bookapi.config;

import faheem.microservices.bookapi.model.Book;

import faheem.microservices.bookapi.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BookRepository bookRepository;

    private List<Book> bookList = new ArrayList<>();



    @Bean
    public FlatFileItemReader<Book> reader() {
        log.info("reader bean");
        FlatFileItemReader<Book> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
        itemReader.setName("bookReader");
        itemReader.setLineMapper(lineMapper());
        log.info("i am in reader() method");
        return itemReader;
    }

    private LineMapper<Book> lineMapper() {

        DefaultLineMapper<Book> lineMapper = new DefaultLineMapper<>();

        return lineMapper;

    }

    @Bean
    public BookProcessor processor() {
        log.info("BookProcessor bean");
        return new BookProcessor();
    }

    @Bean
    public RepositoryItemWriter writer() {
        log.info("Writer bean");
        Book book = new Book();
        book.setBookName("newBook");
        book.setBookCost(10.0);
        book.setBookId(91);
       bookList.add(book);
       log.info("new book in writer() method :   {}",book);
       System.out.println("book list is : "+bookList);
        RepositoryItemWriter<Book> writer = new RepositoryItemWriter<>();
        writer.setRepository(bookRepository);
        return writer;
    }

    @Bean
    public Step step1() {
        log.info("step1 bean");
        log.info("i am in step()1 method!");
        return stepBuilderFactory.get("book-step").<Book, Book>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();

    }

    @Bean //job for books
    public Job job() {
        log.info("job bean");
        log.info("i am in job() method!");
        return jobBuilderFactory.get("BookJob")//name of the job
                .flow(step1())
                .end()
                .build();

    }



}
