package com.example.demo.concept;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.terminology.Terminology;

import springfox.documentation.annotations.ApiIgnore;


@RestController
public class ConceptController {
	ConceptService cs = new ConceptService(); 
	

	@RequestMapping(value = "/api/concepts", method = RequestMethod.GET)
	public List<Map<String , Object>> getAllConcepts(@RequestParam(value = "ontologyId", required = false) String ontologyId) {
		List<Map<String , Object>> conceptList = new ArrayList<>();
		 if (ontologyId != null) {
			conceptList = cs.getConceptsByOntologyId(ontologyId);
		}else {
			conceptList = cs.getAllConcepts();
		}		
		return conceptList; 
	}

	@RequestMapping(value = "/api/concepts/{conceptId}/{ontologyId}" , method = RequestMethod.GET)
	public Concept getConceptByIdInSpecificOntology(@PathVariable("conceptId")String cid ,@PathVariable(value = "ontologyId") String myid) {
		Concept c = new Concept();
		c = cs.getConceptByIdInSpecificOntology(cid , myid);
		return c ; 
	}
	
	@ApiIgnore
	@RequestMapping(value = "/api/concepts/{conceptId}/{ontologyId}/has_term" , method = RequestMethod.GET)
	public List<Terminology> getConceptHasTermInSpecificOntology(@PathVariable("conceptId")String cid ,@PathVariable(value = "ontologyId") String myid) {
		List<Terminology> terminologyList = new ArrayList<Terminology>();
		terminologyList =cs.getConceptHasTermInSpecificOntology(cid , myid);
		return terminologyList;
	}
	
	@ApiIgnore
	@RequestMapping(value = "/api/concepts/{conceptId}/{ontologyId}/is_a" , method = RequestMethod.GET)
	public List<Concept> getConceptIsAForSpecificOntology(@PathVariable("conceptId")String cid ,@PathVariable(value = "ontologyId") String myid) {
  		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList = cs.getConceptIsAForSpecificOntology(cid , myid);	
		return conceptList ; 
	}
	
	@ApiIgnore
	@RequestMapping(value = "/api/concepts/{conceptId}/{ontologyId}/pre_requisite" , method = RequestMethod.GET)
	public List<Concept> getConceptPrerequisiteForSpecificOntology(@PathVariable("conceptId")String cid ,@PathVariable(value = "ontologyId") String myid) {
		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList = cs.getConceptPrerequisiteForSpecificOntology(cid , myid);
		
		return conceptList;
	}
	
	@ApiIgnore
	@RequestMapping(value = "/api/concepts/{conceptId}/{ontologyId}/require" , method = RequestMethod.GET)
	public Concept getConceptRequireForSpecificOntology(@PathVariable("conceptId")String cid ,@PathVariable(value = "ontologyId") String myid) {
		Concept c = new Concept();
		c = cs.getConceptRequireForSpecificOntology(cid, myid);
		return c;
	}
	
	@ApiIgnore
	@RequestMapping(value = "/api/concepts/{conceptId}/{ontologyId}/part_of" , method = RequestMethod.GET)
	public List<Concept> getConceptAggregationForSpecificOntology(@PathVariable("conceptId")String cid ,@PathVariable(value = "ontologyId") String myid) {
  		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList  = cs.getConceptAggregationForSpecificOntology(cid, myid);
		return conceptList;
	}
	
	@ApiIgnore
  	@RequestMapping(value = "/api/concepts/{conceptId}/{ontologyId}/follows" , method = RequestMethod.GET)
	public List<Concept> getConceptFollowForSpecificOntology(@PathVariable("conceptId")String cid ,@PathVariable(value = "ontologyId") String myid) {
  		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList = cs.getConceptFollowForSpecificOntology(cid, myid);
		return conceptList;
  	}
	@ApiIgnore
	@RequestMapping(value = "/api/concepts/{conceptId}/{ontologyId}/before" , method = RequestMethod.GET)
	public List<Concept> getConceptBeforeForSpecificOntology(@PathVariable("conceptId")String cid ,@PathVariable(value = "ontologyId") String myid) {
  		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList = cs.getConceptBeforeForSpecificOntology(cid, myid);
		return conceptList;
  	}	
	@ApiIgnore
	@RequestMapping(value = "/api/concepts/{id}/{ontologyID}/parent", method = RequestMethod.GET)
	public List<Concept> getConceptParentForSpecificOntology(@PathVariable("ontologyID")String ontologyID,@PathVariable(value = "id") String myid) {
		List<Concept> conceptList = new ArrayList<Concept>();
		conceptList = cs.getConceptParentForSpecificOntology(ontologyID , myid);
		return conceptList;
	}
	
	@ApiIgnore
	@RequestMapping(value = "/api/concepts/{id}/{ontologyID}/parent/{depthLevel}", method = RequestMethod.GET)
	public List<Concept> getConceptDepthLevelForSpecificOntology(@PathVariable("ontologyID")String ontologyID,  @PathVariable(value = "id") String myid, @PathVariable (value = "depthLevel") String mydepth) {
			List<Concept> conceptList = new ArrayList<Concept>();
			conceptList = cs.getConceptDepthLevelForSpecificOntology(ontologyID , myid , mydepth);
			return conceptList; 
	}
	
	@ApiIgnore
	@RequestMapping(value = "/api/relations" , method = RequestMethod.GET)
	public List<String> getAllRelations() {
		List<String> allRelations = new ArrayList<>();
		allRelations = cs.getAllRelations();
	   return allRelations;
	}
	
	@ApiIgnore
	@RequestMapping(value = "/api/{id}/relations" , method = RequestMethod.GET)
	public List<Map<String , Object>> getConceptRelations(@PathVariable("id") String cid) {
		List<Map<String , Object>> conRelations = new ArrayList<>();
		conRelations = cs.getConceptRelations(cid);
	return conRelations;
	}
	
	
	@ApiIgnore
	@RequestMapping(method = RequestMethod.POST , value = "/api/concepts/insert/{cname}/{cdescription}/{cdepth}/{cdomain}/{term}/{termDef}/{rel}/{consWith}")
	public Concept insertConcept(  @PathVariable("cname")String myname , @PathVariable("cdescription")String description  , @PathVariable("cdepth")String depth ,  @PathVariable("cdomain")String domain , @PathVariable("term") String termCode ,  @PathVariable("termDef") String termDef ,  @PathVariable("rel")String rel, @PathVariable("consWith")String consWith   ) throws IOException{
		Concept c = new Concept() ; 
		c = cs.insertConcept( myname , description , depth , domain , termCode , termDef , rel  , consWith);
		return c;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
