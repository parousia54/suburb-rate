package com.pinkdroid.model;

import java.util.Collection;
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
	private LinkedList<SensisImage>  imageGallery;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LinkedList<SensisContact> getPrimaryContacts() {
		return primaryContacts;
	}

	public void setPrimaryContacts(LinkedList<SensisContact> primaryContacts) {
		this.primaryContacts = primaryContacts;
	}

	public String getReportingId() {
		return reportingId;
	}

	public void setReportingId(String reportingId) {
		this.reportingId = reportingId;
	}

	public boolean isGeoCoded() {
		return geoCoded;
	}

	public void setGeoCoded(boolean geoCoded) {
		this.geoCoded = geoCoded;
	}

	public boolean isAddressSuppressed() {
		return addressSuppressed;
	}

	public void setAddressSuppressed(boolean addressSuppressed) {
		this.addressSuppressed = addressSuppressed;
	}

	public String getListingType() {
		return listingType;
	}

	public void setListingType(String listingType) {
		this.listingType = listingType;
	}

	public boolean isHasExposureProducts() {
		return hasExposureProducts;
	}

	public void setHasExposureProducts(boolean hasExposureProducts) {
		this.hasExposureProducts = hasExposureProducts;
	}

	public SensisAddress getPrimaryAddress() {
		return primaryAddress;
	}

	public void setPrimaryAddress(SensisAddress primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	public LinkedList<SensisImage> getImageGallery() {
		return imageGallery;
	}

	public void setImageGallery(LinkedList<SensisImage> imageGallery) {
		this.imageGallery = imageGallery;
	}

	
	
}
