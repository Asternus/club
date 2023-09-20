package com.club.repositories;

import com.club.entities.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, String> {
    Optional<CompanyProfile> findByCompanyName(String value);
}
