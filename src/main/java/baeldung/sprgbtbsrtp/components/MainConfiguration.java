package baeldung.sprgbtbsrtp.components;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * A @SpringBootApplication0ba nem jo @Enable*-t tenni, mert pl. a teszt meghulyul tole :
 * https://stackoverflow.com/questions/51467132/spring-webmvctest-with-enablejpa-annotation
 *
 * @EnableAutoConfiguration :itt most nem kellett(?),de"we have to use this annotation with @Configuration"
 * @ConditionalOnClass/Bean and @ConditionalOnMissingClass/Bean
 * Using these conditions, Spring will only use the marked auto-configuration bean
 * if the class in the annotation's argument is present/absent
 * https://www.baeldung.com/spring-boot-annotations
 * @ConditionalOnProperty( name = "usemysql", havingValue = "local")
 * @ConditionalOnResource(resources = "classpath:mysql.properties")
 * @ConditionalOnWebApplication
 * @ConditionalOnNotWebApplication
**/

https://www.baeldung.com/spring-boot-starters +++++++++++++++++++++++++++++++++++++++++++++++++++++

@EnableJpaRepositories( "baeldung.sprgbtbsrtp") // scan the specified package for repositories
@Configuration
public class MainConfiguration
{
}
