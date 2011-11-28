package com.pinkdroid.model;

import java.util.LinkedList;

public class Criterion {
	private String name;
	private LinkedList<SensisCategory> categories;
	private float parameter;
	private int totalResults;

	public LinkedList<SensisCategory> getCategories() {
		return categories;
	}

	public String getName() {
		return name;
	}

	public float getParameter() {
		return parameter;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setCategories(LinkedList<SensisCategory> categories) {
		this.categories = categories;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParameter(float parameter) {
		this.parameter = parameter;
	}

	public void setTotalResults(int totalResults) {
		System.out.println("set total results " + totalResults);
		this.totalResults = totalResults;
	}

}
