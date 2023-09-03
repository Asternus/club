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

    private final WalletService walletService;

    @Autowired
    public WorkService(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    public void updateWork(final User user, final Long time, final Long salary) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        user.setAvailable(false);
        userService.editUser(user);

        final Runnable task = () -> {
            final Wallet wallet = user.getWallet();
            final BigDecimal bigDecimal = wallet.addAsters(BigDecimal.valueOf(time * salary));
            wallet.setAster(bigDecimal);
            user.setAvailable(true);
            walletService.saveWallet(wallet);
            userService.editUser(user);
        };

        scheduler.schedule(task, time, TimeUnit.SECONDS);
    }

}
