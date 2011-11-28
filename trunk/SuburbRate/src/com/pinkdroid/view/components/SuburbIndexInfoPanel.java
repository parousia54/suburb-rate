package com.pinkdroid.view.components;

import com.pinkdroid.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SuburbIndexInfoPanel extends SuburbInfoPanel{

	public SuburbIndexInfoPanel(Context context, String title, String score, String ausRank, String stateRank, String ausPct, String statePct) {
		super(context, title);
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		layout.setOrientation(LinearLayout.VERTICAL);
		
		TextView scoreTV = new TextView(context);
		scoreTV.setPadding(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE);
		scoreTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		scoreTV.setTextSize(17);
		scoreTV.setTextColor(this.getResources().getColor(R.color.text_color));
		scoreTV.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
		scoreTV.setText("Score: "+score);		
		layout.addView(scoreTV);
		
		TextView ausRankTV = new TextView(context);
		ausRankTV.setPadding(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE);
		ausRankTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		ausRankTV.setTextSize(17);
		ausRankTV.setTextColor(this.getResources().getColor(R.color.text_color));
		ausRankTV.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
		ausRankTV.setText("AUS Ranking: "+ausRank+ " ("+ausPct+"%)");		
		layout.addView(ausRankTV);
		
		TextView stateRankTV = new TextView(context);
		stateRankTV.setPadding(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE);
		stateRankTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		stateRankTV.setTextSize(17);
		stateRankTV.setTextColor(this.getResources().getColor(R.color.text_color));
		stateRankTV.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
		stateRankTV.setText("State Ranking: "+stateRank + " ("+statePct+"%)");		
		layout.addView(stateRankTV);
		
		this.addView(layout);
	}

}
