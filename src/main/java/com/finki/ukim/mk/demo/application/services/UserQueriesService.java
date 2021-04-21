package com.finki.ukim.mk.demo.application.services;

import com.finki.ukim.mk.demo.domain.model.UserQueries;
import com.finki.ukim.mk.demo.domain.repository.UserQueriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserQueriesService {

    @Autowired
    UserQueriesRepository userQueriesRepository;

    public Long createNewUserQuery(String userEmail, String url, String defaultDatasetName, String queryString, String format, int timeout, String queryResult, String queryName, boolean privateAccess){
        UserQueries userQuery = new UserQueries();
        userQuery.setUserEmail(userEmail);
        userQuery.setUrl(url);
        userQuery.setPrivateAccess(privateAccess);

        userQuery.setDefaultDatasetName(defaultDatasetName);


        userQuery.setQueryString(queryString);

        if(queryResult.length() < 16000000) {
            userQuery.setQueryResult(queryResult);
        }
        userQuery.setFormat(format);
        userQuery.setTimeout(timeout);
        List<UserQueries> queriesWithSameName = userQueriesRepository.findAllByQueryName(queryName);
        if(queriesWithSameName.size() == 0 ) {
            userQuery.setQueryName(queryName);
        }
        else {
            userQuery.setQueryName(queryName);
            userQuery.setQueryNameSuffix("(" + queriesWithSameName.size() + ")");

        }

        userQueriesRepository.saveAndFlush(userQuery);
        return userQuery.getId();
    }

    public Page<UserQueries> getAllQueriesForUser(String email, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return userQueriesRepository.findAllByUserEmail(email, pageable);
    }

    public Page<UserQueries> getAllPublicQueries(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        return userQueriesRepository.findAllByPrivateAccess(false,pageable);
    }

    public UserQueries getSingleUserQuery(Long queryId){
        return userQueriesRepository.findById(queryId).get();
    }

    public String getQueryResult(Long id){
        return userQueriesRepository.findById(id).get().getQueryResult();
    }
}
