package com.luv2code.jobportal.services;

import com.luv2code.jobportal.entity.RecruiterProfile;
import com.luv2code.jobportal.repository.RecruterProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private final RecruterProfileRepository recruterProfileRepository;

    @Autowired
    public RecruiterProfileService(RecruterProfileRepository recruterProfileRepository) {
        this.recruterProfileRepository = recruterProfileRepository;
    }
public Optional<RecruiterProfile> getOne(Integer id){
        return  recruterProfileRepository.findById(id);
}

    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return recruterProfileRepository.save(recruiterProfile);
    }
}
