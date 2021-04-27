package com.finki.ukim.mk.demo.rest;


import com.finki.ukim.mk.demo.application.services.QueryService;
import com.finki.ukim.mk.demo.application.services.UserQueriesService;
import com.finki.ukim.mk.demo.application.services.UserService;
import com.finki.ukim.mk.demo.application.services.security.CustomUserDetailsService;
import com.finki.ukim.mk.demo.domain.exceptions.UsernameAlreadyRegisteredException;
import com.finki.ukim.mk.demo.domain.model.User;
import com.finki.ukim.mk.demo.domain.model.UserQueries;
import com.finki.ukim.mk.demo.domain.model.dto.UserDTO;
import com.finki.ukim.mk.demo.domain.model.dto.UserQueriesDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public Long saveQuery(@RequestParam String url, @RequestParam String defaultGraphSetIri, @RequestParam String queryStr, @RequestParam String format, @RequestParam(required = false, defaultValue = "10000") int timeout, @RequestParam String queryName, @RequestParam(required = true) boolean privateAccess, @RequestParam String queryType)  {
        String queryResult = queryService.getByQuery(url, defaultGraphSetIri, queryStr, format, timeout, false, queryType);
        User user = customUserDetailsService.getAuthenticatedUser();
        if(user != null){
            return userQueriesService.createNewUserQuery(user.getEmail(), url, defaultGraphSetIri, queryStr, format, timeout, queryResult, queryName, privateAccess);
        }
        return -1l;
    }

    @GetMapping("/get_all_queries")
    public UserQueriesDTO getAllQueriesForUser(@RequestParam("page") int page,
                                               @RequestParam("size") int size)  {
        User user = customUserDetailsService.getAuthenticatedUser();
        Page<UserQueries> results =  userQueriesService.getAllQueriesForUser(user.getEmail(), page, size);
        return new UserQueriesDTO(results.getTotalPages(), results.getContent() );


    }

    @GetMapping("/get_all_public_queries")
    public UserQueriesDTO getAllPublicQueries(@RequestParam("page") int page,
                                                 @RequestParam("size") int size)  {
        Page<UserQueries> results =  userQueriesService.getAllPublicQueries( page, size);
        return new UserQueriesDTO(results.getTotalPages(), results.getContent() );

    }

    @GetMapping("/get_single_public_query")
    public UserQueries getAllPublicQueries( @RequestParam Long queryId)  {
        return userQueriesService.getSingleUserQuery(queryId);

    }

    @PostMapping("/qet_query_result")
    public ResponseEntity<String> getQueryResult(@RequestParam String format, @RequestParam Long queryId)
    {
        String [] formatSplit = format.split("/");

        return ResponseEntity.ok().contentType(new MediaType(formatSplit[0], formatSplit[1])).body(userQueriesService.getQueryResult(queryId));

    }
//



}




