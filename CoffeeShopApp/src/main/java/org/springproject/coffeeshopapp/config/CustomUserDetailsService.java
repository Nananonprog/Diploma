package org.springproject.coffeeshopapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springproject.coffeeshopapp.entity.User;
import org.springproject.coffeeshopapp.repository.UserRepo;
import org.springproject.coffeeshopapp.service.UserService;
@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }else{
            return new CustomUser(user);
        }
    }
}
