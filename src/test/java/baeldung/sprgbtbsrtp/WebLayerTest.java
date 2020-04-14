package baeldung.sprgbtbsrtp;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * https://spring.io/guides/gs/testing-web/
 *
 * Another useful approach is to not start the server at all but to test only the layer below that,
 * where Spring handles the incoming HTTP request and hands it off to your controller.
 * That way, almost of the full stack is used, and your code will be called in exactly the same way
 * as if it were processing a real HTTP request but without the cost of starting the server.
 * To do that, use Spring’s MockMvc and ask for that to be injected for you by using the
 * @AutoConfigureMockMvc annotation on the test case.
 */
//@SpringBootTest          eredetileg ez a ket annotacio volt
//@AutoConfigureMockMvc

// MkYong szerint : https://mkyong.com/spring-boot/spring-boot-test-unable-to-autowired-mockmvc/
// es ez telleg beinjektalja a MockMvc-t !!!
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test

public class WebLayerTest
{
  @Autowired
  private MockMvc mockMvc; // null

  @Test
  public void test() throws Exception
  {
    mockMvc.perform( get( "/")).andDo( print()).andExpect( MockMvcResultMatchers.status().isOk())
                                                          .andExpect( MockMvcResultMatchers.content().string( Matchers.containsString( "XXXXXXXXXXXXXXXXXX")) );
  }
}
