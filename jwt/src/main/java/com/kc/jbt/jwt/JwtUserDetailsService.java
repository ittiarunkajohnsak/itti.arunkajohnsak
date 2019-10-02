package com.kc.jbt.jwt;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("itti.test".equals(username)) {
            // password come from new BCryptPasswordEncoder().encode("password")
            return new User("itti.test", "$2a$10$sKdRrOLe9z8xlCDzcdzczOpGwkrG.SxiH3WPG4s6J/4ElqLe0TEXm",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
