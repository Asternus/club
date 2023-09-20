package com.club.controllers;

import com.club.entities.CompanyProfile;
import com.club.entities.User;
import com.club.services.CompanyProfileService;
import com.club.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyProfileService companyProfileService;

    private final UserService userService;

    @Autowired
    public CompanyController(CompanyProfileService companyProfileService, UserService userService) {
        this.companyProfileService = companyProfileService;
        this.userService = userService;
    }

    @PostMapping("/add-company")
    public ResponseEntity<Map<String, String>> getAllMessages(@Valid @RequestBody CompanyProfile companyProfile,
                                                              @AuthenticationPrincipal User user,
                                                              BindingResult bindingResult) {
        final User myUser = userService.getById(user.getId()).orElseThrow();

        try {
            companyProfileService.createCompany(companyProfile, myUser);
        } catch (IllegalStateException e){
            final HashMap<String, String> map = new HashMap<>();
            map.put("companyName", "this company name is used");
            return ResponseEntity.badRequest().body(map);
        } catch (IllegalArgumentException e) {
            final HashMap<String, String> map = new HashMap<>();
            map.put("companyBudget", "You dont have money");
            return ResponseEntity.badRequest().body(map);
        }

        return ResponseEntity.ok(new HashMap<>());
    }

}
