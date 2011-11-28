package com.pinkdroid.view.components;

import com.pinkdroid.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SuburbSafetyRatingPanel extends SuburbInfoPanel{

	public SuburbSafetyRatingPanel(Context context, String title, String year,String walking, String transport) {
		super(context, title);
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		layout.setOrientation(LinearLayout.VERTICAL);
		
		TextView yearTV = new TextView(context);
		yearTV.setPadding(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE);
		yearTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		yearTV.setTextSize(17);
		yearTV.setTextColor(this.getResources().getColor(R.color.text_color));
		yearTV.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
		yearTV.setText("Safety data year: "+year);		
		layout.addView(yearTV);
		
		TextView walkingTV = new TextView(context);
		walkingTV.setPadding(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE);
		walkingTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		walkingTV.setTextSize(17);
		walkingTV.setTextColor(this.getResources().getColor(R.color.text_color));
		walkingTV.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
		walkingTV.setText("Walking safety score: "+walking);		
		layout.addView(walkingTV);
		
		TextView transportTV = new TextView(context);
		transportTV.setPadding(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE);
		transportTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		transportTV.setTextSize(17);
		transportTV.setTextColor(this.getResources().getColor(R.color.text_color));
		transportTV.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
		transportTV.setText("Transport safety score: "+transport);		
		layout.addView(transportTV);
		
		this.addView(layout);
	}

}
