package ir.maktab56.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/professor/**").hasRole("PROFESSOR")
                .mvcMatchers("/student/**").hasRole("STUDENT")
                .mvcMatchers("/image/**").hasAnyAuthority("write")
                .anyRequest().permitAll()
                .and().formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
        http.httpBasic();
        http.formLogin()
                .loginPage("/login")
                .successForwardUrl("/user-page")
                .and().logout();
    }
}
