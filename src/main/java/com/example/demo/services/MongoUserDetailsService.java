package com.example.demo.services;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/*We need to tell Spring where our user data is located and where to find the information it needs for authentication. To do this, we can create an Authentication Service.*/

//the @Component annotation indicates that this class can be injected into another file.
@Component
// this implements UserDetailsService section denotes that this class will be creating a service for finding and authenticating users.
public class MongoUserDetailsService implements UserDetailsService {

    /*The @Autowired annotation above private UsersRepository repository; is an example of injection, this property will give us an instance of our UsersRepository to work with.*/
    @Autowired
    private UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        //The user is given an authority/role.
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}