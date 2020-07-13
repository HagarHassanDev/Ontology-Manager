package com.example.demo.ontology;

public class Ontology {
	private String OntologyId ; 
	private String OntologyName ;
	public String getOntologyId() {
		return OntologyId;
	}
	public void setOntologyId(String ontologyId) {
		OntologyId = ontologyId;
	}
	public String getOntologyName() {
		return OntologyName;
	}
	public void setOntologyName(String ontologyName) {
		OntologyName = ontologyName;
	}
	@Override
	public String toString() {
		return "OntologyController [OntologyId=" + OntologyId + ", OntologyName=" + OntologyName + "]";
	}
	public Ontology(String ontologyId, String ontologyName) {
		super();
		OntologyId = ontologyId;
		OntologyName = ontologyName;
	} 
	public Ontology() {
	
	}
	
}
