package com.club.config;


import com.club.entities.CompanyProfile;
import com.club.entities.Message;
import com.club.entities.Role;
import com.club.entities.User;
import com.club.entities.Wallet;
import com.club.entities.WorkProfile;
import com.club.services.CompanyProfileService;
import com.club.services.MessageService;
import com.club.services.UserService;
import com.club.services.WalletService;
import com.club.services.WorkProfileService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

@Component
public class DbInit {

    private final MessageService messageService;

    private final WalletService walletService;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final WorkProfileService workProfileService;

    private final CompanyProfileService companyProfileService;

    private Random random = new Random();

    @Autowired
    public DbInit(MessageService messageService, WalletService walletService, PasswordEncoder encoder, UserService service, WorkProfileService workProfileService, CompanyProfileService companyProfileService) {
        this.messageService = messageService;
        this.walletService = walletService;
        passwordEncoder = encoder;
        userService = service;
        this.workProfileService = workProfileService;
        this.companyProfileService = companyProfileService;
    }

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 100; i++) {
            final Message message = new Message();
            message.setText(random.nextInt() + "");
            messageService.saveMassage(message);
        }

        final User user = new User();
        user.setEmail("admin@gmail.com");
        user.setUsername("admin");
        user.setRoles(Set.of(Role.ADMIN));
        user.setAvailable(true);
        String password = "1234567890";
        final String encode = passwordEncoder.encode(password);
        user.setPassword(encode);

        final Wallet wallet = new Wallet();
        wallet.setAster(new BigDecimal(1000));
        walletService.saveWallet(wallet);

        user.setWallet(wallet);

        userService.saveUserByAdmin(user);

        CompanyProfile companyProfile = new CompanyProfile();
        companyProfile.setCompanyName("Racoons");
        companyProfile.setEmployees(1L);
        companyProfile.setDescription("We are raccoons");

        companyProfileService.save(companyProfile);

        final WorkProfile workProfile = new WorkProfile();
        workProfile.setPosition("Racoon");
        workProfile.setSalary(new BigDecimal(100));
        workProfile.setJobName("Chill");
        workProfile.setUser(user);
        workProfile.setCompanyProfile(companyProfile);

        workProfileService.saveWorkProfile(workProfile);
    }
}
