package com.pinkdroid.model;

public class SensisAddress {
	private String state;
	private String postcode;
	private String latitude;
	private String longitude;
	private String suburb;
	private String addressLine;
	private String geoCodeGranularity;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	public String getAddressLine() {
		return addressLine;
	}
	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}
	public String getGeoCodeGranularity() {
		return geoCodeGranularity;
	}
	public void setGeoCodeGranularity(String geoCodeGranularity) {
		this.geoCodeGranularity = geoCodeGranularity;
	}
}
