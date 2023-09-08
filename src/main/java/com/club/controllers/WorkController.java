package com.club.controllers;

import com.club.entities.User;
import com.club.entities.WorkProfile;
import com.club.services.UserService;
import com.club.services.WorkProfileService;
import com.club.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WorkController {

    private final WorkService workService;

    private final UserService userService;

    private final WorkProfileService workProfileService;

    @Autowired
    public WorkController(WorkService workService, UserService userService, WorkProfileService workProfileService) {
        this.workService = workService;
        this.userService = userService;
        this.workProfileService = workProfileService;
    }

    @GetMapping("/get-work")
    public ResponseEntity<Map<String, Object>> getWork(@AuthenticationPrincipal User user) {
        final User myUser = userService.getById(user.getId()).orElseThrow();

        final Map<String, Object> result = new HashMap<>();

        final List<WorkProfile> workProfiles = myUser.getWorkProfiles();

        result.put("isAvailable", myUser.isAvailable());
        result.put("workProfiles", workProfiles);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/do-work")
    public ResponseEntity<Map<String, Long>> doWork(@AuthenticationPrincipal User user, @RequestBody Map<String, Long> requestData) {
        final Long time = requestData.get("time");
        final Long salary = requestData.get("salary");
        final User myUser = userService.getById(user.getId()).orElseThrow();
        workService.updateWork(myUser, time, salary);
        return ResponseEntity.ok(new HashMap<>());
    }

    @GetMapping("/get-vacancy")
    public List<WorkProfile> getVacancy() {
        return workProfileService.getAllFreeWorkProfiles();
    }

    @PostMapping("/take-vacancy")
    public ResponseEntity<Boolean> takeVacancy(@AuthenticationPrincipal User user, @RequestBody Map<String, String> requestBody) {
        final User myUser = userService.getById(user.getId()).orElseThrow();
        String vacancyId = requestBody.get("vacancyId");
        final WorkProfile worksProfileById = workProfileService.getWorksProfileById(vacancyId);
        worksProfileById.setUser(myUser);
        workProfileService.saveWorkProfile(worksProfileById);

        return ResponseEntity.ok(true);
    }

}
