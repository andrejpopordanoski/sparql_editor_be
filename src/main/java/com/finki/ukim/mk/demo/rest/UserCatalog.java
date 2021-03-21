package com.finki.ukim.mk.demo.rest;


import com.finki.ukim.mk.demo.application.services.UserService;
import com.finki.ukim.mk.demo.application.services.security.CustomUserDetailsService;
import com.finki.ukim.mk.demo.domain.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserCatalog {


    private final UserService userService;

    private final CustomUserDetailsService customUserDetailsService;

    public UserCatalog(UserService userService, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;

    }

    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getAllUsers();
    }

//    @GetMapping("/{id}")
//    public UserOutsideDTO getUser(@PathVariable String id){
//        return userService.findById(new UserId(id));
//    }

//    @PostMapping("/info/update")
//    public User updateInfo(@RequestParam(value = "name", required = false)String name, @RequestParam(value = "surname", required = false)String surname, @RequestParam(value = "image", required = false) MultipartFile image){
//        return customUserDetailsService.updateInfo(name,surname,image);
//    }

    @GetMapping("/info/get")
    public User getInfo(){
        return customUserDetailsService.getAuthenticatedUser();
    }





}




