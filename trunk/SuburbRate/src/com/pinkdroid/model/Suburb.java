package com.pinkdroid.model;

public class Suburb {
	public final static String ACT = "ACT";
	public final static String VIC = "VIC";
	public final static String NSW = "NSW";
	public final static String TAS = "TAS";
	public final static String WA = "WA";
	public final static String NT = "NT";
	public final static String QLD = "QLD";

	private int postcode;
	private String name;
	private String state;

	private SuburbStats statistics;

	public String getName() {
		return name;
	}

	public int getPostcode() {
		return postcode;
	}

	public String getState() {
		return state;
	}

	public SuburbStats getStatistics() {
		return statistics;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setStatistics(SuburbStats statistics) {
		this.statistics = statistics;
	}

}
