package baeldung.sprgbtbsrtp.components;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security with Spring
 * https://www.baeldung.com/security-spring
 *
 * Ha spring-boot-starter-security rajta van a CP-on,
 * alapbol minden vegpont httpBasic or formLogin eljarassal vedve vagyon,
 * emiatt kell sajat biztonsagi szabalyokat definialni. *
 */


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    // In our example, we're allowing unrestricted access to all endpoints.
    http.authorizeRequests()
        .anyRequest()
        .permitAll()
        .and().csrf().disable();
  }
}