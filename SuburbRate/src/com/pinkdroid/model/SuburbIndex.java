package com.pinkdroid.model;

public class SuburbIndex {
	
	// ================= SOCIO-ECO attributes ======
	
	private double score;
	
	private int aus_rank; 		// National ranking
	private int state_rank;		// State ranking
	
	private double aus_pct; 		// National percentile
	private double state_pct;	// State percentile
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getAus_rank() {
		return aus_rank;
	}
	public void setAus_rank(int aus_rank) {
		this.aus_rank = aus_rank;
	}
	public int getState_rank() {
		return state_rank;
	}
	public void setState_rank(int state_rank) {
		this.state_rank = state_rank;
	}
	public double getAus_pct() {
		return aus_pct;
	}
	public void setAus_pct(double aus_pct) {
		this.aus_pct = aus_pct;
	}
	public double getState_pct() {
		return state_pct;
	}
	public void setState_pct(double state_pct) {
		this.state_pct = state_pct;
	}
	
}
