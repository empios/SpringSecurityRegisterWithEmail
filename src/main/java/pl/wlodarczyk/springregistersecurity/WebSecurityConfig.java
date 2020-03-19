package pl.wlodarczyk.springregistersecurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.wlodarczyk.springregistersecurity.service.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private MyEncoder myEncoder;
    private UserDetailsServiceImpl userDetailsService;

    //    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return this.myEncoder;
    }

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService,MyEncoder myEncoder) {
        this.userDetailsService = userDetailsService;
        this.myEncoder=myEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/forAdmin").hasRole("ADMIN")
                .antMatchers("/forUser").hasAnyRole("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll().successForwardUrl("/forUser").failureUrl("/login/error").permitAll()
                .and().logout().logoutSuccessUrl("/logout");

    }
}

