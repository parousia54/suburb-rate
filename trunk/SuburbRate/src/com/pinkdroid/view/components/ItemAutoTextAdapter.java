package com.pinkdroid.view.components;

import com.pinkdroid.logic.Controller;
import com.pinkdroid.model.Suburb;
import com.pinkdroid.persistent.DatabaseAccessHelper;
import com.pinkdroid.persistent.DatabaseManager;
import com.pinkdroid.view.SuburbRateHomeScreen;
import com.pinkdroid.ws.RatingAPICaller;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import com.pinkdroid.R;
public class ItemAutoTextAdapter extends SimpleCursorAdapter implements
		android.widget.AdapterView.OnItemClickListener {
	private DatabaseManager dbManager;
	private Context context;
	final static int[] to = new int[] { R.id.custom_dropdown_suburb, R.id.custom_dropdown_postcode , R.id.custom_dropdown_state};
	final static String[] from = new String[] { "areaname", "postcode", "statename"};
	public ItemAutoTextAdapter(Context context, DatabaseManager dbManager) {
		super(context, R.layout.custom_search_dropdown_list, null, from,to);
		this.dbManager = dbManager;
		this.context = context;
	}

	@Override
	public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
		if (getFilterQueryProvider() != null) {
			return getFilterQueryProvider().runQuery(constraint);
		}

		Cursor cursor = DatabaseAccessHelper.getMatchingSuburbSearch(dbManager,
				constraint.toString());
		((Activity) context).startManagingCursor(cursor);
		cursor.moveToFirst();
		return cursor;
	}

	@Override
	public String convertToString(Cursor cursor) {
		final int columnIndex = cursor.getColumnIndexOrThrow("areaname");
		final String str = cursor.getString(columnIndex);
		return str;
	}

	@Override
	public void onItemClick(AdapterView<?> listView, View view, int position,
			long id) {
		// Get the cursor, positioned to the corresponding row in the result
		Cursor c = (Cursor) listView.getItemAtPosition(position);
		
		String suburbName = c.getString(c.getColumnIndex("areaname"));
		int postcode = Integer.parseInt(c.getString(c.getColumnIndex("postcode")));
		String state = c.getString(c.getColumnIndex("statename"));
		
		Suburb suburb = new Suburb();
		suburb.setName(suburbName);
		suburb.setPostcode(postcode);
		suburb.setState(state);
		((SuburbRateHomeScreen)context).getGoButton().setVisibility(View.VISIBLE);		
		Controller.getInstance().setCurrentSuburb(suburb);
		((SuburbRateHomeScreen)context).getSuburbInfo();	
	}
}
