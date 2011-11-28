package com.pinkdroid.model;

public class SensisAddress {
	private String state;
	private String postcode;
	private String latitude;
	private String longitude;
	private String suburb;
	private String addressLine;
	private String geoCodeGranularity;

	public String getAddressLine() {
		return addressLine;
	}

	public String getGeoCodeGranularity() {
		return geoCodeGranularity;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getState() {
		return state;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public void setGeoCodeGranularity(String geoCodeGranularity) {
		this.geoCodeGranularity = geoCodeGranularity;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
}
