package com.decagon.rewardyourteacherapi.controller;

import com.decagon.rewardyourteacherapi.OAuth.CustomOAuth2User;
import com.decagon.rewardyourteacherapi.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class OAuthController {
    private UserServiceImpl userService;

    @Autowired
    public OAuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String socialRegister(HttpSession session) throws IOException {
        Authentication userDetails =  SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User oAuth2User = (CustomOAuth2User) userDetails.getPrincipal();
        String loggedUserEmail = oAuth2User.getEmail();
        session.setAttribute("loggedUserEmail", loggedUserEmail);
        String name = userDetails.getName();
        return name + " successfully logged in";
    }

}
