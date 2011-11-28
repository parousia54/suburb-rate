package com.pinkdroid.model;

import java.util.LinkedList;

public class QueryResponse {
	private int count;
	private int totalResults;
	private int totalPages;
	private LinkedList<SensisResult> results;

	public int getCount() {
		return count;
	}

	public LinkedList<SensisResult> getResults() {
		return results;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setResults(LinkedList<SensisResult> results) {
		this.results = results;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

}
