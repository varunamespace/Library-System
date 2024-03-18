package management.Library;

import com.fasterxml.jackson.databind.ObjectMapper;
import management.Library.domain.Book;
import management.Library.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private BookService service;

    @Test
    public void testBookIsSaved() throws Exception {
        final Book book = Book.builder().id("11").author("Varun").title("mastering life").build();
        final ObjectMapper obj = new ObjectMapper();
        final String bookJson = obj.writeValueAsString(book);

        mvc.perform(MockMvcRequestBuilders.put("/book/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.id").value(book.getId()));
    }
    @Test
    public void testBookIsUpdated() throws Exception {
        final Book book = Book.builder().id("11").author("Varun").title("mastering life").build();
        final ObjectMapper obj = new ObjectMapper();
        final String bookJson = obj.writeValueAsString(book);
        service.create(book);
        mvc.perform(MockMvcRequestBuilders.put("/book/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.id").value(book.getId()));
    }

    @Test
    public void testBookNotFound() throws Exception{
        final Book book = Book.builder().id("11").author("Varun").title("mastering life").build();
        service.create(book);
        mvc.perform(MockMvcRequestBuilders.get("/book/12345"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testBookFound() throws Exception{
        final Book book = Book.builder().id("11").author("Varun").title("mastering life").build();
        service.create(book);
        mvc.perform(MockMvcRequestBuilders.get("/book/"+book.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void allBooks() throws Exception{
        final Book book = Book.builder().id("11").author("Varun").title("mastering life").build();
        service.create(book);
        mvc.perform(MockMvcRequestBuilders.get("/book/books"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatHttp204IsReturnedWhenBookDoesntExist() throws Exception {
        mvc
                .perform(MockMvcRequestBuilders.delete("/book/21321213"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatHttp204IsReturnedWhenExistingBookIsDeleted() throws Exception {
        final Book book = Book.builder().id("11").author("Varun").title("mastering life").build();
        service.create(book);

        mvc
                .perform(MockMvcRequestBuilders.delete("/book/" + book.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
