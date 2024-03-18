package management.Library;

import management.Library.domain.Book;
import management.Library.domain.BookEntity;
import management.Library.repository.BookRepository;
import management.Library.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository repo;
    @InjectMocks
    private BookService service;

    @Test
    public void testBookIsSaved(){
        final Book testBook = Book.builder().id("123").author("Varun").title("mastering life").build();
        final BookEntity testBookEntity = BookEntity.builder().id("123").author("Varun").title("mastering life").build();
        when(repo.save(eq(testBookEntity))).thenReturn(testBookEntity);
        final Book result = service.create(testBook);
        assertEquals(result,testBook);
    }
    @Test
    public void testFindByIdReturnsEmpty(){
        final String id = "12345";
        when(repo.findById(eq(id))).thenReturn(Optional.empty());
        final Optional<Book> result = service.findById(id);
        assertEquals(Optional.empty(),result);
    }
    @Test
    public void testFindByIdReturnsId(){
        final Book testBook = Book.builder().id("123").author("Varun").title("mastering life").build();
        final BookEntity testBookEntity = BookEntity.builder().id("123").author("Varun").title("mastering life").build();

        when(repo.findById(eq(testBook.getId()))).thenReturn(Optional.of(testBookEntity));
        final Optional<Book> result =service.findById(testBook.getId());
        assertEquals(Optional.of(testBook),result);
    }
    @Test
    public void getAllBooks(){
        final Book testBook = Book.builder().id("123").author("Varun").title("mastering life").build();
        final BookEntity testBookEntity = BookEntity.builder().id("123").author("Varun").title("mastering life").build();
        final BookEntity testBookEntity2 = BookEntity.builder().id("456").author("Alice").title("java programming").build();
        when(repo.findAll()).thenReturn(List.of(testBookEntity,testBookEntity2));
        final List<Book> allBooks = service.getAllBooks();
        assertEquals(allBooks.size(),2);
    }
    @Test
    public void bookExist(){
        final Book testBook = Book.builder().id("123").author("Varun").title("mastering life").build();
        final BookEntity testBookEntity = BookEntity.builder().id("123").author("Varun").title("mastering life").build();
        when(repo.existsById(testBookEntity.getId())).thenReturn(true);
        final boolean result = service.isBookExist(testBook);
        assertEquals(result,true);
    }
    // test required for delete,
}
