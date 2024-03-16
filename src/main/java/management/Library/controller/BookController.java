package management.Library.controller;

import lombok.RequiredArgsConstructor;
import management.Library.domain.Book;
import management.Library.domain.BookEntity;
import management.Library.service.BookService;
import org.apache.coyote.Response;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookController {
    @Autowired
    private final BookService bookService;
    @PutMapping("/book/{id}")
    public ResponseEntity<Book> createBook(@PathVariable final String id, @RequestBody final Book book){
        bookService.create(book);
        ResponseEntity<Book> toReturn = new ResponseEntity<Book>(book, HttpStatus.CREATED);
        return toReturn;
    }
}
