package com.club.controllers;

import com.club.entities.User;
import com.club.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add-user")
    public ResponseEntity<Map<String, String>> getAllMessages(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final Map<String, String> errorResponse = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            DefaultMessageSourceResolvable::getDefaultMessage,
                            (existing, replacement) -> existing
                    ));

            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            userService.saveUser(user);
        } catch (ValidationException e) {
            final HashMap<String, String> emailNotUniq = new HashMap<>();
            emailNotUniq.put("email", "Email already in use");
            return ResponseEntity.badRequest().body(emailNotUniq);
        }

        return ResponseEntity.ok(new HashMap<>());
    }

}
