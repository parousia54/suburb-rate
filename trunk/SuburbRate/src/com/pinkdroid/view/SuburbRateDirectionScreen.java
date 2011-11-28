package com.pinkdroid.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class SuburbRateDirectionScreen extends SuburbRateScreen{

	String address;
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		address = this.getIntent().getStringExtra("address");
		
		Intent intent = new Intent(
									android.content.Intent.ACTION_VIEW,
									Uri.parse("http://maps.google.com/maps" +
												"?saddr=&daddr=" +address));
		startActivity(intent);

	}
	
	

}
