package com.example.demo.concept;

import java.util.List;

import com.example.demo.terminology.Terminology;

public class Concept {
	private String id;
	private String name;
	private String description;
	private String depth;
	private List<Terminology> terminologies;
	private List<Concept> childrens;
	private List<Concept> is_a;

	public List<Concept> getIs_a() {
		return is_a;
	}

	public void setIs_a(List<Concept> is_a) {
		this.is_a = is_a;
	}

	public List<Concept> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Concept> childrens) {
		this.childrens = childrens;
	}

	public String getId() {
		return id;
	}

	public List<Terminology> getTerminologies() {
		return terminologies;
	}

	public void setTerminologies(List<Terminology> terminologies) {
		this.terminologies = terminologies;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public Concept(String id, String name, String description, String depth, List<Terminology> terminologies,
			List<Concept> childrens, List<Concept> is_a) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.depth = depth;
		this.terminologies = terminologies;
		this.childrens = childrens;
		this.is_a = is_a;
	}

	@Override
	public String toString() {
		return "Concept [id=" + id + ", name=" + name + ", description=" + description + ", depth=" + depth
				+ ", terminologies=" + terminologies + ", childrens=" + childrens + ", is_a=" + is_a + "]";
	}

	public Concept() {
	}

}
