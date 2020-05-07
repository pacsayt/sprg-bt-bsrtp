package baeldung.sprgbtbsrtp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Boot Tutorial – Bootstrap a Simple Application
 * https://www.baeldung.com/spring-boot-start
 * Error Handling for REST with Spring
 * https://www.baeldung.com/exception-handling-for-rest-with-spring
 *
 * Az @EnableJpaRepositories (@Enable*) annotaciot/~kat nem szabad ide tenni, hanem egy dedikalt
 * @Configuration-osztalyba
 * https://stackoverflow.com/questions/51467132/spring-webmvctest-with-enablejpa-annotation
 *
 * ERDEKES LEHET MEG :
 * "How to guides"
 * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-spring-mvc
 */

// !!! NE IDE l. ^ + MainConfiguration.java @EnableJpaRepositories( "baeldung.sprgbtbsrtp") // scan the specified package for repositories
@EntityScan( "baeldung.sprgbtbsrtp") // pick up our JPA entities
@SpringBootApplication
public class SprgBtBsrtpApplication
{
	public static void main( String[] args)
	{
		SpringApplication.run( SprgBtBsrtpApplication.class, args);
	}
}