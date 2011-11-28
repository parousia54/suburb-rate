package com.pinkdroid.model;

public class SensisCategory {
	private String name;
	private String id;
	private boolean sensitive;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isSensitive() {
		return sensitive;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSensitive(boolean sensitive) {
		this.sensitive = sensitive;
	}

}
