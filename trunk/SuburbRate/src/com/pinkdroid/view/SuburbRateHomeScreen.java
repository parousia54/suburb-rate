package com.pinkdroid.view;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pinkdroid.R;
import com.pinkdroid.logic.Controller;
import com.pinkdroid.model.Suburb;
import com.pinkdroid.model.SuburbStats;
import com.pinkdroid.persistent.DatabaseManager;
import com.pinkdroid.view.components.ItemAutoTextAdapter;
import com.pinkdroid.view.components.SuburbIndexInfoPanel;
import com.pinkdroid.view.components.SuburbRatingInfoPanel;
import com.pinkdroid.view.components.SuburbSafetyRatingPanel;
import com.pinkdroid.ws.SensisAPICaller;

public class SuburbRateHomeScreen extends SuburbRateScreen {
	private Button goButton;
	private AutoCompleteTextView searchInput;
	private static String logname="SuburbRateHomeScreen";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
//		getWindow().setWindowAnimations();
		goButton = (Button) findViewById(R.id.goBtn);		
		goButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(logname, "Got here successfully");
				Intent intent = new Intent(SuburbRateHomeScreen.this,
						SuburbRateCriteriaScreen.class);
				startActivity(intent);
			}
		});
		
		searchInput = (AutoCompleteTextView)this.findViewById(R.id.home_screen_search_input);
		ItemAutoTextAdapter itemAdapter = new ItemAutoTextAdapter(this, new DatabaseManager(this));
		searchInput.setAdapter(itemAdapter);
		searchInput.setOnItemClickListener(itemAdapter);
		Suburb suburb = Controller.getInstance().getApplicationState().getCurrentSuburb();
		if(suburb!=null){
			displaySuburbInfo();
		}
	
	}
	

	public void getSuburbInfo(){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
		Suburb suburb = Controller.getInstance().getApplicationState().getCurrentSuburb();
		if(suburb!=null){
			displayProgress("", "Searching for suburb info....");
			Controller.getInstance().getCommunicator().getSuburbInfo(this, suburb);
		}
	}
	@Override
	public void update(Object data){
		super.update(data);
		if(data!=null)
			displaySuburbInfo();
		else
			displayMessage("Warning", "No info available for this suburb!!");
	}
	
	protected void trackSuburbSearch(Suburb suburb){
		Controller.getInstance().getTracker().trackEvent(
                "Search",  // Category
                "Found",  // Action
                suburb.getName(), // Label
                suburb.getPostcode());
	}
	
	public void displaySuburbInfo(){
		Suburb suburb = Controller.getInstance().getApplicationState().getCurrentSuburb();
		SuburbStats stats = suburb.getStatistics();
		LinearLayout layout = (LinearLayout)findViewById(R.id.home_screen_info_panel);
		trackSuburbSearch(suburb);
		layout.removeAllViews();
		if(stats!=null){
			SensisAPICaller caller = SensisAPICaller.getInstance();
			ArrayList<String> images = caller.searchBusinessImagesInSuburbByCategories(suburb);
			
			Gallery gallery = (Gallery) findViewById(R.id.gallery);
			ImageAdapter x = new ImageAdapter(this);
			x.setMyRemoteImages(images);
		    gallery.setAdapter(x);
		    if(images.size() > 1)
		    	gallery.setSelection(1);
		    
		    gallery.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView parent, View v, int position, long id) {
		            Toast.makeText(SuburbRateHomeScreen.this, "" + position, Toast.LENGTH_SHORT).show();
		        }
		    });
		    
