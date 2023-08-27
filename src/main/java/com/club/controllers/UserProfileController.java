package com.club.controllers;

import com.club.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    @GetMapping("")
    public User getUserInfo(@AuthenticationPrincipal User user) {
        return user;
    }

}
