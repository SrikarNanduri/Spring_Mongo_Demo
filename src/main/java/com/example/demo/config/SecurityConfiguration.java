package com.example.demo.config;

import com.example.demo.services.MongoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// The @Configuration annotation indicates that the class will contain java "beans"
@Configuration
// The @EnableConfigurationProperties annotation indicates that the class will contain as special kind of bean called configuration beans.
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    MongoUserDetailsService userDetailsService;


    /* we need to tell Spring Security how we want to handle user authentication. By default, Spring Security has a predefined username and password, CSRF Protection, and Session Management.
     However, we want our users to use their own username and password to access the database. Additionally, since our users will be re-authenticating with every request instead of logging in,
     CSRF Protection and Session Management are unnecessary, so we can add a method called configure that overrides the default authentication scheme, to tell Spring exactly how we want to handle authentication.*/

    //The @Override annotation tells Spring Boot to use this configure(HttpSecurity http) method, instead of Spring's default configuration.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
       Then we call a series of methods on the http object, which is where the actual configuration happens. These methods do the following:

        .csrf().disable(): Disables CSRF protection, as it is uneccesary for an API
        .authorizeRequests().anyRequest().authenticated(): Explains that all requests to any endpoint must be authorizes, or else they should be rejected
        .and().httpBasic(): Tells Spring to look for the HTTP Basic authentication method (discussed above)
        .and().sessionManagement().disable(): Tells Spring not to hold session information for users, as this is uneccesary in an API
        */
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement().disable();
    }


    /*Bcrypt Encoder:
    We now need to tell Spring to use the BCrypt encoder to hash and compare passwords.*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*Specifying the Authentication Manager:
    Lastly, we must specify in our SecurityConfiguration that we want to use the MongoUserDetailsService for our authentication. We can do this with the following method:*/
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }

}
