package com.project.security_bootstarter.service;

import com.project.security_bootstarter.model.MyUser;
import com.project.security_bootstarter.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void validateUser(MyUser user){

    }

    public void createUser(MyUser user){
        user.setPassword(encoder.encode(user.getPassword()));
        this.userRepo.save(user);
    }
}
