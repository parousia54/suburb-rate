package com.pinkdroid.logic;

import java.util.ArrayList;

import com.pinkdroid.model.Criterion;
import com.pinkdroid.model.QueryResponse;
import com.pinkdroid.model.Suburb;

public class ApplicationState {
	
	private QueryResponse businessesForSelectedCriterion;
	
	private Criterion selectedCriterion;
	private Suburb currentSuburb;


	public void setCurrentSuburb(Suburb currentSuburb) {
		this.currentSuburb = currentSuburb;
	}

	public Suburb getCurrentSuburb() {
		return currentSuburb;
	}

	public QueryResponse getBusinessesForSelectedCriterion() {
		return businessesForSelectedCriterion;
	}

	public void setBusinessesForSelectedCriterion(
			QueryResponse businessesForSelectedCriterion) {
		this.businessesForSelectedCriterion = businessesForSelectedCriterion;
	}

	public Criterion getSelectedCriterion() {
		return selectedCriterion;
	}

	public void setSelectedCriterion(Criterion selectedCriterion) {
		this.selectedCriterion = selectedCriterion;
	}
	
	public void setSensisQueryResponse(QueryResponse sensisQueryResponse) {
		this.businessesForSelectedCriterion = sensisQueryResponse;
	}

	public QueryResponse getSensisQueryResponse() {
		return businessesForSelectedCriterion;
	}


}
