package com.example.demo.ontology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.vocabulary.OWL2;

import com.example.demo.OntologyManagerWithFusekiApplication;


public class OntologyRepository {
	
	public List<Ontology> getAllOntologies() {
		List<Ontology> ontologyList = new ArrayList<Ontology>();
		try {
			String queryString = 
					  "PREFIX ns: <"+OntologyManagerWithFusekiApplication.ns+">"
					+ "PREFIX rdf: <"+OntologyManagerWithFusekiApplication.rdf+">" 
					+ "SELECT  ?id ?Individuals ?name "
					+ "WHERE {" 
					+ "?Individuals rdf:type ns:Ontology." 
					+ "?Individuals ns:ontologyId ?id." 
					+ "?Individuals ns:ontologyName ?name."
					+ "}";

			ResultSet results = OntologyManagerWithFusekiApplication.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI,queryString);


			while (results.hasNext()) {
				QuerySolution result = (results.next());
				 
				org.apache.jena.rdf.model.Literal ind = result.getLiteral("id");
				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("name");
								
				String var1 = ind.asLiteral().getString();
				String var2 = ind2.asLiteral().getString();
				
				
Ontology o = new Ontology(var1 , var2); 

				 ontologyList.add(o);
			}
				
			
			return ontologyList; 
		}catch(Exception e) {
			e.printStackTrace();
		}
return null ; 
		
		
	}
	
	public List<Ontology> getOntologyByDomainId(String myid) {
		List<Ontology> ontologyList = new ArrayList<Ontology>();
		try {
			
			String queryString = 
					 "PREFIX ns:<"+OntologyManagerWithFusekiApplication.ns+">"
					+ "PREFIX rdf:<"+OntologyManagerWithFusekiApplication.rdf+">" 
					+ "SELECT ?id ?x ?name "
					+ "WHERE {"
					+ "?Individuals rdf:type ns:Domain."
					+ "?Individuals ns:domainId \"" + myid + "\"."
					+ "?Individuals ns:has ?y" + "."
					+ "?y rdf:type ns:Ontology." 
					+ "?y ns:ontologyId ?x."
					+ "?y ns:ontologyName ?name."
					+ "}";

			ResultSet results = OntologyManagerWithFusekiApplication.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI,queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal var2 = result.getLiteral("x");
				org.apache.jena.rdf.model.Literal var = result.getLiteral("name");

				String a= var.asLiteral().getString();
				String b= var2.asLiteral().getString();
				
				Ontology o = new Ontology(b , a);
			
				
				ontologyList.add(o);
			}
			return ontologyList; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
return null ;		

	}
	
	public Ontology getOntologyById(String myid ) {
		Ontology o = new Ontology();
		try {
			String queryString =
					"PREFIX ns:<"+OntologyManagerWithFusekiApplication.ns+">"
					+ "PREFIX rdf:<"+OntologyManagerWithFusekiApplication.rdf+">" 
					+ "SELECT ?id ?Individuals ?name " 
					+ "WHERE {"
					+ "?Individuals rdf:type ns:Ontology." 
					+ "?Individuals ns:ontologyId \"" + myid + "\"." 
					+ "?Individuals ns:ontologyId ?id."
					+ "?Individuals ns:ontologyName ?name."
					+ "}";

			ResultSet results = OntologyManagerWithFusekiApplication.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI,queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("id");
				org.apache.jena.rdf.model.Literal ind3 = result.getLiteral("name");

				
				String b = ind2.asLiteral().getString();
			    String c = ind3.asLiteral().getString();
				 
			    o.setOntologyId(b);
			    o.setOntologyName(c);
				
			}
			return o ; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
		
	}
	
	
	

	public Ontology insertOntology(String myname, String domain) throws IOException {
		
		Ontology c = new Ontology();
		
		  RDFConnection conn1 = RDFConnectionFactory.connect("http://localhost:3030/eduDataSet/") ;
		  Model model =  conn1.fetch();
		try {
			OntClass ontologyClass = OntologyManagerWithFusekiApplication.model
					.getOntClass(OntologyManagerWithFusekiApplication.ns + "Ontology");
			
			String ontologySub = myname.trim().substring(0, 2);
			String domainSub = domain.substring(0 , 2 );
			String ontologycostmizeCode = ontologySub + "-" + domainSub;
			
			Individual i = OntologyManagerWithFusekiApplication.model
					.createIndividual(OntologyManagerWithFusekiApplication.ns + ontologycostmizeCode, ontologyClass);
			i.addRDFType(OWL2.NamedIndividual);
			
			DatatypeProperty pname = OntologyManagerWithFusekiApplication.model
					.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "ontologyName");
			i.addProperty(pname, model.createLiteral(myname));

			DatatypeProperty pcode = OntologyManagerWithFusekiApplication.model
					.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "ontologyId");
			i.addProperty(pcode,model.createLiteral(ontologycostmizeCode));

			OntClass domainClass = OntologyManagerWithFusekiApplication.model
					.getOntClass(OntologyManagerWithFusekiApplication.ns + "Domain");
			
			
			Individual i3 = OntologyManagerWithFusekiApplication.model
					.createIndividual(OntologyManagerWithFusekiApplication.ns + domain  ,   domainClass);
			i3.addRDFType(OWL2.NamedIndividual);
			ObjectProperty oRel = OntologyManagerWithFusekiApplication.model.getObjectProperty(OntologyManagerWithFusekiApplication.ns + "part_of");
		oRel.hasRDFType(OntologyManagerWithFusekiApplication.ns + i3);
		i.addProperty(oRel , i3);
		
		oRel.hasRDFType(OntologyManagerWithFusekiApplication.ns+i3);
		i.addProperty(oRel , i3);
		
			
			
			c.setOntologyId(ontologycostmizeCode);
			c.setOntologyName(myname);
			
			conn1.put( model);
			return c;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
}
