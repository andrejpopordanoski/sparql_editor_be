package com.finki.ukim.mk.demo.domain.repository;

import com.finki.ukim.mk.demo.domain.model.User;
import com.finki.ukim.mk.demo.domain.model.UserQueries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserQueriesRepository extends JpaRepository<UserQueries, Long> {
    List<UserQueries> findAllByUserEmail(String email);

}
