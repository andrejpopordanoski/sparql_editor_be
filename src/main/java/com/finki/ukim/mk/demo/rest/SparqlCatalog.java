package com.finki.ukim.mk.demo.rest;

import com.finki.ukim.mk.demo.application.services.QueryService;
import com.finki.ukim.mk.demo.application.services.UserQueriesService;
import com.finki.ukim.mk.demo.application.services.security.CustomUserDetailsService;
import com.finki.ukim.mk.demo.domain.model.User;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.sparql.resultset.RDFOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller

public class SparqlCatalog {

    @Autowired
    UserQueriesService userQueriesService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    QueryService queryService;


    @PostMapping(value = "/sparql")
    @ResponseBody
    public ResponseEntity<String>  getByQuery(@RequestParam String url, @RequestParam String defaultGraphSetIri, @RequestParam String queryStr, @RequestParam String format, @RequestParam(required = false, defaultValue = "10000") int timeout, @RequestParam(required = false, defaultValue = "false") boolean forHtml, @RequestParam String queryType) {

        String [] formatSplit = format.split("/");
        return ResponseEntity.ok().contentType(new MediaType(formatSplit[0], formatSplit[1])).body(queryService.getByQuery(url, defaultGraphSetIri, queryStr, format, timeout, forHtml, queryType));
    }

}
