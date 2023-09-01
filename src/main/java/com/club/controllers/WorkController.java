package com.club.controllers;

import com.club.entities.User;
import com.club.services.UserService;
import com.club.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class WorkController {

    private final WorkService workService;

    private final UserService userService;

    @Autowired
    public WorkController(WorkService workService, UserService userService) {
        this.workService = workService;
        this.userService = userService;
    }

    @GetMapping("/get-work")
    public ResponseEntity<Map<String, Boolean>> getWork(@AuthenticationPrincipal User user) {
        final Optional<User> byId = userService.getById(user.getId());

        final Map<String, Boolean> result = new HashMap<>();

        final boolean isAvailable = byId
                .map(User::isAvailable)
                .orElse(false);

        result.put("isAvailable", isAvailable);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/do-work")
    public ResponseEntity<Map<String, Long>> doWork(@AuthenticationPrincipal User user, @RequestBody Map<String, Long> requestData) {
        final Long time = requestData.get("time");
        workService.updateWork(user, time);
        return ResponseEntity.ok(new HashMap<>());
    }

}
