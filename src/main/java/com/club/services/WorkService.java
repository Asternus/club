package com.club.services;

import com.club.entities.User;
import com.club.entities.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class WorkService {


    private final UserService userService;

    private final AccountService accountService;

    @Autowired
    public WorkService(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public void updateWork(final User user, final Long time) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        user.setAvailable(false);
        userService.editUser(user);

        final Runnable task = () -> {
            final Wallet wallet = user.getWallet();
            wallet.setAster(new BigDecimal(time * 100));
            user.setAvailable(true);
            accountService.saveAccount(wallet);
            userService.editUser(user);
        };

        scheduler.schedule(task, time, TimeUnit.SECONDS);
    }

}
