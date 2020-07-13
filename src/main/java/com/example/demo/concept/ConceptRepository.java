package com.example.demo.concept;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.vocabulary.OWL2;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.OntologyManagerWithFusekiApplication;
import com.example.demo.terminology.Terminology;

public class ConceptRepository {

	public List<Map<String , Object>> getAllConcepts() {
		List<Map<String , Object>> l = new ArrayList<>();
		try {
			String queryString = "PREFIX ns: <" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf: <"
					+ OntologyManagerWithFusekiApplication.rdf + ">" 
					+ "SELECT  ?id ?Name " + "WHERE {"
					+ "?Individuals rdf:type ns:Concept." 
					+"?Individuals ns:id ?id."
					+"?Individuals ns:name ?Name."
					+"}";
			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				Literal ind = result.getLiteral("id");
	Literal ind2 = result.getLiteral("Name");
				
				String a = ind.asLiteral().getString();	
				String b = ind2.asLiteral().getString();
				
				Map<String , Object> jsParams = new HashMap<>();

				jsParams.put("conceptName", b);
				jsParams.put("conceptId", a);
				l.add(jsParams);

				
			}

			return l;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String , Object>> getConceptsByOntologyId(@RequestParam(value = "ontologyId") String myid) {
		List<Map<String , Object>> conceptList = new ArrayList<>();

		try {

			String queryString = "PREFIX ns:<" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf:<"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "SELECT ?id ?name " + "WHERE {"
					+ "?Individuals rdf:type ns:Ontology." + "?Individuals ns:ontologyId \"" + myid + "\"."
					+ "?Individuals ns:onthas ?x" + "." 
					+ "?x ns:id ?id." 
					+ "?x ns:name ?name."
					+ "}";
			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal var2 = result.getLiteral("id");
				org.apache.jena.rdf.model.Literal var3 = result.getLiteral("name");

				String b = var2.asLiteral().getString();
				String c = var3.asLiteral().getString();

				Map<String , Object> jsParams = new HashMap<>();

				jsParams.put("conceptId", b);
				jsParams.put("conceptName", c);
				
				conceptList.add(jsParams);
			}

			return conceptList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<String> getConceptByDomainId(String myid) {
		List<String> conceptList = new ArrayList<String>();
		try {
			String queryString = "PREFIX ns:<" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf:<"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "SELECT ?id " + "WHERE {"
					+ "?Individuals rdf:type ns:Domain." + "?Individuals ns:domainId \"" + myid + "\"."
					+ "?Individuals ns:has ?y." + "?y rdf:type ns:Ontology." + "?y ns:onthas ?x."
					+ "?x rdf:type ns:Concept." + "?x ns:id ?id." + "}";
			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal var3 = result.getLiteral("id");
				String c = var3.asLiteral().getString();
				conceptList.add(c);
			}

			return conceptList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Concept getConceptById(String myid) {
		Concept concept = new Concept();
		try {
			String queryString = "PREFIX ns:<" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf:<"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "SELECT ?id ?name ?title ?depth " + "WHERE {"
					+ "?Individuals rdf:type ns:Concept." + "?Individuals ns:id \"" + myid + "\"."
					+ "?Individuals ns:id ?id." + "?Individuals ns:name ?name." + "?Individuals ns:description ?title."
					+ "?Individuals ns:depth_level ?depth." + "}";

			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("id");
				org.apache.jena.rdf.model.Literal ind3 = result.getLiteral("name");
				org.apache.jena.rdf.model.Literal ind4 = result.getLiteral("title");
				org.apache.jena.rdf.model.Literal ind5 = result.getLiteral("depth");

				String b = ind2.asLiteral().getString();
				String c = ind3.asLiteral().getString();
				String d = ind4.asLiteral().getString();
				String e = ind5.asLiteral().getString();

				concept.setId(b);
				concept.setName(c);
				concept.setDescription(d);
				concept.setDepth(e);
			}

			return concept;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getConceptByIdInSpecificOntology(String cid, String myid) {
		try {
			String queryString = "PREFIX ns:<" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf:<"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "SELECT ?id " + "WHERE {"
					+ "?Individuals rdf:type ns:Ontology." + "?Individuals ns:ontologyId \"" + myid + "\"."
					+ "?Individuals ns:onthas ?y" + "." + "?x ns:id \"" + cid + "\"." + "?x ns:id ?id." + "}";
			String concpetID = null;
			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("id");
				concpetID = ind2.asLiteral().getString();
			}
			return concpetID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getConceptHasTermInSpecificOntology(String cid, String myid) {
		List<String> term = new ArrayList<>();
		try {
			String queryString = "PREFIX ns:<" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf:<"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "SELECT ?id " + "WHERE {"
					+ "?Individuals rdf:type ns:Ontology." + "?Individuals ns:ontologyId \"" + myid + "\"."
					+ "?Individuals ns:onthas ?y" + "." + "?y ns:id \"" + cid + "\"." + "?y ns:has_term ?t."
					+ "?t ns:termId ?id." + "}";
			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				Literal t = result.getLiteral("id");
				String x = t.asLiteral().getString();
				term.add(x);
			}
			return term;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getConceptIsAForSpecificOntology(String cid, String myid) {
		List<String> c = new ArrayList<>();
		try {
			String queryString = 
					  "PREFIX ns:<" + OntologyManagerWithFusekiApplication.ns + ">" 
			        + "PREFIX rdf:<" + OntologyManagerWithFusekiApplication.rdf + ">" 
			        + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>" 
					+ "SELECT ?x ?id " 
					+ "WHERE {"
					+ "?Individuals rdf:type ns:Ontology."
					+ "?Individuals ns:ontologyId \"" + myid + "\"."
					+ "?Individuals ns:onthas ?y" + "." 
					+ "?y ns:id \"" + cid + "\"." 
					+ "?y ns:is_a ?x."
					+ "?x ns:id ?id." 
			+ "}";
			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("id");
				String	id = ind2.asLiteral().getString();
				c.add(id);
			}

			return c;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getConceptPrerequisiteForSpecificOntology(String cid, String myid) {
		List<String> conceptList = new ArrayList<String>();

		try {

			String queryString = "PREFIX ns:<" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf:<"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "SELECT ?id " + "WHERE {"
					+ "?Individuals rdf:type ns:Ontology." + "?Individuals ns:ontologyId \"" + myid + "\"."
					+ "?Individuals ns:onthas ?y" + "." + "?y ns:id \"" + cid + "\"." + "?y ns:pre_requisite ?x."
					+ "?x ns:id ?id." + "}";

			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());

				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("id");
				String b = ind2.asLiteral().getString();

				conceptList.add(b);
			}

			return conceptList;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getConceptRequireForSpecificOntology(String cid, String myid) {
		String b = "";
		try {

			String queryString = "PREFIX ns:<" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf:<"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "SELECT ?id " + "WHERE {"
					+ "?Individuals rdf:type ns:Ontology." + "?Individuals ns:ontologyId \"" + myid + "\"."
					+ "?Individuals ns:onthas ?y" + "." + "?y ns:id \"" + cid + "\"." + "?y ns:require ?x."
					+ "?x ns:id ?id." + "}";

			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("id");
				b = ind2.asLiteral().getString();
			}

			return b;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getConceptAggregationForSpecificOntology(String cid, String myid) {
		List<String> c = new ArrayList<>();
		try {
			String queryString = "PREFIX ns:<" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf:<"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "SELECT ?id " + "WHERE {"
					+ "?Individuals rdf:type ns:Ontology." + "?Individuals ns:ontologyId \"" + myid + "\"."
					+ "?Individuals ns:onthas ?y" + "." + "?y rdf:type ns:Concept." + "?y ns:id \"" + cid + "\"."
					+ "?y ns:part_of ?x." + "?x ns:id ?id." + "}";

			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("id");
				String b = ind2.asLiteral().getString();
				c.add(b);
			}
			return c;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getConceptFollowForSpecificOntology(String cid, String myid) {
		List<String> conceptList = new ArrayList<String>();
		try {
			String queryString = "PREFIX ns:<" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf:<"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "SELECT ?id " + "WHERE {"
					+ "?Individuals rdf:type ns:Ontology." + "?Individuals ns:ontologyId \"" + myid + "\"."
					+ "?Individuals ns:onthas ?y" + "." + "?y ns:id \"" + cid + "\"." + "?y ns:follows ?x."
					+ "?x ns:id ?id." + "}";

			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("id");
				String b = ind2.asLiteral().getString();

				conceptList.add(b);
			}

			return conceptList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getConceptBeforeForSpecificOntology(String cid, String myid) {
		List<String> conceptList = new ArrayList<String>();
		try {
			String queryString = "PREFIX ns:<" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf:<"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "SELECT ?id " + "WHERE {"
					+ "?Individuals rdf:type ns:Ontology." + "?Individuals ns:ontologyId \"" + myid + "\"."
					+ "?Individuals ns:onthas ?y" + "." + "?y ns:id \"" + cid + "\"." + "?y ns:before ?x."
					+ "?x ns:id ?id." + "}";

			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal ind2 = result.getLiteral("id");
				String b = ind2.asLiteral().getString();

				conceptList.add(b);
			}

			return conceptList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getConceptParentForSpecificOntology(String ontologyID, String myid) {
		String c = "";
		try {
			String queryString = "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
					+ "PREFIX ns:<http://www.semanticweb.org/hightech/ontologies/2019/8/untitled-ontology-4#>"
					+ "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>" + "SELECT ?myid " + "WHERE {"
					+ "?z ns:ontologyId \"" + ontologyID + "\"." + "?z ns:onthas ?Individuals."
					+ "?Individuals ns:id \"" + myid + "\"." + "?Individuals ns:parent ?x." + "?x ns:id ?myid." + "}";

			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);

			while (results.hasNext()) {
				QuerySolution result = (results.next());
				org.apache.jena.rdf.model.Literal var3 = result.getLiteral("myid");
				c = var3.asLiteral().getString();
			}

			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getAllRelations() {
		List<String> allRelations = new ArrayList<>();

		try {
			String queryString = "PREFIX ns: <" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf: <"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "PREFIX owl:<"
					+ OntologyManagerWithFusekiApplication.owl + ">" + "SELECT ?x " + "WHERE {"
					+ "?x a owl:ObjectProperty." + "}";
			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);
			while (results.hasNext()) {
				QuerySolution result = (results.next());
				RDFNode ind = result.getResource("x");
				String var1 = ind.asResource().getLocalName();

				allRelations.add(var1);
			}
			return allRelations;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Map<String , Object>> getConceptRelations(String cid) {
List<Map<String, Object>> l = new ArrayList<>();
		try {
			String queryString = "PREFIX ns: <" + OntologyManagerWithFusekiApplication.ns + ">" + "PREFIX rdf: <"
					+ OntologyManagerWithFusekiApplication.rdf + ">" + "PREFIX owl:<"
					+ OntologyManagerWithFusekiApplication.owl + ">" + "SELECT ?relation ?withcon " + "WHERE {"
					+ "?ind ?relation ?withcon." + "?ind ns:id \"" + cid + "\"."
					+ "FILTER (NOT EXISTS {SELECT ?relation ?withcon WHERE { ?ind ns:id ?withcon.}})"
					+ "FILTER (NOT EXISTS {SELECT ?relation ?withcon WHERE { ?ind ns:name ?withcon.}})"
					+ "FILTER (NOT EXISTS {SELECT ?relation ?withcon WHERE { ?ind ns:depth_level ?withcon.}})"
					+ "FILTER (NOT EXISTS {SELECT ?relation ?withcon WHERE { ?ind ns:description ?withcon.}})"
					+ "FILTER (NOT EXISTS {SELECT ?relation ?withcon WHERE { ?ind rdf:type ?withcon.}})"
					+ "FILTER (NOT EXISTS {SELECT ?relation ?withcon WHERE { ?ind ns:has_term ?withcon.}})" + "}";

			ResultSet results = OntologyManagerWithFusekiApplication
					.execSelectAndProcess(OntologyManagerWithFusekiApplication.serviceURI, queryString);
			while (results.hasNext()) {
				QuerySolution result = (results.next());
				RDFNode ind = result.getResource("relation");
				 RDFNode ind2 = result.getResource("withcon");

				String var1 = ind.asResource().getLocalName(); 
				 String var2 = ind2.asResource().getLocalName(); 
					Map<String , Object> jsParams = new HashMap<>();

					jsParams.put("relation", var1);
					jsParams.put("withconcept", var2);
					l.add(jsParams);
			}
			return l;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Concept insertConcept(String myname, String description, String depth, String domain,String termCode, String termDef,String rel, String consWith) throws IOException {
		
		Concept c = new Concept();
		
		  RDFConnection conn1 = RDFConnectionFactory.connect("http://localhost:3030/eduDataSet/") ;
		  Model model =  conn1.fetch();

		
		try {
			OntClass conceptClass = OntologyManagerWithFusekiApplication.model
					.getOntClass(OntologyManagerWithFusekiApplication.ns + "Concept");

			
			String conceptSub = myname.trim().substring(0, 2);
			String domainSub = domain.substring(0 , 2 );
			String conceptcostmizeCode = conceptSub + "-" + domainSub;
			
			Individual i = OntologyManagerWithFusekiApplication.model
					.createIndividual(OntologyManagerWithFusekiApplication.ns + conceptcostmizeCode, conceptClass);
			i.addRDFType(OWL2.NamedIndividual);
			
			DatatypeProperty pname = OntologyManagerWithFusekiApplication.model
					.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "name");
			i.addProperty(pname, model.createLiteral(myname));

			DatatypeProperty pcode = OntologyManagerWithFusekiApplication.model
					.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "id");
			i.addProperty(pcode,model.createLiteral(conceptcostmizeCode));

			DatatypeProperty pdescription = OntologyManagerWithFusekiApplication.model
					.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "description");
			i.addProperty(pdescription, model.createLiteral(description));

			DatatypeProperty pdepth = OntologyManagerWithFusekiApplication.model
					.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "depth_level");
			i.addProperty(pdepth, model.createLiteral(depth));

			DatatypeProperty pdomain = OntologyManagerWithFusekiApplication.model
					.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "domain");
			i.addProperty(pdomain,model.createLiteral(domain));

			OntClass terminologyClass = OntologyManagerWithFusekiApplication.model
					.getOntClass(OntologyManagerWithFusekiApplication.ns + "Terminology");
			String[] termCodeSplited = termCode.split("undefined");
			String[] termCodeSplited2 = termCodeSplited[0].split(",");

			String[] termDefSplited = termDef.split(",");
			for (int j = 0; j < termCodeSplited2.length; j++) {
				Individual i2 = OntologyManagerWithFusekiApplication.model.createIndividual(
						OntologyManagerWithFusekiApplication.ns + termCodeSplited2[j], terminologyClass);
				i2.addRDFType(OWL2.NamedIndividual);

				String termIdcustomized = termCode + conceptcostmizeCode;
				DatatypeProperty termId = OntologyManagerWithFusekiApplication.model
						.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "termId");
				i2.addProperty(termId, model.createLiteral(termIdcustomized));

				DatatypeProperty termName = OntologyManagerWithFusekiApplication.model
						.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "termName");
				i2.addProperty(termName, model.createLiteral(termCodeSplited2[j]));
				for (int k = 0; k < termDefSplited.length; k++) {
					DatatypeProperty termDef2 =OntologyManagerWithFusekiApplication.model
							.getDatatypeProperty(OntologyManagerWithFusekiApplication.ns + "termDef");
					i2.addProperty(termDef2,
							model.createLiteral(termDefSplited[k]));
				}
				ObjectProperty ohasTerm = OntologyManagerWithFusekiApplication.model
						.getObjectProperty(OntologyManagerWithFusekiApplication.ns + "has_term");
				ohasTerm.hasRDFType(OntologyManagerWithFusekiApplication.ns + i2);
				i.addProperty(ohasTerm, i2);
			}
			String[] relSplited = rel.split(",");
			String[] consWithSplited = consWith.split(",");
			for (int x = 1; x < relSplited.length; x++) {
				Individual i3 = OntologyManagerWithFusekiApplication.model
						.createIndividual(OntologyManagerWithFusekiApplication.ns + consWithSplited[x], conceptClass);
				i3.addRDFType(OWL2.NamedIndividual);
				ObjectProperty oRel = OntologyManagerWithFusekiApplication.model.getObjectProperty(OntologyManagerWithFusekiApplication.ns + relSplited[x]);
			oRel.hasRDFType(OntologyManagerWithFusekiApplication.ns + i3);
			i.addProperty(oRel , i3);
			oRel.hasRDFType(OntologyManagerWithFusekiApplication.ns+i3);
			i.addProperty(oRel , i3);
			
			OntologyManagerWithFusekiApplication.model.write(System.out , "TTL");
			OntologyManagerWithFusekiApplication.model.write(System.out, "N-Triples");
			OntologyManagerWithFusekiApplication.model.write(System.out, "TTL");
			
	
			}
			c.setId(conceptcostmizeCode);
			c.setName(myname);
			c.setDescription(description);
			c.setDepth(depth);
			Terminology t = new Terminology();
			t.setTerminologyId(termCode);
			t.setTerminologyName(termCode);
			t.setTerminologyDefinition(termDef);
			
			List<Terminology> tList = new ArrayList<>();
			tList.add(t);
			c.setTerminologies(tList);
			conn1.put( model);
			return c;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
