package com.club.repositories;

import com.club.entities.WorkProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkProfileRepository extends JpaRepository<WorkProfile, String> {

    @Query("from WorkProfile w where w.user is null")
    List<WorkProfile> getAllFreeWorkProfile();

}
