package baeldung.sprgbtbsrtp;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleControllerTest
{

  @Value("${spring.application.name}")
  String appName;

  // bind the above RANDOM_PORT 8081 ?
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testRootPage() throws Exception
  {
    ResponseEntity<String> response = restTemplate.getForEntity( new URL("http://localhost:" + port + "/").toString(), String.class);
    assertEquals("<!DOCTYPE html>\n" +
                          "<html lang=\"en\">\n" +
                          "\n" +
                          "  <head>\n" +
                          "    <meta charset=\"UTF-8\">\n" +
                          "    <title>Home Page</title>\n" +
                          "  </head>\n" +
                          "\n" +
                          "  <body>\n" +
                          "    <h1>Hello !</h1>\n" +
                          "    <p>Welcome to <span>" + appName + "</span></p>\n" +
                          "  </body>\n" +
                          "\n" +
                          "</html>", response.getBody());
  }
}
