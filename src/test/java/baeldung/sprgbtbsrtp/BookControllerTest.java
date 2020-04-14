package baeldung.sprgbtbsrtp;

// import static org.hamcrest.MatcherAssert.assertThat;
import baeldung.sprgbtbsrtp.persistency.DummyBookRepository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import baeldung.sprgbtbsrtp.components.Book;
import baeldung.sprgbtbsrtp.components.BookController;
import baeldung.sprgbtbsrtp.persistency.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * @WebMvcTest
 * Using this annotation will disable full auto-configuration and instead apply only configuration relevant to MVC tests
 * (i.e. * @Controller, @ControllerAdvice, @JsonComponent, Converter/GenericConverter, Filter, WebMvcConfigurer and
 * HandlerMethodArgumentResolver beans but not @Component, @Service or @Repository beans).
 *
 * https://spring.io/guides/gs/testing-web/  :
 * In this test (@WebMvcTest), the full Spring application context is started but without the server.
 * We can narrow the tests to only the web layer by using @WebMvcTest( controllers =  ...)
 * Or else we need to include or mock away all beans any controller depends on.
 *
 * controllers :
 * If we leave away the controllers parameter, Spring Boot will include all controllers in the application context.
 * Thus, we need to include or mock away all beans any controller depends on.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * https://spring.io/guides/gs/testing-web/ - itt a restAssured & Co. nem uzemelt be
 */

// @RunWith( SpringRunner.class)         // JUnit 4.x ezt nem talalja ... helyette :
// @ExtendWith( SpringExtension.class)   // JUnit 5 Jupiter allitolag nem kell mar, mert benne van a @DataJpaTest/@WebMvcTest/@SpringBootTest-ban
// @WebMvcTest( controllers = BookController.class) // spring-boot-test

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest
{
  // Spring Boot automatically provides beans like an @ObjectMapper to map to and from JSON
  // and a MockMvc instance to simulate HTTP requests.
  @Autowired
  private ObjectMapper objectMapper;

  // instance to simulate HTTP requests
  @Autowired
  private MockMvc mockMvc; // null

  @InjectMocks // l. meg : https://howtodoinjava.com/mockito/mockito-mock-injectmocks/
  private BookController bookController;

  // vs. @InjectMocks ??? to mock away the business logic, since we don’t want to test integration between controller
  // and business logic, but between controller and the HTTP layer.
  @MockBean
  private BookRepository mockBookRepository;

  @Before
  public void setUp()
  {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testFindAllRequest() throws Exception
  {
    // simulate getting a new form for the user to fill in (GET Request)
    mockMvc.perform( get("/api/books")
                     .contentType( "application/json")) // ???
           .andExpect( status().is(200))
           .andReturn();
  }

  @Test
  public void testFindOneRequest() throws Exception
  {
    // simulate getting a new form for the user to fill in (GET Request)
    mockMvc.perform( get("/{id}", 123).contentType("application/json"))
           .andExpect( status().is(200))
           .andReturn();

// Book( long newId, String newTitle, String newAuthor)
  }

  @Test
  public void checkParamPassedToBusiness() throws Exception
  {
    Book newBook = new Book( 123, "Cim", "Szerzo");

    mockMvc.perform( post( "/api/books/create")
                     .contentType( "application/json")
                     .content( objectMapper.writeValueAsString( newBook))) // <-> create( @RequestBody Book book)
            .andExpect( status().isOk()); // <->             .andExpect( status().isOk()); // <->


    ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass( Book.class);
    verify( bookController, times(1)).create( bookCaptor.capture());
    assertThat( bookCaptor.getValue().getTitle(), is( "Cim"));
    assertThat( bookCaptor.getValue().getAuthor(), is( "Szerzo"));
  }

  @Test
  public void checkReturnedStuff() throws Exception
  {
    // "findOne() -> findById()"
    MvcResult mvcResult = mockMvc.perform( get( "/api/books/{id}", 123))
                                 .andReturn();

    String actualResponseBody = mvcResult.getResponse().getContentAsString();

    assertThat( objectMapper.writeValueAsString( DummyBookRepository.DUMMY_BOOK_1), is(actualResponseBody));
  }
}