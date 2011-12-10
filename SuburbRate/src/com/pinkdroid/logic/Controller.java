package com.pinkdroid.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.database.Cursor;
import android.view.View;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.pinkdroid.R;
import com.pinkdroid.io.ExternalFileReader;
import com.pinkdroid.model.Criterion;
import com.pinkdroid.model.Suburb;
import com.pinkdroid.networking.Communicator;
import com.pinkdroid.persistent.DatabaseAccessHelper;
import com.pinkdroid.persistent.DatabaseManager;
import com.pinkdroid.util.FileReadingUtil;
import com.pinkdroid.view.ScreenUpdater;
import com.pinkdroid.ws.SensisAPICaller;

public class Controller extends Application {

	private static final String UA_ID = "UA-27628323-1";

	private static Controller instance;
	private GoogleAnalyticsTracker tracker;
	private Collection<Criterion> criteria;
	private DatabaseManager dbManager;
	private Communicator communicator;
	private ApplicationState applicationState;
	private double score;
	Map<Criterion, Double> ratings;

	public static Controller getInstance() {
		if (instance == null)
			instance = new Controller();
		return instance;
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

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		release();
	}

	private void release() {
		// TODO Auto-generated method stub
		tracker.stopSession();
	}

	public void setDbManager(DatabaseManager dbManager) {
		this.dbManager = dbManager;
	}

	public DatabaseManager getDbManager() {
		return dbManager;
	}

	private void initiate() {

		try {

			tracker = GoogleAnalyticsTracker.getInstance();

			// Start the tracker in manual dispatch mode...
			tracker.startNewSession(UA_ID, 10, this);

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

	public void setCommunicator(Communicator communicator) {
		this.communicator = communicator;
	}

	public Communicator getCommunicator() {
		return communicator;
	}

	public void setApplicationState(ApplicationState applicationState) {
		this.applicationState = applicationState;
	}

	public ApplicationState getApplicationState() {
		return applicationState;
	}

	public Collection<Criterion> getCriteria() {
		return criteria;
	}

	public void setCriteria(Collection<Criterion> criteria) {
		this.criteria = criteria;
	}

	public void getCriterionValue(ScreenUpdater updater, Criterion criterion) {
		communicator.getSuburbCount(updater, criterion);
	}

	public double computeRankingCriteria() {
		score = 0.0;
		for (Criterion crit : this.criteria) {
			if (ratings.containsKey(crit))
				score += (1000 * crit.getParameter() * ((double) ratings
						.get(crit))) / crit.getTotalResults();
		}
		return score;
	}

	public Map<Criterion, Double> getRatings() {
		return ratings;
	}

	public void setRatings(Map<Criterion, Double> ratings) {
		this.ratings = ratings;
	}

	public void generateMap() {
	}

	public void setCurrentSuburb(Suburb suburb) {
		getApplicationState().setCurrentSuburb(suburb);
		// communicator.getSuburbCount();
	}

	public void chooseCriterion(final ScreenUpdater updater, Criterion criterion) {
		communicator.getBusinessesForCriterion(updater, criterion);
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

	public GoogleAnalyticsTracker getTracker() {
		return tracker;
	}

	public void setTracker(GoogleAnalyticsTracker tracker) {
		this.tracker = tracker;
	}

}
