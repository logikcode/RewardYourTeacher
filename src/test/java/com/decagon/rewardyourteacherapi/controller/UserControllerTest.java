package com.decagon.rewardyourteacherapi.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.decagon.rewardyourteacherapi.response.ResponseAPI;
import com.decagon.rewardyourteacherapi.service.UserService;
import com.decagon.rewardyourteacherapi.serviceImpl.MailService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private MailService mailService;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#currentUserBalance(Long)}
     */
    @Test
    void testCurrentUserBalance() throws Exception {
        LocalDateTime time = LocalDateTime.of(1, 1, 1, 1, 1);
        when(userService.userWalletBalance((Long) any()))
                .thenReturn(new ResponseAPI<>("Not all who wander are lost", time, BigDecimal.valueOf(42L)));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/retrieve/balance/{id}", 123L);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Not all who wander are lost\",\"time\":[1,1,1,1,1],\"dto\":42}"));
    }
}

