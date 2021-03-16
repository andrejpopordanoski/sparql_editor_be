package com.finki.ukim.mk.demo.rest;

import org.apache.jena.query.*;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

@Controller

public class SparqlCatalog {
    @PostMapping(value = "/sparql")
    @ResponseBody
    public ResponseEntity<String> getByQuery(@RequestParam String queryStr, @RequestParam String format){
        try {
            Query query = QueryFactory.create(queryStr);

            try (QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query)) {
                // Set the DBpedia specific timeout.
                ((QueryEngineHTTP) qexec).addParam("timeout", "10000");

                // Execute.
                ResultSet rs = qexec.execSelect();
//                String text = ResultSetFormatter.asText(rs);
//                String json = ResultSetFormatter.asXMLString(rs);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ResultSetFormatter.outputAsJSON(byteArrayOutputStream, rs);
//                ResponseEntity<String> responseEntity;
                if(format.equals("application/json")){
                    return ResponseEntity.ok().contentType(new MediaType("application", "sparql-results+json")).body(byteArrayOutputStream.toString());
                }
                else {
                    return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body( "<html>\n" + "<header><title>Welcome</title></header>\n" + "<body>\n" + "Hello world\n" + "</body>\n" + "</html>");
                }


            } catch (Exception e) {
                return  ResponseEntity.ok().body(e.getMessage());
            }
        }
        catch (QueryParseException e){

            return ResponseEntity.ok().body(e.getLocalizedMessage());
        }

//        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
//                "<body>\n" + "Hello world\n" + "</body>\n" + "</html>";


    }
}
