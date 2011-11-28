package com.pinkdroid.view.components;


import com.pinkdroid.R;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SuburbInfoPanel extends LinearLayout{
	public static final int PADDING_VALUE=5;
	public SuburbInfoPanel(Context context, String title) {
		super(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.setMargins(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE);		
		this.setLayoutParams(lp);
		this.setOrientation(LinearLayout.VERTICAL);
		this.setPadding(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE);
		this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.product_secondary_panel_bg));
		LinearLayout tiltleLayout = new LinearLayout(context);
		tiltleLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
	
		TextView titleTV = new TextView(context);
		titleTV.setPadding(PADDING_VALUE, PADDING_VALUE, PADDING_VALUE, PADDING_VALUE);
		titleTV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		titleTV.setTextSize(20);
		titleTV.setTextColor(this.getResources().getColor(R.color.text_title));
		titleTV.setText(title);
		tiltleLayout.addView(titleTV);		
		this.addView(tiltleLayout);
	}

}
