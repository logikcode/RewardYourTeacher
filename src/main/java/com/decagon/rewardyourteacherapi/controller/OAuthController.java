package com.decagon.rewardyourteacherapi.controller;

import com.decagon.rewardyourteacherapi.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class OAuthController {
    private UserServiceImpl userService;

    @Autowired
    public OAuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String socialRegister() throws IOException {
        Authentication userDetails =  SecurityContextHolder.getContext().getAuthentication();
        String name = userDetails.getName();
        return name + " successfully logged in";
    }


}
