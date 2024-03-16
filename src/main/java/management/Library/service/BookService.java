package management.Library.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import management.Library.domain.Book;
import management.Library.domain.BookEntity;
import management.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class BookService {
    @Autowired
    private final BookRepository repository;
    public Book create(Book book){
        BookEntity newBook = new BookEntity();
        newBook = BookToBookEntity(book);
        repository.save(newBook);
        return book;
    }
    private BookEntity BookToBookEntity(Book book){
        return BookEntity.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle()).build();
    }

}
