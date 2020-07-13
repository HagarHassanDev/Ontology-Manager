package com.example.demo.concept;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.terminology.Terminology;
import com.example.demo.terminology.TerminologyService;

@Service
public class ConceptService {
	ConceptRepository cr = new ConceptRepository();
	TerminologyService ts = new TerminologyService();

	public List<Map<String, Object>> getAllConcepts() {
		List<Map<String, Object>> conceptList = new ArrayList<>();
		conceptList = cr.getAllConcepts();
		return conceptList;
	}

	public List<Map<String, Object>> getConceptsByOntologyId(String myid) {
		List<Map<String, Object>> conceptList = new ArrayList<>();
		conceptList = cr.getConceptsByOntologyId(myid);
		return conceptList;
	}

	public List<String> getConceptByDomainId(String myid) {
		List<String> conceptList = new ArrayList<String>();
		conceptList = cr.getConceptByDomainId(myid);

		return conceptList;
	}

	public Concept getConceptById(String myid) {
		Concept c = new Concept();
		c = cr.getConceptById(myid);
		List<Terminology> terms = ts.getTerminologyForSpecificConcept(myid);
		c.setTerminologies(terms);
		return c;
	}

	public Concept getConceptByIdInSpecificOntology(String cid, String myid) {
		Concept c = new Concept();
		String conceptId = cr.getConceptByIdInSpecificOntology(cid, myid);
		c = getConceptById(conceptId);
		return c;
	}

	public List<Terminology> getConceptHasTermInSpecificOntology(String cid, String myid) {
		List<Terminology> terminologyList = new ArrayList<Terminology>();
		List<String> termID = cr.getConceptHasTermInSpecificOntology(cid, myid);
		for (int i = 0; i < termID.size(); i++) {
			Terminology ter = ts.getTerminologyDetails(termID.get(i));
			terminologyList.add(ter);
		}
		return terminologyList;
	}

	// This is the is_a logic implementation
	public List<Concept> getConceptIsAForSpecificOntology(String cid, String myid) {
		List<Concept> conceptList = new ArrayList<Concept>();

		List<String> conceptIDs = cr.getConceptIsAForSpecificOntology(cid, myid);

		for (int i = 0; i < conceptIDs.size(); i++) {
			Concept conceptObject = cr.getConceptById(conceptIDs.get(i));
			List<Concept> l = getConceptIsAForSpecificOntology(conceptIDs.get(i), myid);
			for (int j = 0; j < l.size(); j++) {
				if (l.get(j).getId() != null) {
					conceptObject.setIs_a(l);

				}
			}

			conceptList.add(conceptObject);

		}

		return conceptList;
	}

	@RequestMapping(value = "/api/concepts/{conceptId}/{ontologyId}/pre_requisite", method = RequestMethod.GET)
	public List<Concept> getConceptPrerequisiteForSpecificOntology(@PathVariable("conceptId") String cid,
			@PathVariable(value = "ontologyId") String myid) {
		List<Concept> conceptList = new ArrayList<Concept>();
		List<String> conceptIDs = cr.getConceptPrerequisiteForSpecificOntology(cid, myid);
		for (int i = 0; i < conceptIDs.size(); i++) {
			conceptList.add(cr.getConceptById(conceptIDs.get(i)));
		}
		return conceptList;
	}

	public Concept getConceptRequireForSpecificOntology(String cid, String myid) {
		Concept c = new Concept();
		String id = cr.getConceptRequireForSpecificOntology(cid, myid);
		c = cr.getConceptById(id);
		return c;
	}

	public List<Concept> getConceptAggregationForSpecificOntology(String cid, String myid) {
		List<Concept> conceptList = new ArrayList<>();
		List<String> conceptIDs = cr.getConceptAggregationForSpecificOntology(cid, myid);
		for (int i = 0; i < conceptIDs.size(); i++) {
			conceptList.add(cr.getConceptById(conceptIDs.get(i)));
		}
		return conceptList;
	}

