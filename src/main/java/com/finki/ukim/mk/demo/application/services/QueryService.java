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

    private String replaceFormatForModel(String format) {
        switch (format) {
            case "application/rdf+xml":
                return "RDF/XML";
            case "text/turtle":
                return "TURTLE";
            case "application/n-triples":
                return "N-Triples";
            default:
                return "TURTLE";
        }
    }

    private String convertModelToString(Model model, String format){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        model.write( byteArrayOutputStream, format);
        return byteArrayOutputStream.toString();
    }

    private String executeSelect(QueryExecution qexec, String format, boolean forHtml) {

            ResultSet rs = qexec.execSelect();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if(format.equals("application/json")){
                ResultSetFormatter.outputAsJSON(byteArrayOutputStream, rs);
                return byteArrayOutputStream.toString();
            }
            else if (format.equals("text/html")) {
                List<String> l=rs.getResultVars();
                String s = "";
                if (!forHtml) {
                    s += "<html><body>" + "\n";
                }
                s += "<div>" + "\n";
                s += "<table>" + "\n";
                s += "<tbody>" + "\n";
                s += "<tr>" + "\n";
                for(int i=0;i<l.size();i++)
                    s += "<th><p>"+l.get(i)+"</p></th>" + "\n";
                s += "</tr>" + "\n";

                while(rs.hasNext())
                {
                    QuerySolution qs=rs.nextSolution();
                    s += "<tr>" + "\n";
                    for(int i=0;i<l.size();i++)
                    {
                        String val=qs.get(l.get(i)).toString();
                        s += "<td><div>"+val+"</div></td>" + "\n";
                    }
                    s += "</tr>" + "\n";
                }
                s += "</tbody></table>" + "\n";
                s += "</div>" + "\n";

                if(!forHtml) {
                    s += "</body></html>" + "\n";
                }

                return s;
            }

            else if (format.equals("application/xml")) {
                ResultSetFormatter.outputAsXML(byteArrayOutputStream, rs);
                return byteArrayOutputStream.toString();
            }
            else if (format.equals("text/turtle")) {
                Model model = RDFOutput.encodeAsModel(rs);
                return convertModelToString(model, "TURTLE");

            }
            else if (format.equals("application/rdf+xml")){
                Model model = RDFOutput.encodeAsModel(rs);

                return convertModelToString(model, "RDF/XML");
            }
            else if (format.equals("application/n-triples")){
                Model model = RDFOutput.encodeAsModel(rs);
                return convertModelToString(model, "N-Triples");


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


    }

    public String getByQuery(String url, String defaultGraphSetIri,  String queryStr,  String format, int timeout, boolean forHtml, String queryType){
        try {
            Query query = QueryFactory.create(queryStr);
            ArrayList<String> defaultGraphIri = new ArrayList<String>();
            defaultGraphIri.add(defaultGraphSetIri);
            try (QueryExecution qexec = QueryExecutionFactory.sparqlService(url, query, defaultGraphIri, defaultGraphIri)) {
                ((QueryEngineHTTP) qexec).addParam("timeout", String.valueOf(timeout));


                if (queryType.equals("select")) {
                    return executeSelect(qexec, format, forHtml);
                } else if (queryType.equals("construct")) {
                    return convertModelToString(qexec.execConstruct(), replaceFormatForModel(format));
                } else if (queryType.equals("describe")) {
                    return convertModelToString(qexec.execDescribe(), replaceFormatForModel(format));
                } else if (queryType.equals("ask")) {
                    return qexec.execAsk() + "";
                }
                else {
                    return "Error";
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
