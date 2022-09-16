package com.decagon.rewardyourteacherapi.controller;

import com.decagon.rewardyourteacherapi.dto.UserDTO;
import com.decagon.rewardyourteacherapi.service.UserService;
import com.decagon.rewardyourteacherapi.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OAuthController {
    private UserServiceImpl userService;

    @Autowired
    public OAuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String socialRegister(Authentication authentication) throws IOException {
//        Principal principal = (Principal) auth2User.getName();
        return authentication.getName() + " successfully logged in";
    }

//    @GetMapping ("/oauth2/authorization/google")
//    public String socialRegister(OAuth2AuthenticationToken token) {
//        UserDTO userDTO = new UserDTO();
//        Map<String, Object> tokenMap = token.getPrincipal().getAttributes();
//
//        userDTO.setName((String) tokenMap.get("name"));
//        userDTO.setEmail((String) tokenMap.get("email"));
//        System.out.println(userDTO);
//        userService.createUser(userDTO);
//
//        return "Google user successfully logged";
//    }

}
