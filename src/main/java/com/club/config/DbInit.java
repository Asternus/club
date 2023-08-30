package com.club.config;


import com.club.entities.Wallet;
import com.club.entities.Message;
import com.club.entities.Role;
import com.club.entities.User;
import com.club.services.AccountService;
import com.club.services.MessageService;
import com.club.services.UserService;
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

    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private Random random = new Random();

    @Autowired
    public DbInit(MessageService messageService, AccountService accountService, PasswordEncoder encoder, UserService service) {
        this.messageService = messageService;
        this.accountService = accountService;
        passwordEncoder = encoder;
        userService = service;
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

        Wallet wallet = new Wallet();
        wallet.setAster(new BigDecimal(1000));
        accountService.saveAccount(wallet);

        user.setWallet(wallet);

        userService.saveUserByAdmin(user);
    }
}
