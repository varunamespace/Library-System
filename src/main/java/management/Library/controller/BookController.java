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
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BookController {
    @Autowired
    private final BookService bookService;
    @GetMapping("/book/index")
    public String index(){
        return "Welcome to Library ";
    }
    @PutMapping("/book/{id}")
    public ResponseEntity<Book> updateOrCreateBook(@PathVariable final String id, @RequestBody final Book book){
        book.setId(id);
        final boolean bookExists = bookService.isBookExist(book);
        bookService.create(book);
        if(bookExists){
            return new ResponseEntity<Book>(book,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Book>(book, HttpStatus.CREATED);
        }
    }
    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable final String id){
        final Optional<Book> found = bookService.findById(id);
        return found.map(book -> new ResponseEntity<Book>(book,HttpStatus.OK))
                .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/book/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        ResponseEntity<List<Book>> allBooks = new ResponseEntity<List<Book>>(bookService.getAllBooks(),HttpStatus.OK);
        return allBooks;
    }
    @DeleteMapping("/book/{id}")
    public ResponseEntity deleteBook(@PathVariable final String id){
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
