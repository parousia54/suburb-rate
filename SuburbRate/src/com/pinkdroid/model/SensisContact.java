package com.pinkdroid.model;

public class SensisContact {
	String value;
	String type;
	String description;

	public String getDescription() {
		return description == null ? "" : description;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
