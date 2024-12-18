package com.project.security_bootstarter.controller;


import com.project.security_bootstarter.dto.LoginDto;
import com.project.security_bootstarter.model.MyUser;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getHello(){
        return ResponseEntity.ok("Hello srikar!@");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDto user) {
        var response = this.userService.loginUser(user);
        if (response.isEmpty()) return ResponseEntity.status(404).body("No user");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/create")
    public ResponseEntity<String> createUser(@RequestBody MyUser user){
        this.userService.createUser(user);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }
}
