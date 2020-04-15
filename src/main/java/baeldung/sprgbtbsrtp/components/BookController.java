package baeldung.sprgbtbsrtp.components;

import baeldung.sprgbtbsrtp.errorhandling.BookIdMismatchException;
import baeldung.sprgbtbsrtp.errorhandling.BookNotFoundException;
import baeldung.sprgbtbsrtp.persistency.BookRepository;
import baeldung.sprgbtbsrtp.persistency.DummyBookRepository;
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
@RestController // =  @Controller + @ResponseBody (each method marshalls the returned resource right to the HTTP response)
@RequestMapping("/api/books")
public class BookController
{
  @Autowired
  private DummyBookRepository bookRepository;

  @GetMapping( "/")
  public Iterable findAll()
  {
    return bookRepository.findAll();
  }

  @GetMapping("/title/{bookTitle}")
  public List findByTitle( @PathVariable String bookTitle)
  {
    return bookRepository.findByTitle( bookTitle);
  }

  @GetMapping("/{id}")
  public Book findOne(@PathVariable Long id)
  {
    Optional<Book> result = bookRepository.findById( id);

    return result.orElseThrow( createExceptionWithMessage( BookNotFoundException::new, "not found"));
//    return bookRepository.findById(id).orElseThrow( BookNotFoundException::bind( BookNotFoundException::new, "not found")); ez nem jo valamiert
  }

  @PostMapping( "/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Book create( @RequestBody Book book)
  {
    return bookRepository.save( book);
  }

  @DeleteMapping("/{id}")
  public void delete( @PathVariable Long id)
  {
    bookRepository.findById(id).orElseThrow( createExceptionWithMessage( BookNotFoundException::new, "not found")); // az exceptionban levo bind()-et nem talalja
    bookRepository.deleteById(id);
  }

  @PutMapping("/{id}")
  public Book updateBook( @RequestBody Book book, @PathVariable Long id) throws BookIdMismatchException
  {
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