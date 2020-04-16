package baeldung.sprgbtbsrtp;

import baeldung.sprgbtbsrtp.components.BookController;
import baeldung.sprgbtbsrtp.persistency.BookRepository;
import baeldung.sprgbtbsrtp.persistency.DummyBookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * https://spring.io/guides/gs/testing-web/
 *
 * In this test, the full Spring application context is started but without the server.
 * We can narrow the tests to only the web layer by using @WebMvcTest,
 */
@WebMvcTest()
public class BookWebMvcTest
{
  @Value("${spring.application.name}")
  String appName;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private DummyBookRepository mockDummyBookRepository;

  @Test
  public void test() throws Exception
  {
    Mockito.when( mockDummyBookRepository.findAll()).thenReturn( Arrays.asList( DummyBookRepository.DUMMY_BOOK_1));

    MvcResult mvcResult = mockMvc.perform( get( "/api/books/")).andDo( print()).andExpect( MockMvcResultMatchers.status().isOk()).andReturn();

    String actualResponseBody = mvcResult.getResponse().getContentAsString();

    assertThat( actualResponseBody, is( objectMapper.writeValueAsString( Arrays.asList( DummyBookRepository.DUMMY_BOOK_1))));
  }
}
