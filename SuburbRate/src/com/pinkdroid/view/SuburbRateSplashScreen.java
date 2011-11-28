package com.pinkdroid.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.pinkdroid.R;
import com.pinkdroid.logic.Controller;

public class SuburbRateSplashScreen extends SuburbRateScreen {

	private void doWait() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void goNext() {
		dismissDialog(DIALOG_PROGRESS);
		Intent intent = new Intent(this, SuburbRateHomeScreen.class);
		startActivity(intent);

	}

	@Override
	public void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.splash_screen);

		LinearLayout splash = (LinearLayout) findViewById(R.id.splash_layout);
		splash.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.splashscreen));

		displayProgress("Data Fetching",
				"Getting a few stuff. Might take a little bit of time :)\n"
						+ "Rest assured it wont happen the next time ;)");

		AsyncTask<Object, Integer, Object> task = new AsyncTask<Object, Integer, Object>() {
			@Override
			protected Object doInBackground(Object... params) {
				try {
					Controller.getInstance().doDatabaseStuff();
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}

			@Override
			protected void onPostExecute(Object data) {
				goNext();
			}
		};
		task.execute("");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

}
