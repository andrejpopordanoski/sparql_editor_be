package com.finki.ukim.mk.demo.application.services;


import com.finki.ukim.mk.demo.domain.model.User;
import com.finki.ukim.mk.demo.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

//    public UserOutsideDTO findById(UserId userId) {
//        UserOutsideDTO userOutsideDTO = new UserOutsideDTO();
//
//        Optional<User> userOpt = userRepository.findById(userId);
//        if(userOpt.isPresent()){
//            User user = userOpt.get();
//            userOutsideDTO.setAuthRole(user.authRole);
//            userOutsideDTO.setEmail(user.email);
//            userOutsideDTO.setHeight(user.height);
//            userOutsideDTO.setLastName(user.lastName);
//            userOutsideDTO.setName(user.name);
//            return userOutsideDTO;
//
//        }
//        else {
//            // throw exception
//            throw new UserNotFoundException();
//        }
//
//    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
