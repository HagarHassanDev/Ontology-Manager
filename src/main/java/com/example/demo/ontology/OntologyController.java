package com.example.demo.ontology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class OntologyController {
	
	OntologyService os = new OntologyService();
	
	@RequestMapping(value = "/api/ontologies" , method= RequestMethod.GET)
	public List<Ontology> getAllOntologies(@RequestParam(value = "domainId" , required = false)String myid) {
		List<Ontology> ontologyList = new ArrayList<Ontology>();
		if(myid !=null) {
			ontologyList= os.getOntologyByDomainId(myid);
		}else {
		ontologyList= os.getAllOntologies();
		
		}return ontologyList; 
	}
	
	
	@RequestMapping(value = "/api/ontologies/{id}", method = RequestMethod.GET)
	public Ontology getOntologyById(@PathVariable("id") String myid) {
	Ontology o = new Ontology(); 
	o = os.getOntologyById(myid);
	return o ; 
		
		
	}
	
	
	@ApiIgnore
	@RequestMapping(method = RequestMethod.POST , value = "/api/ontologies/insert/{ontologyName}/{cdomain}")
	public Ontology insertOntology( @PathVariable("ontologyName")String myname, @PathVariable("cdomain") String domain  ) throws IOException{
		Ontology o = new Ontology() ; 
		o = os.insertOntology( myname ,  domain );
		return o;
	}
	
	
	
}
