package faheem.microservices.bookapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookJDBC {

    private Integer bookId;
    private String bookName;
    private Double bookCost;
}
