package com.finki.ukim.mk.demo.domain.repository;

import com.finki.ukim.mk.demo.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}
