package com.club.services;

import com.club.entities.User;
import com.club.entities.Wallet;
import com.club.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void saveWallet(final Wallet wallet) {
        walletRepository.save(wallet);
    }

    public Wallet getWalletFromUser(final User user) {
        return walletRepository.findById(user.getWallet().getId()).orElseThrow();
    }
}
