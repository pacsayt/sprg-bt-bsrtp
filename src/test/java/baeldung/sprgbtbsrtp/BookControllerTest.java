package baeldung.sprgbtbsrtp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import baeldung.sprgbtbsrtp.components.BookController;
import baeldung.sprgbtbsrtp.persistency.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @WebMvcTest
 * Using this annotation will disable full auto-configuration and instead apply only configuration relevant to MVC tests
 * (i.e. * @Controller, @ControllerAdvice, @JsonComponent, Converter/GenericConverter, Filter, WebMvcConfigurer and
 * HandlerMethodArgumentResolver beans but not @Component, @Service or @Repository beans).
 *
 * https://spring.io/guides/gs/testing-web/  :
 * In this test (@WebMvcTest), the full Spring application context is started but without the server.
 * We can narrow the tests to only the web layer by using @WebMvcTest
 *
 * controllers :
 * If we leave away the controllers parameter, Spring Boot will include all controllers in the application context.
 * Thus, we need to include or mock away all beans any controller depends on.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * https://spring.io/guides/gs/testing-web/ - itt a restAssured & Co. nem uzemelt be
 */

// @RunWith( SpringRunner.class) ezt nem talalja ... helyette :
@ExtendWith( SpringExtension.class) // allitolag nem kell mar, mert benne van a @DataJpaTest/@WebMvcTest/@SpringBootTest-ban
@WebMvcTest( controllers = BookController.class)
public class BookControllerTest
{
  // instance to simulate HTTP requests
  @Autowired
  private MockMvc mockMvc;

  // Spring Boot automatically provides beans like an @ObjectMapper to map to and from JSON
  // and a MockMvc instance to simulate HTTP requests.
  @Autowired
  private ObjectMapper objectMapper;

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
  public void testFindAllRequest() throws Exception {

    // simulate getting a new form for the user to fill in (GET Request)
    mockMvc.perform( get("/api/books"))
           .andExpect( status().is(200))
           .andReturn();
  }

  @Test
  public void testFindOneRequest() throws Exception {

    // simulate getting a new form for the user to fill in (GET Request)
    mockMvc.perform( get("/{id}", 123).contentType("application/json"))
           .andExpect( status().is(200))
           .andReturn();

// Book( long newId, String newTitle, String newAuthor)
  }

}

/*
import io.reflectoring.testing.persistence.PersistenceAdapter;
import io.reflectoring.testing.persistence.UserEntity;
import io.reflectoring.testing.persistence.UserRepository;
import io.reflectoring.testing.web.UserResource;
 */