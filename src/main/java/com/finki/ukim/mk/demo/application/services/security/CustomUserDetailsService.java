package com.finki.ukim.mk.demo.application.services.security;


import com.finki.ukim.mk.demo.domain.exceptions.NoAuthenticationFoundException;
import com.finki.ukim.mk.demo.domain.model.User;
import com.finki.ukim.mk.demo.domain.model.dto.UserDTO;
import com.finki.ukim.mk.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ApplicationEventPublisher applicationEventPublisher;

    public CustomUserDetailsService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();


        if (user == null){
            throw new UsernameNotFoundException("User with emaiL: " + email + " was not found!");
        }

        return user;
    }

    public User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (User) auth.getPrincipal();
        }
        else {
            throw new NoAuthenticationFoundException();
        }
    }

    @Transactional
    public User register(UserDTO userDTO) {
        User newUser = new User();

        newUser.setEmail(userDTO.email);
        newUser.setPassword(bCryptPasswordEncoder.encode(userDTO.password));
        newUser.setName(userDTO.name);
        newUser.setLastName(userDTO.lastName);
        newUser.setAuthRole("ROLE_USER");
        userRepository.save(newUser);

        return newUser;
    }


}
