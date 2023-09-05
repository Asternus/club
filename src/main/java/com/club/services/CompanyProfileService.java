package com.club.services;

import com.club.entities.CompanyProfile;
import com.club.entities.WorkProfile;
import com.club.repositories.CompanyProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
