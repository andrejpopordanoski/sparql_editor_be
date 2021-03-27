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
    public ResponseEntity<String>  getByQuery(@RequestParam String url, @RequestParam String defaultGraphSetIri, @RequestParam String queryStr, @RequestParam String format, @RequestParam(required = false, defaultValue = "10000") int timeout) {

        String [] formatSplit = format.split("/");

        return ResponseEntity.ok().contentType(new MediaType(formatSplit[0], formatSplit[1])).body(queryService.getByQuery(url, defaultGraphSetIri, queryStr, format, timeout));
    }
//        try {
//            Query query = QueryFactory.create(queryStr);
//            ArrayList<String> defaultGraphIri =  new ArrayList<String>();
//            defaultGraphIri.add(defaultGraphSetIri);
//            User user = customUserDetailsService.getAuthenticatedUser();
//
//            try (QueryExecution qexec = QueryExecutionFactory.sparqlService(url, query, defaultGraphIri, defaultGraphIri)) {
//                ((QueryEngineHTTP) qexec).addParam("timeout", String.valueOf(timeout));
//                // Execute.
//                ResultSet rs = qexec.execSelect();
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                if(format.equals("application/json")){
//
//                    ResultSetFormatter.outputAsJSON(byteArrayOutputStream, rs);
//                    if(saveResults && user != null){
//                        userQueriesService.createNewUserQuery(user.getEmail(), url, defaultGraphSetIri, queryStr, format, timeout,(byteArrayOutputStream.toString() ));
//                    }
//
//                    return ResponseEntity.ok().contentType(new MediaType("application", "sparql-results+json")).body(byteArrayOutputStream.toString());
//                }
//                else if (format.equals("application/html")) {
//                    List<String> l=rs.getResultVars();
//                    String s = "";
//                    s += "<html><body bgcolor=\"#EAE6F5\">";
//                    s += "<h2 align=center><font color=\"#FF00FF\">SPARQL RESULT</font></h2>";
//                    s += "<table border=1 align=\"center\">";
//                    s += "<tr>";
//                    for(int i=0;i<l.size();i++)
//                        s += "<th bgcolor=\"#FFA500\"><fontsize=6>"+l.get(i)+"</font></th>";
//                    s += "</tr>";
//                    s += "<tbody bgcolor=\"#C0C0C0\">";
//
//                    while(rs.hasNext())
//                    {
//                        QuerySolution qs=rs.nextSolution();
//                        s += "<tr>";
//                        for(int i=0;i<l.size();i++)
//                        {
//                            String val=qs.get(l.get(i)).toString();
//                            s += "<td>"+val+"</td>";
//                        }
//                        s += "</tr>";
//                    }
//                    s += "</tbody></table>";
//                    s += "</body></html>";
//
//
//
//                    if(saveResults && user != null){
//                        userQueriesService.createNewUserQuery(user.getEmail(), url, defaultGraphSetIri, queryStr, format, timeout,s );
//                    }
//                    return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body( s);
//                }
//
//                else if (format.equals("application/xml")) {
//
//                    ResultSetFormatter.outputAsXML(byteArrayOutputStream, rs);
//
//                    if(saveResults && user != null){
//                        userQueriesService.createNewUserQuery(user.getEmail(), url, defaultGraphSetIri, queryStr, format, timeout,(byteArrayOutputStream.toString() ));
//                    }
//                    return ResponseEntity.ok().contentType(MediaType.TEXT_XML).body(byteArrayOutputStream.toString());
//                }
//                else if (format.equals("text/turtle")) {
////                    ResultSetFormatter.(byteArrayOutputStream, rs);
//                    Model model = RDFOutput.encodeAsModel(rs);
//
//                    model.write( byteArrayOutputStream, "TURTLE");
//
//                    if(saveResults && user != null){
//                        userQueriesService.createNewUserQuery(user.getEmail(), url, defaultGraphSetIri, queryStr, format, timeout,(byteArrayOutputStream.toString() ));
//                    }
//                    return ResponseEntity.ok().contentType(new MediaType("text", "turtle")).body(byteArrayOutputStream.toString());
//                }
//                else if (format.equals("application/rdf+xml")){
//                    Model model = RDFOutput.encodeAsModel(rs);
//
//                    model.write( byteArrayOutputStream, "RDF/XML");
//
//                    if(saveResults && user != null){
//                        userQueriesService.createNewUserQuery(user.getEmail(), url, defaultGraphSetIri, queryStr, format, timeout,(byteArrayOutputStream.toString() ));
//                    }
//                    return ResponseEntity.ok().contentType(new MediaType("application", "rdf+xml")).body(byteArrayOutputStream.toString());
//                }
//                else if (format.equals("application/n-triples")){
//                    Model model = RDFOutput.encodeAsModel(rs);
//
//                    model.write( byteArrayOutputStream, "N-Triples");
//                    if(saveResults && user != null){
//                        userQueriesService.createNewUserQuery(user.getEmail(), url, defaultGraphSetIri, queryStr, format, timeout,(byteArrayOutputStream.toString() ));
//                    }
//                    return ResponseEntity.ok().contentType(new MediaType("text", "plain")).body(byteArrayOutputStream.toString());
//                }
//                else if (format.equals("text/csv")){
//                    ResultSetFormatter.outputAsCSV(byteArrayOutputStream, rs);
//
//                    if(saveResults && user != null){
//                        userQueriesService.createNewUserQuery(user.getEmail(), url, defaultGraphSetIri, queryStr, format, timeout,(byteArrayOutputStream.toString() ));
//                    }
//                    return ResponseEntity.ok().contentType(new MediaType("text", "csv")).body(byteArrayOutputStream.toString());
//                }
//
//                else if (format.equals("text/tab-separated-values")){
//                    ResultSetFormatter.outputAsTSV(byteArrayOutputStream, rs);
//                    if(saveResults && user != null){
//                        userQueriesService.createNewUserQuery(user.getEmail(), url, defaultGraphSetIri, queryStr, format, timeout,(byteArrayOutputStream.toString() ));
//                    }
//                    return ResponseEntity.ok().contentType(new MediaType("text", "tab-separated-values")).body(byteArrayOutputStream.toString());
//                }
//
//                else {
//
//                    return ResponseEntity.badRequest().contentType(new MediaType("text", "plain")).body("Something went wrong with content-type header");
//
//                }
//
//            } catch (Exception e) {
//                System.out.println("Exception happened");
//                return  ResponseEntity.ok().body(e.getMessage());
//            }
//        }
//        catch (QueryParseException e){
//
//            return ResponseEntity.ok().body(e.getLocalizedMessage());
//        }
//
//    }
}
