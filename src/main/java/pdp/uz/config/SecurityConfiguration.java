package pdp.uz.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("111")).roles("SUPER_ADMIN")
                .and()
                .withUser("moderator").password(passwordEncoder().encode("222")).roles("MODERATOR")
                .and()
                .withUser("operator").password(passwordEncoder().encode("333")).roles("OPERATOR");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                .antMatchers( "/api/product/add").hasAnyRole("MODERATOR", "SUPER_ADMIN")
                .antMatchers( "/api/product/update/*").hasAnyRole("MODERATOR", "SUPER_ADMIN")
                .antMatchers("/api/order/**").hasAnyRole("OPERATOR", "SUPER_ADMIN")
                .antMatchers("/api/**").hasRole("SUPER_ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
