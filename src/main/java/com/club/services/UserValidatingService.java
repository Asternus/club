package com.club.services;

import com.club.entities.User;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserValidatingService {

    private final UserService userService;

    @Autowired
    public UserValidatingService(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<Map<String, String>> getValidatingResponse(final User user, final String rePassword, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final Map<String, String> errorResponse = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            DefaultMessageSourceResolvable::getDefaultMessage,
                            (existing, replacement) -> existing
                    ));

            return ResponseEntity.badRequest().body(errorResponse);
        }

        if (!rePassword.equals(user.getPassword())) {
            final HashMap<String, String> emailNotUniq = new HashMap<>();
            emailNotUniq.put("rePassword", "Passwords are different");
            return ResponseEntity.badRequest().body(emailNotUniq);
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
