package com.pinkdroid.view;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinkdroid.R;
import com.pinkdroid.logic.Controller;
import com.pinkdroid.model.Criterion;
import com.pinkdroid.view.components.CriteriaGridAdapter;

public class SuburbRateCriteriaScreen extends SuburbRateScreen implements ScreenUpdater {

	private static String logtag = "CrieriaScreen";

	enum Choice {
		MAP, LIST
	}

	private Controller controller;
	private static final int NEGATIVE_LIMIT = -2;
	private static final int POSITIVE_LIMIT = 2;
	private static final int TOP_BOTTOM_MARGIN = 7;
	private static final int TEXT_LEFT_RIGHT_MARGIN = 0;
	private static final int LEFT_RIGHT_MARGIN = 50;
	protected static final int CHOICE_DIALOG = 1;
	protected static final int PROGRESS_DIALOG = 2;
	protected static final int NODATA_DIALOG = 3;

	protected static final int REALESTATE = 0;
	protected static final int DOMAIN = 1;

	private TextView totalValue;
	private Criterion selectedCriterion;
	private ImageButton realEstBtn, domainBtn;

	private Choice choice;
	ProgressDialog pDialog;
	AlertDialog aDialog, nDialog;

	class MinusClickListener implements OnClickListener {

		TextView value;

		Criterion criterion;

		public MinusClickListener(Criterion criterion, TextView value) {
			// TODO Auto-generated constructor stub
			this.value = value;
			this.criterion = criterion;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("minus " + value.getText());
			int val = Integer.parseInt(value.getText().toString());
			if (val-- > NEGATIVE_LIMIT) {
				value.setText(String.valueOf(val));
				criterion.setParameter((float) (val));
				controller.getCriterionValue(SuburbRateCriteriaScreen.this, criterion);
				// double totalVal =
				// controller.computeRankingCriteria(criterion);
				// totalValue.setText(String.valueOf(totalVal));

			}
		}
	}

	class PlusClickListener implements OnClickListener {

		TextView value;
		Criterion criterion;

		public PlusClickListener(Criterion criterion, TextView value) {
			// TODO Auto-generated constructor stub
			this.value = value;
			this.criterion = criterion;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("plus " + value.getText());
			int val = Integer.parseInt(value.getText().toString());
			if (val++ < POSITIVE_LIMIT) {
				value.setText(String.valueOf(val));	
				criterion.setParameter((float) (val ));
				controller.getCriterionValue(SuburbRateCriteriaScreen.this, criterion);

			}
		}

	}

	private void setListeners(Criterion criterion, Button minus, Button plus,
			final TextView value) {
		minus.setOnClickListener(new MinusClickListener(criterion, value));

		plus.setOnClickListener(new PlusClickListener(criterion, value));
	}

