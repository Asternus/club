package com.club.controllers;

import com.club.entities.User;
import com.club.services.UserService;
import com.club.services.UserValidatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    private final UserValidatingService userValidatingService;

    @Autowired
    public UserController(UserService userService, UserValidatingService service) {
        this.userService = userService;
        userValidatingService = service;
    }

    @PostMapping("/add-user")
    public ResponseEntity<Map<String, String>> getAllMessages(@Valid @RequestBody User user, BindingResult bindingResult, @RequestParam String rePassword) {
        return userValidatingService.getValidatingResponse(user, rePassword, bindingResult);
    }

}
