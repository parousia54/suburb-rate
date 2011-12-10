package com.pinkdroid.view.components;

import java.util.Hashtable;
import java.util.LinkedList;

import com.pinkdroid.R;
import com.pinkdroid.logic.Controller;
import com.pinkdroid.model.ImageItem;
import com.pinkdroid.model.QueryResponse;
import com.pinkdroid.model.SensisAddress;
import com.pinkdroid.model.SensisContact;
import com.pinkdroid.model.SensisResult;
import com.pinkdroid.view.ScreenUpdater;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SensisResponseListViewAdapter extends BaseAdapter implements
		ScreenUpdater {
	private Context context;
	private QueryResponse sensisQueryResponse;
	private Hashtable<Integer, Bitmap> bitmaps;

	public SensisResponseListViewAdapter(Context context,
			QueryResponse sensisQueryResponse) {
		this.context = context;
		this.sensisQueryResponse = sensisQueryResponse;
		this.bitmaps = new Hashtable<Integer, Bitmap>();
	}

	@Override
	public int getCount() {
		return sensisQueryResponse.getCount();
	}

	@Override
	public Object getItem(int pos) {
		return sensisQueryResponse.getResults().get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		SensisResult result = sensisQueryResponse.getResults().get(pos);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(
					R.layout.custom_sensis_response_list_item, null);
		}
		TextView titleTV = (TextView) convertView
				.findViewById(R.id.custom_response_item_title);
		titleTV.setText(result.getName());

		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.custom_response_item_image);
		// System.out.println(result.getImageGallery().getFirst().getThumbnailUrl());
		// System.out.println(result.getImageGallery());
		// System.out.println(result.getImageGallery().getFirst());
		// System.out.println(result.getImageGallery().getFirst().getThumbnailUrl());
		if (result.getImageGallery() != null
				&& result.getImageGallery().size() > 0) {
			if (!bitmaps.containsKey(pos))
				Controller
						.getInstance()
						.getCommunicator()
						.downloadImage(
								this,
								result.getImageGallery().getFirst()
										.getThumbnailUrl(), pos);
			else
				imageView.setImageBitmap(bitmaps.get(pos));
		} else
			imageView.setVisibility(View.GONE);

		TextView listingTypeTV = (TextView) convertView
				.findViewById(R.id.custom_response_item_listing_type);
		listingTypeTV.setText("Listing Type: " + result.getListingType());

		LinearLayout contactPanel = (LinearLayout) convertView
				.findViewById(R.id.custom_response_item_contact_panel);
		contactPanel.removeAllViews();
		LinkedList<SensisContact> primaryContacts = result.getPrimaryContacts();
		for (int i = 0; i < primaryContacts.size(); i++) {
			SensisContact contact = primaryContacts.get(i);
			TextView contactTV = new TextView(context);
			contactTV.setTextColor(Controller.getInstance().getResources()
					.getColor(R.color.text_color));
			contactTV.setLinksClickable(true);
			contactTV.setAutoLinkMask(15);
			contactTV.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			contactTV.setText(contact.getType() + " - " + contact.getValue());
			contactPanel.addView(contactTV);
		}

		LinearLayout addressPanel = (LinearLayout) convertView
				.findViewById(R.id.custom_response_item_address_panel);
		addressPanel.removeAllViews();
		SensisAddress address = result.getPrimaryAddress();

		TextView addressTV = new TextView(context);
		addressTV.setTextColor(Controller.getInstance().getResources()
				.getColor(R.color.text_color));
		addressTV.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		if (address != null)
			addressTV.setText(address.getAddressLine() + ", "
					+ address.getSuburb() + ", " + address.getState() + " "
					+ address.getPostcode());
		addressPanel.addView(addressTV);
		return convertView;
	}

	@Override
	public void update(Object data) {
		if (data instanceof ImageItem) {
			ImageItem imageItem = (ImageItem) data;
			bitmaps.put(imageItem.getPosition(), imageItem.getBitmap());
			notifyDataSetInvalidated();
		}

	}

	@Override
	public void displayMessage(String title, String message) {
		// TODO Auto-generated method stub

	}

}
