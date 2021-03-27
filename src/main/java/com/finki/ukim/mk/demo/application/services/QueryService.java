package com.finki.ukim.mk.demo.application.services;

import com.finki.ukim.mk.demo.domain.model.User;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.sparql.resultset.RDFOutput;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class QueryService {
    public String getByQuery(String url, String defaultGraphSetIri,  String queryStr,  String format, int timeout){
        try {
            Query query = QueryFactory.create(queryStr);
            ArrayList<String> defaultGraphIri =  new ArrayList<String>();
            defaultGraphIri.add(defaultGraphSetIri);


            try (QueryExecution qexec = QueryExecutionFactory.sparqlService(url, query, defaultGraphIri, defaultGraphIri)) {
                ((QueryEngineHTTP) qexec).addParam("timeout", String.valueOf(timeout));
                // Execute.
                ResultSet rs = qexec.execSelect();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if(format.equals("application/json")){

                    ResultSetFormatter.outputAsJSON(byteArrayOutputStream, rs);

                    return byteArrayOutputStream.toString();
                }
                else if (format.equals("text/html")) {
                    List<String> l=rs.getResultVars();
                    String s = "";
                    s += "<html><body bgcolor=\"#EAE6F5\">";
                    s += "<h2 align=center><font color=\"#FF00FF\">SPARQL RESULT</font></h2>";
                    s += "<table border=1 align=\"center\">";
                    s += "<tr>";
                    for(int i=0;i<l.size();i++)
                        s += "<th bgcolor=\"#FFA500\"><fontsize=6>"+l.get(i)+"</font></th>";
                    s += "</tr>";
                    s += "<tbody bgcolor=\"#C0C0C0\">";

                    while(rs.hasNext())
                    {
                        QuerySolution qs=rs.nextSolution();
                        s += "<tr>";
                        for(int i=0;i<l.size();i++)
                        {
                            String val=qs.get(l.get(i)).toString();
                            s += "<td>"+val+"</td>";
                        }
                        s += "</tr>";
                    }
                    s += "</tbody></table>";
                    s += "</body></html>";

                    return s;
                }

                else if (format.equals("application/xml")) {

                    ResultSetFormatter.outputAsXML(byteArrayOutputStream, rs);
                    return byteArrayOutputStream.toString();
                }
                else if (format.equals("text/turtle")) {
//                    ResultSetFormatter.(byteArrayOutputStream, rs);
                    Model model = RDFOutput.encodeAsModel(rs);

                    model.write( byteArrayOutputStream, "TURTLE");

                    return byteArrayOutputStream.toString();

                }
                else if (format.equals("application/rdf+xml")){
                    Model model = RDFOutput.encodeAsModel(rs);

                    model.write( byteArrayOutputStream, "RDF/XML");

                    return byteArrayOutputStream.toString();

                }
                else if (format.equals("application/n-triples")){
                    Model model = RDFOutput.encodeAsModel(rs);

                    model.write( byteArrayOutputStream, "N-Triples");
                    return byteArrayOutputStream.toString();

                }
                else if (format.equals("text/csv")){
                    ResultSetFormatter.outputAsCSV(byteArrayOutputStream, rs);

                    return byteArrayOutputStream.toString();

                }

                else if (format.equals("text/tab-separated-values")){
                    ResultSetFormatter.outputAsTSV(byteArrayOutputStream, rs);
                    return byteArrayOutputStream.toString();
                }

                else {
                    return "Something went wrong with content-type header";

                }

            } catch (Exception e) {
                System.out.println("Exception happened");
                return e.getMessage();
            }
        }
        catch (QueryParseException e){

            return e.getLocalizedMessage();
        }

    }
}
