package baeldung.sprgbtbsrtp.errorhandling;

import java.util.function.Function;
import java.util.function.Supplier;

public class BookNotFoundException extends RuntimeException {



  public BookNotFoundException( String message)
  {
    super( message);
  }

  public static <T, R> Supplier<R> bind( Function<T,R> function, T value) {
    return () -> function.apply( value);
  }

//  create(bind(Foo::new, "hello"));   https://stackoverflow.com/questions/31251629/java-8-supplier-with-arguments-in-the-constructor
}
