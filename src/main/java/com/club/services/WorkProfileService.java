package com.club.services;

import com.club.entities.User;
import com.club.entities.WorkProfile;
import com.club.repositories.UserRepository;
import com.club.repositories.WorkProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkProfileService {

    private final WorkProfileRepository workProfileRepository;

    private final UserRepository userRepository;

    @Autowired
    public WorkProfileService(WorkProfileRepository workProfileRepository, UserRepository userRepository) {
        this.workProfileRepository = workProfileRepository;
        this.userRepository = userRepository;
    }

    public List<WorkProfile> getWorksProfiles(final User user) {
        final User myUser = userRepository.findById(user.getId()).orElseThrow();
        return myUser.getWorkProfiles();
    }

    public WorkProfile getWorksProfileById(final String id) {
        final WorkProfile workProfile = workProfileRepository.findById(id).orElseThrow();
        return workProfile;
    }

    public void saveWorkProfile(final WorkProfile workProfile) {
        workProfileRepository.save(workProfile);
    }

    public void saveAllWorkProfiles(final List<WorkProfile> workProfiles) {
        workProfileRepository.saveAll(workProfiles);
    }

    public List<WorkProfile> getAllFreeWorkProfiles() {
        return workProfileRepository.getAllFreeWorkProfile();
    }
}
