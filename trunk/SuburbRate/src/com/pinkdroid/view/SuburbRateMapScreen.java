package com.pinkdroid.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.pinkdroid.R;
import com.pinkdroid.logic.Controller;
import com.pinkdroid.maps.MapItemizedOverlay;
import com.pinkdroid.model.Criterion;
import com.pinkdroid.model.QueryResponse;
import com.pinkdroid.model.SensisCategory;
import com.pinkdroid.model.SensisResult;
import com.pinkdroid.model.Suburb;
import com.pinkdroid.util.DataUtility;
import com.pinkdroid.view.component.TransparentDialog;
import com.pinkdroid.ws.SensisAPICaller;

public class SuburbRateMapScreen extends MapActivity {

	private static String logtag = "SuburbRateMapScreen";

	MapView mapView;
	Geocoder gc;

	List<Overlay> mapOverlays;
	MapItemizedOverlay itemizedOverlay;
	MapController mapcon;

	TransparentDialog mapTransparentDialog;

	Collection<Criterion> critList;
	String selectionCategory;
	LinkedList<SensisCategory> categories;
	Spinner categorySpinner;

	Suburb suburb = new Suburb();

	private int minLatitude = (int) (+81 * 1E6);
	private int maxLatitude = (int) (-81 * 1E6);
	private int minLongitude = (int) (+181 * 1E6);
	private int maxLongitude = (int) (-181 * 1E6);

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.map_screen);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		gc = new Geocoder(SuburbRateMapScreen.this, Locale.getDefault());

		mapTransparentDialog = (TransparentDialog) findViewById(R.id.transparent_panel);

		mapOverlays = mapView.getOverlays();

		QueryResponse qR = Controller.getInstance().getApplicationState()
				.getBusinessesForSelectedCriterion();
		
		suburb = Controller.getInstance().getApplicationState().getCurrentSuburb();
		
		Criterion selectedCr = Controller.getInstance().getApplicationState()
				.getSelectedCriterion();

		critList = Controller.getInstance().getCriteria();
		categories = new LinkedList<SensisCategory>();

		categorySpinner = (Spinner) findViewById(R.id.trans_category_spinner);
		ArrayList<String> spinnerValues = new ArrayList<String>();
		spinnerValues = (ArrayList<String>) DataUtility
				.removeDuplicatesfromArrayList(spinnerValues);
		spinnerValues.add("All");
		Collections.sort(spinnerValues);
		ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, spinnerValues);

		for (Criterion c : critList) {
			spinnerValues.add(c.getName());
		}
		categorySpinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorySpinner.setAdapter(categorySpinnerAdapter);

		DataUtility.setSelectedItem(categorySpinner, selectedCr.getName());

		categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long id) {
				selectionCategory = categorySpinner.getSelectedItem()
						.toString();
				redrawMap(selectionCategory);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}

	protected void redrawMap(String selectionCategory) {
		if (!mapOverlays.isEmpty()) {
			mapOverlays.clear();
			mapView.invalidate();
		}
		
		Log.d(logtag, "Selected Category : " + selectionCategory);
		for (Criterion c : critList) {
			if (selectionCategory == null || selectionCategory.matches("All")) {
				categories = c.getCategories();
			} else if (c.getName().matches(selectionCategory)) {
				categories = c.getCategories();
			}
		}

		QueryResponse queryResponse = SensisAPICaller.getInstance()
				.searchBusinessesInSuburbByCategories(suburb, categories);
		if (queryResponse != null) {
			LinkedList<SensisResult> sensisResults = queryResponse.getResults();
			if (sensisResults != null) {
				Log.d(logtag, "Number of results : " + sensisResults.size());
				int numResults = sensisResults.size();
				if (numResults > 0) {
					mapOverlays = mapView.getOverlays();
					Drawable drawable = getResources().getDrawable(
							R.drawable.map_pin);
					if (mapView == null) {
						Log.d(logtag, "mapView is null now!!");
					}
					itemizedOverlay = new MapItemizedOverlay(
							SuburbRateMapScreen.this, drawable, mapView);
					for (SensisResult s : sensisResults) {
						OverlayItem overlayItem = null;
						GeoPoint gp = null;
						int latitude = (int) (Double.parseDouble(s
								.getPrimaryAddress().getLatitude()) * 1E6);
						int longitude = (int) (Double.parseDouble(s
								.getPrimaryAddress().getLongitude()) * 1E6);
						if (latitude != 0 && longitude != 0) {
							minLatitude = (minLatitude > latitude) ? latitude
									: minLatitude;
							maxLatitude = (maxLatitude < latitude) ? latitude
									: maxLatitude;
							minLongitude = (minLongitude > longitude) ? longitude
									: minLongitude;
							maxLongitude = (maxLongitude < longitude) ? longitude
									: maxLongitude;
							Log.d(logtag, "Latitude: " + latitude);
							Log.d(logtag, "Longitude: " + longitude);

							gp = new GeoPoint(latitude, longitude);
							String title = s.getName() + "\n"
									+ s.getListingType();
							String snippet = "Address : \n"
									+ s.getPrimaryAddress().getAddressLine()
									+ "\n" + s.getPrimaryAddress().getSuburb()
									+ "\n" + s.getPrimaryAddress().getState()
									+ "\n"
									+ s.getPrimaryAddress().getPostcode();
							overlayItem = new OverlayItem(gp, title, snippet);
							itemizedOverlay.addOverlay(overlayItem, s.getId());
						}

					}
				}
			}
		}

		mapOverlays.add(itemizedOverlay);
		mapView.setBuiltInZoomControls(true);

		mapcon = mapView.getController();
		mapcon.zoomToSpan((maxLatitude - minLatitude),
				(maxLongitude - minLongitude));
		mapcon.animateTo(new GeoPoint((maxLatitude + minLatitude) / 2,
				(maxLongitude + minLongitude) / 2));

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
