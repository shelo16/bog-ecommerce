package com.tornikeshelia.bogecommerce.security.service;


import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUser;
import com.tornikeshelia.bogecommerce.model.persistence.repository.EcommerceUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private EcommerceUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EcommerceUser myUser = userRepository.searchByEmail(email);
        System.out.println("Successfully found user : " + myUser.getEmail() + " " + myUser.getBcryptedPassword());
        return new User(myUser.getEmail(), myUser.getBcryptedPassword(), new ArrayList<>());
    }
}
