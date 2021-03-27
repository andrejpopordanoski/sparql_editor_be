package com.finki.ukim.mk.demo.rest;


import com.finki.ukim.mk.demo.application.services.QueryService;
import com.finki.ukim.mk.demo.application.services.UserQueriesService;
import com.finki.ukim.mk.demo.application.services.UserService;
import com.finki.ukim.mk.demo.application.services.security.CustomUserDetailsService;
import com.finki.ukim.mk.demo.domain.exceptions.UsernameAlreadyRegisteredException;
import com.finki.ukim.mk.demo.domain.model.User;
import com.finki.ukim.mk.demo.domain.model.dto.UserDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Query;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserCatalog {


    private final UserService userService;

    private final CustomUserDetailsService customUserDetailsService;

    private final QueryService queryService;

    private final UserQueriesService userQueriesService;


    public UserCatalog(UserService userService, CustomUserDetailsService customUserDetailsService, QueryService queryService, UserQueriesService userQueriesService) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
        this.queryService = queryService;
        this.userQueriesService = userQueriesService;
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getAllUsers();
    }


    @GetMapping("/info/get")
    public User getInfo(){
        return customUserDetailsService.getAuthenticatedUser();
    }

    @PostMapping("register")
    public User registerUser(@RequestBody UserDTO userDTO) throws UsernameAlreadyRegisteredException {
        return customUserDetailsService.register(userDTO);
    }

    @PostMapping("/save_query")
    public void saveQuery(@RequestParam String url, @RequestParam String defaultGraphSetIri, @RequestParam String queryStr, @RequestParam String format, @RequestParam(required = false, defaultValue = "10000") int timeout, @RequestParam String queryName)  {
        String queryResult = queryService.getByQuery(url, defaultGraphSetIri, queryStr, format, timeout);
        User user = customUserDetailsService.getAuthenticatedUser();
        if(user != null){
            userQueriesService.createNewUserQuery(user.getEmail(), url, defaultGraphSetIri, queryStr, format, timeout, queryResult, queryName);
        }

    }





}




