package com.pinkdroid.networking;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.pinkdroid.logic.Controller;
import com.pinkdroid.model.Criterion;
import com.pinkdroid.model.ImageItem;
import com.pinkdroid.model.QueryResponse;
import com.pinkdroid.model.Suburb;
import com.pinkdroid.view.ScreenUpdater;
import com.pinkdroid.ws.RatingAPICaller;
import com.pinkdroid.ws.SensisAPICaller;

public class Communicator {

	public void getSuburbInfo(final ScreenUpdater updater, final Suburb suburb) {
		AsyncTask<Object, Integer, Suburb> task = new AsyncTask<Object, Integer, Suburb>() {
			@Override
			protected Suburb doInBackground(Object... params) {
				try {
					RatingAPICaller.updateSuburb(suburb);
					return suburb;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			@Override
			protected void onPostExecute(Suburb data) {
				updater.update(data);
			}
		};
		task.execute("");
	}

	public void getSuburbCount(final ScreenUpdater updater,
			final Criterion criterion) {
		AsyncTask<Object, Integer, Double> task = new AsyncTask<Object, Integer, Double>() {

			@Override
			protected Double doInBackground(Object... params) {
				try {
					Controller controller = Controller.getInstance();
					HashMap<Criterion, Double> ratings = new HashMap<Criterion, Double>();
					SensisAPICaller caller = SensisAPICaller.getInstance();
					double result = (double) caller
							.searchBusinessesCountInSuburbByCategories(
									controller.getApplicationState()
											.getCurrentSuburb(), criterion
											.getCategories(), false);

					return result;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			@Override
			protected void onPostExecute(Double result) {
				Controller controller = Controller.getInstance();
				controller.getRatings().put(criterion, result);
				System.out.println("Result " + result);
				updater.update(result);
			}
		};
		task.execute("");
	}

	public void getBusinessesForCriterion(final ScreenUpdater updater,
			final Criterion criterion) {
		AsyncTask<Object, Integer, QueryResponse> task = new AsyncTask<Object, Integer, QueryResponse>() {

			@Override
			protected QueryResponse doInBackground(Object... params) {
				try {
					Controller controller = Controller.getInstance();
					SensisAPICaller caller = SensisAPICaller.getInstance();
					QueryResponse response = caller
							.searchBusinessesInSuburbByCategories(controller
									.getApplicationState().getCurrentSuburb(),
									criterion.getCategories());
					System.out.println("Finished");
					return response;

				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			@Override
			protected void onPostExecute(QueryResponse response) {
				System.out.println("Set data");
				Controller controller = Controller.getInstance();
				controller.getApplicationState()
						.setBusinessesForSelectedCriterion(response);
				controller.getApplicationState()
						.setSelectedCriterion(criterion);
				updater.update(null);
			}
		};
		task.execute("");
	}

	public void downloadImage(final ScreenUpdater updater, final String url, final int position) {
		AsyncTask<String, Integer, Bitmap> task = new AsyncTask<String, Integer, Bitmap>() {

			protected Bitmap doInBackground(String... params) {
				ImageDownloader downloader = new ImageDownloader();
				byte[] data = null;
				try {
					params[0]= url;
					data = downloader.heavyTask(params);
					System.out.println("Downloaded "+url);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Bitmap bitmap = data == null ? null : BitmapFactory
						.decodeByteArray(data, 0, data.length);

				return bitmap;
			}

			@Override
			protected void onPostExecute(Bitmap imageData) {
				ImageItem imageItem = new ImageItem();
				imageItem.setBitmap(imageData);
				imageItem.setPosition(position);
				updater.update(imageItem);

			}

		};
		task.execute("");

	}
}
