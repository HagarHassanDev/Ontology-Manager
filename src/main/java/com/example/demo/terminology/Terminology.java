package com.example.demo.terminology;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Terminology {
	@JsonIgnore
	private String terminologyId ;
	private String terminologyName;
	private String terminologyDefinition ;
	public String getTerminologyName() {
		return terminologyName;
	}
	public void setTerminologyName(String terminologyName) {
		this.terminologyName = terminologyName;
	}
	public String getTerminologyDefinition() {
		return terminologyDefinition;
	}
	public void setTerminologyDefinition(String terminologyDefinition) {
		this.terminologyDefinition = terminologyDefinition;
	}
	
	public Terminology(String terminologyId, String terminologyName, String terminologyDefinition) {
		super();
		this.terminologyId = terminologyId;
		this.terminologyName = terminologyName;
		this.terminologyDefinition = terminologyDefinition;
	}
	@Override
	public String toString() {
		return "Terminology [terminologyId=" + terminologyId + ", terminologyName=" + terminologyName
				+ ", terminologyDefinition=" + terminologyDefinition + "]";
	}
	public String getTerminologyId() {
		return terminologyId;
	}
	public void setTerminologyId(String terminologyId) {
		this.terminologyId = terminologyId;
	}
	public Terminology() {}
	
	
}
