package com.example.demo.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DomainService {
	DomainRepository dr = new DomainRepository(); 
	
	public List<Domain> getAllDomains() {
		List<Domain> domainlist= new ArrayList<Domain>();
		domainlist = dr.getAllDomains();
		return  domainlist ;  
	}
	
	public Domain getDominaById(String myid) {
		Domain d = new Domain();
		d = dr.getDomainById(myid);
		return d;
	}
	
	public Domain insertDomain(String myname) throws IOException {
		Domain d = new Domain();
		d = dr.insertDomain(myname);
		return d;
	}
	
	
	
	
	
}
