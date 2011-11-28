package com.pinkdroid.model;

import java.util.LinkedList;

public class SensisResult {
	private String name;
	private String id;
	private LinkedList<SensisContact> primaryContacts;
	private String reportingId;
	private boolean geoCoded;
	private boolean addressSuppressed;
	private String listingType;
	private boolean hasExposureProducts;
	private SensisAddress primaryAddress;
	private LinkedList<SensisImage> imageGallery;

	public String getId() {
		return id;
	}

	public LinkedList<SensisImage> getImageGallery() {
		return imageGallery;
	}

	public String getListingType() {
		return listingType;
	}

	public String getName() {
		return name;
	}

	public SensisAddress getPrimaryAddress() {
		return primaryAddress;
	}

	public LinkedList<SensisContact> getPrimaryContacts() {
		return primaryContacts;
	}

	public String getReportingId() {
		return reportingId;
	}

	public boolean isAddressSuppressed() {
		return addressSuppressed;
	}

	public boolean isGeoCoded() {
		return geoCoded;
	}

	public boolean isHasExposureProducts() {
		return hasExposureProducts;
	}

	public void setAddressSuppressed(boolean addressSuppressed) {
		this.addressSuppressed = addressSuppressed;
	}

	public void setGeoCoded(boolean geoCoded) {
		this.geoCoded = geoCoded;
	}

	public void setHasExposureProducts(boolean hasExposureProducts) {
		this.hasExposureProducts = hasExposureProducts;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImageGallery(LinkedList<SensisImage> imageGallery) {
		this.imageGallery = imageGallery;
	}

	public void setListingType(String listingType) {
		this.listingType = listingType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrimaryAddress(SensisAddress primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	public void setPrimaryContacts(LinkedList<SensisContact> primaryContacts) {
		this.primaryContacts = primaryContacts;
	}

	public void setReportingId(String reportingId) {
		this.reportingId = reportingId;
	}

}
