package management.Library.service;

import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management.Library.domain.Book;
import management.Library.domain.BookEntity;
import management.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class BookService {
    @Autowired
    private final BookRepository repository;

    private BookEntity BookToBookEntity(Book book){
        return BookEntity.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle()).build();
    }
    private Book BookEntityToBook(BookEntity book){
        return Book.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle()).build();
    }
    public Book create(Book book){
        BookEntity newBookEntity = new BookEntity();
        newBookEntity = BookToBookEntity(book);
        repository.save(newBookEntity);
        return book;
    }
    public Optional<Book> findById(String id){
        final Optional<BookEntity> foundBook = repository.findById(id);
        return foundBook.map(book -> BookEntityToBook(book)); //if there is any object, then convert the object(bookEntity) to book
    }
    public List<Book> getAllBooks(){
        final List<BookEntity> foundBooks = repository.findAll();
        return foundBooks.parallelStream()
                .map(book -> BookEntityToBook(book))
                .collect(Collectors.toList());
    }
    public boolean isBookExist(Book book){
        return repository.existsById(book.getId());
    }
    public void delete(String id){
        try {
            repository.deleteById(id);
        } catch (final EmptyResultDataAccessException ex) {
            log.debug("Attempted to delete non-existing book", ex);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found", ex);
        }
    }
}
