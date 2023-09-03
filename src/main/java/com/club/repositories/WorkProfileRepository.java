package com.club.repositories;

import com.club.entities.WorkProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkProfileRepository extends JpaRepository<WorkProfile, String> {
}
