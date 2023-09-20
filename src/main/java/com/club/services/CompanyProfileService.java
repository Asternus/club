package com.club.services;

import com.club.entities.CompanyProfile;
import com.club.entities.User;
import com.club.entities.Wallet;
import com.club.entities.WorkProfile;
import com.club.repositories.CompanyProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyProfileService {

    private final CompanyProfileRepository companyProfileRepository;

    @Autowired
    public CompanyProfileService(CompanyProfileRepository companyProfileRepository) {
        this.companyProfileRepository = companyProfileRepository;
    }


    public CompanyProfile save(CompanyProfile companyProfile) {
        return companyProfileRepository.save(companyProfile);
    }

    @Transactional
    public CompanyProfile createCompany(final CompanyProfile companyProfile, final User user) {
        final Wallet wallet = user.getWallet();

        final BigDecimal aster = wallet.getAster();

        if (aster.compareTo(companyProfile.getCompanyBudget()) < 0) {
            throw new IllegalArgumentException();
        }

        final Optional<CompanyProfile> byCompanyName = companyProfileRepository.findByCompanyName(companyProfile.getCompanyName());

        if (byCompanyName.isPresent()) {
            throw new IllegalStateException();
        }

        final BigDecimal subtract = aster.subtract(companyProfile.getCompanyBudget());
        wallet.setAster(subtract);

        user.setCompanyProfile(companyProfile);
        return companyProfileRepository.save(companyProfile);
    }

    public List<CompanyProfile> findAll() {
        return companyProfileRepository.findAll();
    }

    public Optional<CompanyProfile> findById(String id) {
        return companyProfileRepository.findById(id);
    }

    public Optional<CompanyProfile> findByWorkProfile(final WorkProfile workProfile) {
        return companyProfileRepository.findById(workProfile.getCompanyProfile().getId());
    }
}