	public View createViewFromCriterion(final Criterion criterion) {

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View convertView = inflater.inflate(R.layout.criterion_item, null);
		TextView tv = (TextView) convertView.findViewById(R.id.criterion_name);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectedCriterion = criterion;
				controller.chooseCriterion(SuburbRateCriteriaScreen.this,
						selectedCriterion);
				
				showDialog(CHOICE_DIALOG);
			}
		});
		tv.setText(Html.fromHtml("<u>"+criterion.getName()+"</u>"));
		Button minus = (Button) convertView.findViewById(R.id.minus);
		TextView value = (TextView) convertView.findViewById(R.id.value);
		Button plus = (Button) convertView.findViewById(R.id.plus);
		value.setText("" + (int) criterion.getParameter());
		setListeners(criterion, minus, plus, value);
		return convertView;
	}

	private void addCriteria() {
		GridView grid = (GridView) this
				.findViewById(R.id.view_group_page);
		grid.setAdapter(new CriteriaGridAdapter(this));


	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		controller = Controller.getInstance();
		setContentView(R.layout.category_screen);
		TextView suburbname = (TextView) findViewById(R.id.suburb_name);
		suburbname.setText(controller.getApplicationState().getCurrentSuburb().getName());
		totalValue = (TextView) findViewById(R.id.total_rate);
		setTotalValue();

		realEstBtn = (ImageButton) findViewById(R.id.real_estate_button);
		domainBtn = (ImageButton) findViewById(R.id.domain_button);

		realEstBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lookForRealEstate(REALESTATE);
				Controller.getInstance().getTracker().trackEvent(
		                "Clicks",  // Category
		                "Button",  // Action
		                "Real Estate Website", // Label
		                0);
			}
		});

		domainBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lookForRealEstate(DOMAIN);
		        Controller.getInstance().getTracker().trackEvent(
		                "Clicks",  // Category
		                "Button",  // Action
		                "Domain Website", // Label
		                0);  
			}
		});

		addCriteria();
	}

	protected void lookForRealEstate(int source) {
		String url = "";
		switch (source) {
		case REALESTATE:
			url = "http://www.realestate.com.au/buy/in-"
					+ controller.getInstance().getApplicationState()
							.getCurrentSuburb().getPostcode() + "/list-1";
			break;
		case DOMAIN:
			url = "http://www.domain.com.au/Search/buy/" +
					"State/"+controller.getInstance().getApplicationState().getCurrentSuburb().getState()+
					"/Area/Suburb/"+controller.getInstance().getApplicationState().getCurrentSuburb().getName()+
					"?searchterm="+controller.getInstance().getApplicationState().getCurrentSuburb().getName();
			break;

		}
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);

	}

	private void setTotalValue() {
		double totalVal = controller.computeRankingCriteria();
		DecimalFormat df = new DecimalFormat("###.##");
		totalValue.setText(df.format(totalVal));
		if (totalVal >= 0) {
			totalValue.setTextColor(getResources().getColor(R.color.green));
		} else
			totalValue.setTextColor(getResources().getColor(R.color.red));
	}

	@Override
	public void update(Object data) {
		System.out.println("updated");
		if (data == null) {
			if (pDialog != null)
				pDialog.dismiss();
			if (choice != null) {
				switch (choice) {
				case MAP:
					goToMap(selectedCriterion);
					break;
				case LIST:
					goToList(selectedCriterion);
					break;
				}
			}
		}
		if (data instanceof Double) {
			System.out.println("updating total value " + data);
			setTotalValue();
		}

	}

	private void dismissDialogs() {
		// TODO Auto-generated method stub
		if (pDialog != null)
			pDialog.dismiss();
		if (aDialog != null)
			aDialog.dismiss();
	}

	@Override
	public void displayMessage(String title, String message) {
		// TODO Auto-generated method stub

	}

	protected android.content.DialogInterface.OnClickListener getMapClick() {
		return new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				System.out.println("display");

				if (selectedCriterion == controller.getApplicationState()
						.getSelectedCriterion())
					goToMap(selectedCriterion);
				else {
					choice = Choice.MAP;
					showDialog(PROGRESS_DIALOG);
				}
			}

		};
	}

	protected android.content.DialogInterface.OnClickListener getListClick() {
		return new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("display");

				if (selectedCriterion == controller.getApplicationState()
						.getSelectedCriterion())
					goToList(selectedCriterion);
				else {
					choice = Choice.LIST;
					showDialog(PROGRESS_DIALOG);
				}

			}

		};
		
	}
		protected android.content.DialogInterface.OnClickListener showNoData() {
			return new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.out.println("display");
					dismissDialog(NODATA_DIALOG);
					}
			};
		}

	private void goToList(Criterion selectedCriterion) {
		// TODO Auto-generated method stub
		if(controller.getInstance().getApplicationState().getSensisQueryResponse().getTotalResults()>0)
		{
			Intent intent = new Intent(this, SuburbRateResultScreen.class);
			startActivity(intent);
		}
		else
		{
			showDialog(NODATA_DIALOG);
		}
	}

	private void goToMap(Criterion selectedCriterion) {
		// TODO Auto-generated method stub
		if(controller.getInstance().getApplicationState().getSensisQueryResponse().getTotalResults()>0)
		{
			Intent intent = new Intent(this, SuburbRateMapScreen.class);
			startActivity(intent);
		}
		else
		{
			showDialog(NODATA_DIALOG);
		}
	}

	protected ProgressDialog createProgressDialog() {
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("Fetching businesses details");
		// dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		return dialog;
	}

	protected AlertDialog createMessageDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		return builder.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle("For detailed view please choose:")
				.setNegativeButton("Map", getMapClick())
				.setPositiveButton("List", getListClick()).create();
	}
	
	protected AlertDialog noDataDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		return builder.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle("OOPS!")
				.setMessage("There is no data unfortunately")
				.setPositiveButton("OK", showNoData()).create();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case CHOICE_DIALOG:
			aDialog = createMessageDialog();
			return aDialog;
		case PROGRESS_DIALOG:
			pDialog = createProgressDialog();
			return pDialog;
		case NODATA_DIALOG:
			nDialog = noDataDialog();
			return nDialog;
		}

		return null;

	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		dialog.show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		choice = null;
		dismissDialogs();
	}

}
