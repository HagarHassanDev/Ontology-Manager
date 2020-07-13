package com.example.demo.ontology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class OntologyService {
	OntologyRepository or = new OntologyRepository();
	
	public List<Ontology> getAllOntologies() {
		List<Ontology> ontologyList = new ArrayList<Ontology>();
		ontologyList= or.getAllOntologies();
		return ontologyList; 
	}
	
	
	public List<Ontology> getOntologyByDomainId( String myid) {
		List<Ontology> ontologyList = new ArrayList<Ontology>();
		ontologyList= or.getOntologyByDomainId(myid);
		return ontologyList; 
	}
	
	
	public Ontology getOntologyById( String myid) {
		Ontology o = new Ontology();
		o = or.getOntologyById(myid);
		return o ; 
		
	
	}	
	public Ontology insertOntology( String myname ,  String domain ) throws IOException{
		Ontology o = new Ontology() ; 
		o = or.insertOntology( myname ,  domain );
		return o;
	}
	
	
	
	
	
	
	
	
}