//		    System.out.println("Postcode === "+caller.searchPostCodeByLocation(-37.81125,144.9656));
		    
			SuburbRatingInfoPanel populationPanel = new SuburbRatingInfoPanel(this, "Population", String.valueOf(stats.getPopulation())+ " (in "+stats.getDataYear()+")");
			SuburbSafetyRatingPanel safetyPanel = new SuburbSafetyRatingPanel(this, "Safety Ratings", String.valueOf(stats.getSafetyYear()),String.valueOf(stats.getSafetyWalking()),String.valueOf(stats.getSafetyTransport()));
			SuburbIndexInfoPanel economicPanel = new SuburbIndexInfoPanel(this, "Economic Resources Index", String.valueOf(stats.getEconomic().getScore()), String.valueOf(stats.getEconomic().getAus_rank()), String.valueOf(stats.getEconomic().getState_rank()),String.valueOf(stats.getEconomic().getAus_pct()), String.valueOf(stats.getEconomic().getState_pct()));
			SuburbIndexInfoPanel educationPanel = new SuburbIndexInfoPanel(this, "Education Index", String.valueOf(stats.getEducation().getScore()), String.valueOf(stats.getEducation().getAus_rank()), String.valueOf(stats.getEducation().getState_rank()),String.valueOf(stats.getEducation().getAus_pct()), String.valueOf(stats.getEducation().getState_pct()));
			SuburbIndexInfoPanel socioPanel = new SuburbIndexInfoPanel(this, "Socio-Economic Ranking", String.valueOf(stats.getAdv_disAdv().getScore()), String.valueOf(stats.getAdv_disAdv().getAus_rank()), String.valueOf(stats.getAdv_disAdv().getState_rank()),String.valueOf(stats.getAdv_disAdv().getAus_pct()), String.valueOf(stats.getAdv_disAdv().getState_pct()));

			layout.addView(populationPanel);
			layout.addView(safetyPanel);
			layout.addView(economicPanel);
			layout.addView(educationPanel);
			layout.addView(socioPanel);
		}
		else{
			Toast.makeText(this,"No info available for this suburb!!", Toast.LENGTH_SHORT).show();
		}
		
	}

	public Button getGoButton() {
		return goButton;
	}
	
	public class ImageAdapter extends BaseAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;

	    private ArrayList<String> myRemoteImages = new ArrayList<String>();

	    public ImageAdapter(Context c) {
	        mContext = c;
	        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.SuburbRateHomeScreen);
	        mGalleryItemBackground = attr.getResourceId(
	                R.styleable.SuburbRateHomeScreen_android_galleryItemBackground, 0);
	        attr.recycle();
	    }

	    public ArrayList<String> getMyRemoteImages() {
			return myRemoteImages;
		}

		public void setMyRemoteImages(ArrayList<String> myRemoteImages) {
			this.myRemoteImages = myRemoteImages;
		}



		public int getCount() {
	        return myRemoteImages.size();
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView = new ImageView(mContext);
	        imageView.setLayoutParams(new Gallery.LayoutParams(200, 150));
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        //imageView.setBackgroundResource(R.drawable.pink);
	
	        try {
				/* Open a new URL and get the InputStream to load data from it. */
	        	if (position >= myRemoteImages.size()) 
	        		return imageView;
				URL aURL = new URL(myRemoteImages.get(position));
				URLConnection conn = aURL.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				/* Buffered is always good for a performance plus. */
				BufferedInputStream bis = new BufferedInputStream(is);
				/* Decode url-data to a bitmap. */
				Bitmap bm = BitmapFactory.decodeStream(bis);
				bis.close();
				is.close();
				/* Apply the Bitmap to the ImageView that will be returned. */
				imageView.setImageBitmap(bm);
			} catch (IOException e) {
				imageView.setImageResource(android.R.drawable.gallery_thumb);
				Log.e("DEBUGTAG", "Remtoe Image Exception", e);
			}
        
        /* Image should be scaled as width/height are set. */
	        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        /* Set the Width/Height of the ImageView. */
	        return imageView;
    }

    /** Returns the size (0.0f to 1.0f) of the views
     * depending on the 'offset' to the center. */
    public float getScale(boolean focused, int offset) {
    	/* Formula: 1 / (2 ^ offset) */
        return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset)));
    }
	}

}