package com.pinkdroid.view.components;

import com.pinkdroid.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SuburbRatingInfoPanel extends SuburbInfoPanel {

	public SuburbRatingInfoPanel(Context context, String title, String rating) {
		super(context, title);

		LinearLayout ratingLayout = new LinearLayout(context);
		ratingLayout.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));

		TextView ratingTV = new TextView(context);
		ratingTV.setPadding(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE,
				PADDING_VALUE);
		ratingTV.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		ratingTV.setTextSize(17);
		ratingTV.setTextColor(this.getResources().getColor(R.color.text_color));
		ratingTV.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
		ratingTV.setText(rating);
		ratingLayout.addView(ratingTV);

		this.addView(ratingLayout);
	}

}
