package com.example.demo.terminology;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import com.example.demo.OntologyManagerWithFusekiApplication;

public class TerminologyRepesitory {
	
	public List<Terminology> getTerminologyForSpecificConcept(String cid ) {
		List<Terminology> terminologylist = new ArrayList<Terminology>();
		try{			
			String queryString = 
					 "PREFIX ns:<"+OntologyManagerWithFusekiApplication.ns+">"
					+ "PREFIX rdf:<"+OntologyManagerWithFusekiApplication.rdf+">" 
					+ "SELECT ?term ?d " 
					+ "WHERE {"
					+ "?y ns:id \""+cid+"\"."
					+ "?y ns:has_term ?t."
					+ "?t ns:termName ?term."
					+ "?t ns:termDef ?d."
					+ "}";
			
			ResultSet results = OntologyManagerWithFusekiApplication.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI,queryString);


			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("term");
				org.apache.jena.rdf.model.Literal ind3 = result.getLiteral("d");

				String a = ind2.asLiteral().getString();
				String b = ind3.asLiteral().getString();
				Terminology t = new Terminology();
				
				t.setTerminologyName(a);
				t.setTerminologyDefinition(b);
				terminologylist.add(t);
			}
			return terminologylist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ; 
	}
	
	public Terminology getTerminologyDetails(String t ) {
		Terminology term = new Terminology();
		try{			
			String queryString = 
					  "PREFIX ns:<"+OntologyManagerWithFusekiApplication.ns+">"
					+ "PREFIX rdf:<"+OntologyManagerWithFusekiApplication.rdf+">" 
					+ "SELECT ?term ?d " 
					+ "WHERE {"
					+ "?x rdf:type ns:Terminology."
					+ "?x ns:termId \""+t+"\"."
					+ "?x ns:termName ?term."
					+ "?x ns:termDef ?d."
					+ "}";
			
			ResultSet results = OntologyManagerWithFusekiApplication.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI,queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("term");
				org.apache.jena.rdf.model.Literal ind3 = result.getLiteral("d");
				String a = ind2.asLiteral().getString();
				String b = ind3.asLiteral().getString();
				term.setTerminologyName(a);
				term.setTerminologyDefinition(b);
			}
			return term;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ; 
	}
	
	
	
	
	
}
