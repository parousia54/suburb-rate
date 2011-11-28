package com.pinkdroid.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.database.Cursor;

import com.pinkdroid.R;
import com.pinkdroid.io.ExternalFileReader;
import com.pinkdroid.model.Criterion;
import com.pinkdroid.model.Suburb;
import com.pinkdroid.networking.Communicator;
import com.pinkdroid.persistent.DatabaseAccessHelper;
import com.pinkdroid.persistent.DatabaseManager;
import com.pinkdroid.util.FileReadingUtil;
import com.pinkdroid.view.ScreenUpdater;

public class Controller extends Application {
	private static Controller instance;

	public static Controller getInstance() {
		if (instance == null)
			instance = new Controller();
		return instance;
	}

	private Collection<Criterion> criteria;
	private DatabaseManager dbManager;
	private Communicator communicator;
	private ApplicationState applicationState;
	private double score;

	Map<Criterion, Double> ratings;

	public void chooseCriterion(final ScreenUpdater updater, Criterion criterion) {
		communicator.getBusinessesForCriterion(updater, criterion);
	}

	public double computeRankingCriteria() {
		score = 0.0;
		for (Criterion crit : this.criteria) {
			if (ratings.containsKey(crit))
				score += (1000 * crit.getParameter() * (ratings.get(crit)))
						/ crit.getTotalResults();
		}
		return score;
	}

	public boolean doDatabaseStuff() {

		Cursor cursor = DatabaseAccessHelper.getMatchingSuburbSearch(dbManager,
				"3000");
		if (cursor == null || cursor.getCount() <= 0) {
			DatabaseAccessHelper.cleanDatabase(dbManager);
			FileReadingUtil.insertSuburbsToDb(this, "vicdump.txt", dbManager);
		}
		return true;

	}

	public void generateMap() {
	}

	public ApplicationState getApplicationState() {
		return applicationState;
	}

	public Communicator getCommunicator() {
		return communicator;
	}

	public Collection<Criterion> getCriteria() {
		return criteria;
	}

	public void getCriterionValue(ScreenUpdater updater, Criterion criterion) {
		communicator.getSuburbCount(updater, criterion);
	}

	public DatabaseManager getDbManager() {
		return dbManager;
	}

	public Map<Criterion, Double> getRatings() {
		return ratings;
	}

	private void initiate() {

		try {
			ratings = new HashMap<Criterion, Double>();
			ExternalFileReader reader = new ExternalFileReader(this);
			reader.setInputStream(getResources()
					.openRawResource(R.raw.criteria));
			criteria = reader.getCriteria();
			System.out.println("Criteria size " + criteria.size());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		setDbManager(new DatabaseManager(this));
		setCommunicator(new Communicator());
		setApplicationState(new ApplicationState());
		initiate();

	}

	public void setApplicationState(ApplicationState applicationState) {
		this.applicationState = applicationState;
	}

	public void setCommunicator(Communicator communicator) {
		this.communicator = communicator;
	}

	public void setCriteria(Collection<Criterion> criteria) {
		this.criteria = criteria;
	}

	public void setCurrentSuburb(Suburb suburb) {
		getApplicationState().setCurrentSuburb(suburb);
		// communicator.getSuburbCount();
	}

	public void setDbManager(DatabaseManager dbManager) {
		this.dbManager = dbManager;
	}

	public void setRatings(Map<Criterion, Double> ratings) {
		this.ratings = ratings;
	}

}
