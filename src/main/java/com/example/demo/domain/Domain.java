package com.example.demo.domain;

public class Domain {
	private String domainId ; 
	private String domainName ;
	public String getDomainId() {
		return domainId;
	}
	public Domain() {
	}
	
	public Domain(String domainId, String domainName) {
		super();
		this.domainId = domainId;
		this.domainName = domainName;
	}
	@Override
	public String toString() {
		return "Domain [domainId=" + domainId + ", domainName=" + domainName + "]";
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	} 
}
