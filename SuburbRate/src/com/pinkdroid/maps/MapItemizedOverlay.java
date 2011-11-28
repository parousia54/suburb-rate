package com.pinkdroid.maps;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.pinkdroid.logic.Controller;
import com.pinkdroid.model.SensisAddress;
import com.pinkdroid.view.SuburbRateDirectionScreen;
import com.pinkdroid.view.SuburbRateMapScreen;
import com.pinkdroid.view.SuburbRateResultScreen;

public class MapItemizedOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private HashMap<OverlayItem, String> leadref = new HashMap<OverlayItem, String>();
	private Context c;
	private SuburbRateMapScreen screen;

	public MapItemizedOverlay(SuburbRateMapScreen screen, Drawable defaultMarker,
			MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		this.screen = screen;
		c = mapView.getContext();
	}

	public void addOverlay(OverlayItem overlay, String itemLeadId) {
		m_overlays.add(overlay);
		leadref.put(overlay, itemLeadId);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
		Intent intent = new Intent(screen, 
				SuburbRateDirectionScreen.class);
		String address = item.getSnippet();
		intent.putExtra("address",address);
		screen.startActivity(intent);
		
		return true;

	}


	public void refreshBalloonView(int index) {
		onTap(index);
	}

}
