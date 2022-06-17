package com.bimal.springsecurityjpa;

import com.bimal.springsecurityjpa.DAO.UserRepo;
import com.bimal.springsecurityjpa.models.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        var user= userRepo.findByUserName(userName).get();

        return new MyUserDetails(user);
    }
}
