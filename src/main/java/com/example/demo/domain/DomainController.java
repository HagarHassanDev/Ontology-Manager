package com.example.demo.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
public class DomainController {
			
	DomainService ds = new DomainService();
	
	@RequestMapping(value = "/api/domains" , method= RequestMethod.GET )
	public List<Domain> getAllDomains() throws IOException {	
		List<Domain> domainlist= new ArrayList<Domain>();
		domainlist = ds.getAllDomains();
		return domainlist; 
	}
	
	@RequestMapping(value = "/api/domains/{id}", method = RequestMethod.GET)
	public Domain getDomainById(@PathVariable("id") String myid) {
		Domain d = new Domain();
		d = ds.getDominaById(myid);
		return d ; 
	}
	
	@ApiIgnore
	@RequestMapping(value = "/api/domains/insert/{domainName}", method = RequestMethod.POST)
	public Domain insertDomain(@PathVariable("domainName") String myname) throws IOException {
		Domain d = new Domain();
		d = ds.insertDomain(myname);
		return d;
	}
	
	
	
	
	
	
	
	
	
	
}
