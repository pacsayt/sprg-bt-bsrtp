package baeldung.sprgbtbsrtp.persistency;

import baeldung.sprgbtbsrtp.components.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class DummyBookRepository implements BookRepository
{
  public static final Book DUMMY_BOOK_1 =new Book( 123, "Cim_1", "Serzo_1");
  public static final Book DUMMY_BOOK_2 =new Book( 456, "Cim_2", "Serzo_2");

  @Override
  public List<Book> findByTitle(String title)
  {
    return Arrays.asList( DUMMY_BOOK_1, DUMMY_BOOK_2);
  }

  @Override
  public <S extends Book> S save(S s)
  {
    return s;
  }

  @Override
  public <S extends Book> Iterable<S> saveAll(Iterable<S> iterable)
  {
    return iterable;
  }

  @Override
  public Optional<Book> findById(Long aLong)
  {
    return Optional.of( DUMMY_BOOK_1);
  }

  @Override
  public boolean existsById(Long aLong)
  {
    return false;
  }

  @Override
  public Iterable<Book> findAll()
  {
    return Arrays.asList( DUMMY_BOOK_1, DUMMY_BOOK_2);
  }

  @Override
  public Iterable<Book> findAllById(Iterable<Long> iterable)
  {
    return Arrays.asList( DUMMY_BOOK_1, DUMMY_BOOK_2);
  }

  @Override
  public long count()
  {
    return 1;
  }

  @Override
  public void deleteById(Long aLong)
  {
  }

  @Override
  public void delete(Book book)
  {
  }

  @Override
  public void deleteAll(Iterable<? extends Book> iterable)
  {
  }

  @Override
  public void deleteAll()
  {
  }
}
