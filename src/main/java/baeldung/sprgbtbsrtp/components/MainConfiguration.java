package baeldung.sprgbtbsrtp.components;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * https://stackoverflow.com/questions/51467132/spring-webmvctest-with-enablejpa-annotation
 *
**/

@EnableJpaRepositories( "baeldung.sprgbtbsrtp") // scan the specified package for repositories
@Configuration
public class MainConfiguration
{
}
