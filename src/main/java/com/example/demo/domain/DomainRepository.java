package com.example.demo.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.vocabulary.OWL2;

import com.example.demo.OntologyManagerWithFusekiApplication;

public class DomainRepository {
	
	public List<Domain> getAllDomains() {
		List<Domain> domainlist = new ArrayList<Domain>();
	try {
		String queryString = 
				"PREFIX ns: <"+OntologyManagerWithFusekiApplication.ns +">"
				+ "PREFIX rdf: <"+OntologyManagerWithFusekiApplication.rdf+">" 
				+ "SELECT  ?id ?Individuals ?name "
				+ "WHERE {" + "?Individuals rdf:type ns:Domain." + "?Individuals ns:domainId ?id."
				+ "?Individuals ns:domainName ?name." + "}";
		ResultSet results = OntologyManagerWithFusekiApplication.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI,queryString);


		while (results.hasNext()) {
			QuerySolution result = (results.next());
			org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("id");
			org.apache.jena.rdf.model.Literal ind = result.getLiteral("name");
			String var1 = ind.asLiteral().getString();
			String var2 = ind2.asLiteral().getString();
			Domain d = new Domain(var2, var1);
			domainlist.add(d);
		}
		return domainlist;
	}catch(Exception e) {
		e.printStackTrace();
	}
	return null ; 
	}
	
	public Domain getDomainById(String myid){
		Domain d= new Domain();
		try {
			String queryString =
					 "PREFIX ns:<"+OntologyManagerWithFusekiApplication.ns+">"
					+ "PREFIX rdf:<"+OntologyManagerWithFusekiApplication.rdf+">" 
				    + "SELECT ?name ?id" + "WHERE {"
					+ "?Individuals rdf:type ns:Domain." 
					+ "?Individuals ns:domainId \"" + myid + "\"." 
					+ "?Individuals ns:domainName ?name."
					+ "}";

			ResultSet results = OntologyManagerWithFusekiApplication.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI,queryString);


			while (results.hasNext()) {
				QuerySolution result = (results.next());
				 Literal ind = result.getLiteral("name");
				
				String a = ind.asLiteral().getString();
				d.setDomainId(myid);
				d.setDomainName(a);
			}
			
			return d; 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
return null ; 		
		
	}
	

	public Domain insertDomain(String myname) throws IOException {
		
		Domain c = new Domain();
		
		  RDFConnection conn1 = RDFConnectionFactory.connect("http://localhost:3030/eduDataSet/") ;
		  Model model =  conn1.fetch();
		try {
			OntClass domainClass = OntologyManagerWithFusekiApplication.model
					.getOntClass(OntologyManagerWithFusekiApplication.ns + "Domain");
			
			String domainSub = myname.substring(0 , 2 );
			String domaincostmizeCode =  domainSub;
			
			Individual i = OntologyManagerWithFusekiApplication.model
					.createIndividual(OntologyManagerWithFusekiApplication.ns + domaincostmizeCode, domainClass);
			i.addRDFType(OWL2.NamedIndividual);
			
			DatatypeProperty pname = OntologyManagerWithFusekiApplication.model
					.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "domainName");
			i.addProperty(pname, model.createLiteral(myname));

			DatatypeProperty pcode = OntologyManagerWithFusekiApplication.model
					.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "domainId");
			i.addProperty(pcode,model.createLiteral(domaincostmizeCode));

			
			
			c.setDomainId(domaincostmizeCode);
			c.setDomainName(myname);
			
			conn1.put( model);
			return c;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
}
