package com.pinkdroid.model;

import java.util.LinkedList;

public class Criterion {
	private String name;
	private LinkedList<SensisCategory> categories;
	private float parameter;
	private int totalResults;

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		System.out.println("set total results "+ totalResults);
		this.totalResults = totalResults;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<SensisCategory> getCategories() {
		return categories;
	}

	public void setCategories(LinkedList<SensisCategory> categories) {
		this.categories = categories;
	}

	public float getParameter() {
		return parameter;
	}

	public void setParameter(float parameter) {
		this.parameter = parameter;
	}
	
	
}
