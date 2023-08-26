package com.club.controllers;

import com.club.entities.User;
import com.club.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private static final Logger logger = LogManager.getLogger(AdminController.class);

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllMessages() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/ban/{id}")
    public ResponseEntity<Map<String, String>> disableUser(@PathVariable String id) {
        final Optional<User> user = userService.getById(id);

        if (user.isPresent()) {
            user.get().setNonLocked(false);
            return ResponseEntity.ok(new HashMap<>());
        }

        Map<String, String> attributes = new HashMap<>();
        attributes.put("user", "not present");

        return ResponseEntity.badRequest().body(attributes);
    }

}
