package com.project.security_bootstarter.controller;


import com.project.security_bootstarter.model.MyUser;
import com.project.security_bootstarter.service.JwtService;
import com.project.security_bootstarter.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getHello(){
        return ResponseEntity.ok("Hello srikar!@");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> loginUser(@RequestBody MyUser user){
        return new ResponseEntity<>(this.jwtService.generateToken(user), HttpStatus.OK);
    }

    @PostMapping("/auth/create")
    public ResponseEntity<String> createUser(@RequestBody MyUser user){
        this.userService.createUser(user);
        return new ResponseEntity<>(this.jwtService.generateToken(user), HttpStatus.CREATED);
    }
}
