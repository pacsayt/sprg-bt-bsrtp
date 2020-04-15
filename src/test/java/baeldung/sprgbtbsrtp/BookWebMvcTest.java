package baeldung.sprgbtbsrtp;

import baeldung.sprgbtbsrtp.components.BookController;
import baeldung.sprgbtbsrtp.persistency.BookRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * https://spring.io/guides/gs/testing-web/
 *
 * In this test, the full Spring application context is started but without the server.
 * We can narrow the tests to only the web layer by using @WebMvcTest,
 */
@WebMvcTest( BookController.class)
public class BookWebMvcTest
{
  @Value("${spring.application.name}")
  String appName;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookRepository bookRepository;

  @Test
  public void test() throws Exception
  {
    mockMvc.perform( get( "/"))./*andDo( print()).*/andExpect( MockMvcResultMatchers.status().isOk());
//            .andExpect( MockMvcResultMatchers.content().string( Matchers.containsString( appName)) );
  }
}