	public List<Concept> getConceptFollowForSpecificOntology(String cid, String myid) {
		List<Concept> conceptList = new ArrayList<Concept>();
		List<String> conceptIDs = cr.getConceptFollowForSpecificOntology(cid, myid);
		for (int i = 0; i < conceptIDs.size(); i++) {
			conceptList.add(cr.getConceptById(conceptIDs.get(i)));
		}
		return conceptList;
	}

	public List<Concept> getConceptBeforeForSpecificOntology(String cid, String myid) {
		List<Concept> conceptList = new ArrayList<Concept>();
		List<String> conceptIDs = cr.getConceptBeforeForSpecificOntology(cid, myid);
		for (int i = 0; i < conceptIDs.size(); i++) {
			conceptList.add(cr.getConceptById(conceptIDs.get(i)));
		}
		return conceptList;
	}

	public List<Concept> getConceptParentForSpecificOntology(String ontologyID, String myid) {
		List<Concept> conceptList = new ArrayList<Concept>();
		String id = cr.getConceptParentForSpecificOntology(ontologyID, myid);
		Concept conceptObjectp = cr.getConceptById(id);

		if (conceptObjectp.getId() != null) {
			conceptList.add(conceptObjectp);

			List<String> ids = cr.getConceptBeforeForSpecificOntology(id, ontologyID);
			for (int i = 0; i < ids.size(); i++) {
				Concept conceptObject = cr.getConceptById(ids.get(i));
				List<Concept> l = getConceptParentForSpecificOntology(ontologyID, ids.get(i));
				for (int j = 0; j < l.size(); j++) {
					if (l.get(j).getId() != null)
						conceptObject.setChildrens(l);
				}
				conceptList.add(conceptObject);
			}
		}
		return conceptList;
	}

	public List<Concept> getConceptDepthLevelForSpecificOntology(String ontologyID, String myid, String mydepth) {
		List<Concept> listDepths = new ArrayList<>();

		List<Concept> parentlist = new ArrayList<>();
		parentlist = getConceptParentForSpecificOntology(ontologyID, myid);

		List<Concept> totallist = new ArrayList<>();
		for (int i = 0; i < parentlist.size(); i++) {
			if (parentlist.get(i).getChildrens() != null) {
				for (int j = 0; j < parentlist.get(i).getChildrens().size(); j++) {
					totallist.add(parentlist.get(i).getChildrens().get(j));
				}

				parentlist.get(i).setChildrens(null);
				totallist.add(parentlist.get(i));
			} else {
				totallist.add(parentlist.get(i));

			}
		}

		for (int i = 0; i < totallist.size(); i++) {
			if (mydepth.toString().equals(totallist.get(i).getDepth())) {

				listDepths.add(totallist.get(i));
			}

		}
		int flag = Integer.parseInt(mydepth);
		if (flag - 1 != 0) {
			flag--;
			List<Concept> lc = getConceptDepthLevelForSpecificOntology(ontologyID, myid, Integer.toString(flag));
			for (int j = 0; j < lc.size(); j++) {
				listDepths.add(lc.get(j));
			}
		}
		return listDepths;
	}

	public List<String> getAllRelations() {
		List<String> allRelations = new ArrayList<>();
		allRelations = cr.getAllRelations();
		return allRelations;
	}

	public List<Map<String, Object>> getConceptRelations(String cid) {
		List<Map<String, Object>> conRelations = new ArrayList<>();
		conRelations = cr.getConceptRelations(cid);
		return conRelations;
	}

	public Concept insertConcept(String myname, String description, String depth, String domain, String termCode,
			String termDef, String rel, String consWith) throws IOException {
		Concept c = new Concept();
		c = cr.insertConcept(myname, description, depth, domain, termCode, termDef, rel, consWith);
		return c;
	}

}
