package com.pinkdroid.model;

public class SensisContact {
	String value;
	String type;
	String description;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {	
		return description== null?"":description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
