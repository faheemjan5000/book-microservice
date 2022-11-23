package faheem.microservices.bookapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {

    private int pageSize;
    private int pageNumber;
    private int totaleElements;
    private int totalPages;
    List<BookJDBC> bookList = new ArrayList<>();
}
