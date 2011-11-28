package com.pinkdroid.view.maps;

import java.util.Locale;

import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.pinkdroid.R;

public class MapResultsSelectorScreen extends MapActivity{
//	MapView mapView;
//	Geocoder gc;
//	
//	@Override
//	protected void onCreate(Bundle icicle) {
//		super.onCreate(icicle);
//		setContentView(R.layout.map_screen);
//		mapView = (MapView) findViewById(R.id.mapview);
//		gc = new Geocoder(MapResultsSelectorScreen.this, Locale.getDefault());
//		
//	}
//
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
