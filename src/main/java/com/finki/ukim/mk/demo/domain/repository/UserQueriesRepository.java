package com.finki.ukim.mk.demo.domain.repository;

import com.finki.ukim.mk.demo.domain.model.User;
import com.finki.ukim.mk.demo.domain.model.UserQueries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserQueriesRepository extends JpaRepository<UserQueries, Long> {
    Page<UserQueries> findAllByUserEmail(String email, Pageable pageable);
    List<UserQueries> findAllByQueryName(String name);
    Page<UserQueries> findAllByPrivateAccess(Boolean access, Pageable pageable);

}
