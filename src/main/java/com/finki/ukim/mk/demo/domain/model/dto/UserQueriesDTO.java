package com.finki.ukim.mk.demo.domain.model.dto;

import com.finki.ukim.mk.demo.domain.model.UserQueries;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQueriesDTO {
    public int totalPages;
    List<UserQueries> userQueries;
}
