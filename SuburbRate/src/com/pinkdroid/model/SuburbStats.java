package com.pinkdroid.model;

public class SuburbStats {
	

	private String ssc;	
	
	// Socio-Economic rating
	private int dataYear;
	private SuburbIndex adv_disAdv;
	private SuburbIndex economic;
	private SuburbIndex education;
	private int population;
	
	// Safety rating
	private int safetyYear;
	private double safetyWalking; //The suburb's perceived safety rating for walking alone at night in the area (or -1 if there is no data available for the suburb)
	private double safetyTransport; //The suburb's perceived safety rating for waiting for or using public transport alone at night in the area (or -1 if there is no data available for the suburb)
	
	
	
	
	public String getSsc() {
		return ssc;
	}
	public void setSsc(String ssc) {
		this.ssc = ssc;
	}
	
	public int getDataYear() {
		return dataYear;
	}
	public void setDataYear(int dataYear) {
		this.dataYear = dataYear;
	}
	
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	
	
	public int getSafetyYear() {
		return safetyYear;
	}
	public void setSafetyYear(int safetyYear) {
		this.safetyYear = safetyYear;
	}
	public double getSafetyWalking() {
		return safetyWalking;
	}
	public void setSafetyWalking(double safetyWalking) {
		this.safetyWalking = safetyWalking;
	}
	public double getSafetyTransport() {
		return safetyTransport;
	}
	public void setSafetyTransport(double safetyTransport) {
		this.safetyTransport = safetyTransport;
	}
	public SuburbIndex getAdv_disAdv() {
		return adv_disAdv;
	}
	public void setAdv_disAdv(SuburbIndex adv_disAdv) {
		this.adv_disAdv = adv_disAdv;
	}
	public SuburbIndex getEconomic() {
		return economic;
	}
	public void setEconomic(SuburbIndex economic) {
		this.economic = economic;
	}
	public SuburbIndex getEducation() {
		return education;
	}
	public void setEducation(SuburbIndex education) {
		this.education = education;
	}
	

}
