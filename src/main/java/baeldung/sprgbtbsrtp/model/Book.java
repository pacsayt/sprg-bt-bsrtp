package baeldung.sprgbtbsrtp.model;

import javax.persistence.*;

@Entity
public class Book
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(nullable = false, unique = true)
  private String title;

  @Column(nullable = false)
  private String author;

  public Book( long newId, String newTitle, String newAuthor)
  {
    id = newId;
    title = newTitle;
    author = newAuthor;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long newId)
  {
    id = newId;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String newTitle)
  {
    title = newTitle;
  }

  public String getAuthor()
  {
    return author;
  }

  public void setAuthor(String newAuthor)
  {
    author = newAuthor;
  }
}