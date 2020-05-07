package baeldung.sprgbtbsrtp.components;

import baeldung.sprgbtbsrtp.errorhandling.BookIdMismatchException;
import baeldung.sprgbtbsrtp.errorhandling.BookNotFoundException;
import baeldung.sprgbtbsrtp.model.Book;
import baeldung.sprgbtbsrtp.persistency.DummyBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * Input can be either the JSON content of the request body (2. @RequestBody),
 * a variable within the URL path (1. @PathVariable), or an HTTP request parameter (3. @RequestParam)
 * https://reflectoring.io/spring-boot-web-controller-test/
 *
 *   @PostMapping("/forums/{forumId}/register")
 *   UserResource register(
 *           @PathVariable("forumId") Long forumId,                       // 1.
 *           @Valid @RequestBody UserResource userResource,               // 2.
 *           @RequestParam("sendWelcomeMail") boolean sendWelcomeMail) {  // 3.
 *
 *  2. : Parameters may be annotated with @Valid to indicate that Spring should perform bean validation on them.
 */

/**
 * Spring uses HttpMessageConverters to render @ResponseBody (or responses from @RestController).
 * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-spring-mvc
 */
@RestController // =  @Controller + @ResponseBody (each method marshalls the returned resource right to the HTTP response)
@RequestMapping("/api/books")
public class BookController
{
  private Logger logger = LoggerFactory.getLogger( BookController.class);

  @Autowired
  private DummyBookRepository bookRepository;

  @GetMapping( "/")
  public Iterable findAll()
  {
    logger.info( "/api/books/ => bookRepository.findAll()");
    return bookRepository.findAll();
  }

  @GetMapping("/title/{bookTitle}")
  public List findByTitle( @PathVariable String bookTitle)
  {
    logger.info( "findByTitle() : /api/books/title/" + bookTitle);

    return bookRepository.findByTitle( bookTitle);
  }

  @GetMapping("/{id}")
  public Book findOne(@PathVariable Long id)
  {
    logger.info( "findOne() : /api/books/" + id);

    Optional<Book> result = bookRepository.findById( id);

    return result.orElseThrow( createExceptionWithMessage( BookNotFoundException::new, "not found"));
//    return bookRepository.findById(id).orElseThrow( BookNotFoundException::bind( BookNotFoundException::new, "not found")); ez nem jo valamiert
  }

  @PostMapping( "/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Book create( @RequestBody Book book)
  {
    logger.info( "create() : /api/books/create : book=" + book);
    return bookRepository.save( book);
  }

  @DeleteMapping("/{id}")
  public void delete( @PathVariable Long id)
  {
    logger.info( "delete() : /api/books/ : id=" + id);

    bookRepository.findById(id).orElseThrow( createExceptionWithMessage( BookNotFoundException::new, "not found")); // az exceptionban levo bind()-et nem talalja
    bookRepository.deleteById(id);
  }

  @PutMapping("/{id}")
  public Book updateBook( @RequestBody Book book, @PathVariable Long id) throws BookIdMismatchException
  {
    logger.info( "updateBook() : /api/books/ : id=" + id);

    if ( book.getId() != id ) // nem jo otlet ez a redundans interfesz ...
    {
      throw new BookIdMismatchException();
    }

    bookRepository.findById(id).orElseThrow( createExceptionWithMessage( BookNotFoundException::new, "not found")); // az exceptionban levo bind()-et nem talalja
    return bookRepository.save( book);
  }

  public static <T, R> Supplier<R> createExceptionWithMessage(Function<T,R> function, T value)
  {
    return () -> function.apply( value);
  }

}