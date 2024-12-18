package com.project.security_bootstarter.service;

import com.project.security_bootstarter.dto.LoginDto;
import com.project.security_bootstarter.model.MyUser;
import com.project.security_bootstarter.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public UserService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepo userRepo) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    public String loginUser(LoginDto user){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if(auth.isAuthenticated()){
            var new_user = this.userRepo.findByUsername(user.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("cant find user"));
            return this.jwtService.generateToken(new_user);
        }

        return "failure!!";
    }

    public void createUser(MyUser user){
        user.setPassword(encoder.encode(user.getPassword()));
        this.userRepo.save(user);
    }
}
