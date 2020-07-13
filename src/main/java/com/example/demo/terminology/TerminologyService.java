package com.example.demo.terminology;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TerminologyService {
	TerminologyRepesitory tr = new TerminologyRepesitory();

	public List<Terminology> getTerminologyForSpecificConcept( String cid) {
		List<Terminology> terminologylist = new ArrayList<Terminology>();
		terminologylist = tr.getTerminologyForSpecificConcept(cid);
		return terminologylist;
	}
	
	
	public Terminology getTerminologyDetails(String t ) {
		Terminology terminologylist = new Terminology();
		terminologylist = tr.getTerminologyDetails(t);
		return terminologylist;
	}
}
