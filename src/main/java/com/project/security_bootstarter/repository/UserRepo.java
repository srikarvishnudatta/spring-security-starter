package com.project.security_bootstarter.repository;

import com.project.security_bootstarter.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String email);
}
