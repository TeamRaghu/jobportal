package com.luv2code.jobportal.services;

import com.luv2code.jobportal.entity.JobSeekerProfile;
import com.luv2code.jobportal.entity.RecruiterProfile;
import com.luv2code.jobportal.entity.Users;
import com.luv2code.jobportal.repository.JobSeekerRepository;
import com.luv2code.jobportal.repository.RecruterProfileRepository;
import com.luv2code.jobportal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private  final RecruterProfileRepository recruterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, JobSeekerRepository jobSeekerRepository, RecruterProfileRepository recruterProfileRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.recruterProfileRepository = recruterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public Users addNewUser(Users users){
    users.setActive(true);
    users.setRegistrationDate(new Date(System.currentTimeMillis()));
    users.setPassword(passwordEncoder.encode(users.getPassword()));
    int userTypeId= users.getUserTypeId().getUserTypeId();

    if(userTypeId==1) {
        users.getUserTypeId().setUserTypeName("Recruiter");
        recruterProfileRepository.save(new RecruiterProfile(users));
    }
    else {
        users.getUserTypeId().setUserTypeName("Candidate");
        jobSeekerRepository.save(new JobSeekerProfile(users));
    }
        Users savedUser= usersRepository.save(users);
    return savedUser;
    }


    public Optional<Users> getUserByEmail(String email){
    return usersRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {
        Authentication authentication= getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username =authentication.getName();
           Users users= usersRepository.findByEmail(username).orElseThrow(
                    ()->new UsernameNotFoundException("Could not Found user"));
           int userId = users.getUserID();
           if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
             return   recruterProfileRepository.findById(userId).orElse(new RecruiterProfile());
           }else{
               return jobSeekerRepository.findById(userId).orElse(new JobSeekerProfile());
           }
        }
        return null;
    }

    public Users getCurrentUser() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof  AnonymousAuthenticationToken)){
           String userName= authentication.getName();
            Users users= usersRepository.findByEmail(userName).orElseThrow(
                    ()->new UsernameNotFoundException("Could not Found user"));
            return  users;
        }
        return  null;
    }
}
