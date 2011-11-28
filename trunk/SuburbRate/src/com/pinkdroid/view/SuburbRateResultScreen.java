package com.pinkdroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.pinkdroid.R;
import com.pinkdroid.logic.Controller;
import com.pinkdroid.model.QueryResponse;
import com.pinkdroid.model.SensisAddress;
import com.pinkdroid.view.components.SensisResponseListViewAdapter;

public class SuburbRateResultScreen extends SuburbRateScreen{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_screen);

//		QueryResponse response = new QueryResponse();
//		
//		LinkedList<SensisContact> contactList = new LinkedList<SensisContact>();
//		SensisContact contact = new SensisContact();
//		contact.setDescription("This is the test contact");
//		contact.setType("Type");
//		contact.setValue("Value");
//		contactList.add(contact);
//		
//		SensisAddress address = new SensisAddress();
//		address.setAddressLine("Address Line");
//		address.setPostcode("3000");
//		address.setState("VIC");
//		address.setSuburb("Melbourne");
//		
//		SensisResult result = new SensisResult();
//		result.setId("1234");
//		result.setListingType("Adult");
//		result.setName("Mens Gallery");
//		result.setPrimaryAddress(address);
//		result.setPrimaryContacts(contactList);
//		
//		LinkedList<SensisResult> resultList = new LinkedList<SensisResult>();
//		response.setResults(resultList);
//		response.setTotalPages(10);
//		response.setCount(100);
//		response.setTotalResults(100);
//		for(int i = 0; i < 100; i++){
//			resultList.add(result);			
//		}
		
		
//		Suburb suburb = Controller.getInstance().getApplicationState().getCurrentSuburb();
//		SensisCategory category = new SensisCategory();
//		category.setId("24082");
//		category.setName("Schools--General");
//		category.setSensitive(false);
//		ArrayList<SensisCategory> catList = new ArrayList<SensisCategory>();
//		QueryResponse response = SensisAPICaller.getInstance().searchBusinessesInSuburbByCategories(suburb, catList);
//		
//		
		final QueryResponse response = Controller.getInstance().getApplicationState().getSensisQueryResponse();
		if(response!=null){
			ListView responseListView = (ListView) findViewById(R.id.result_screen_response_listview);
			SensisResponseListViewAdapter adapter = new SensisResponseListViewAdapter(this, response);
			responseListView.setAdapter(adapter);
			responseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					Intent intent = new Intent(SuburbRateResultScreen.this, 
							SuburbRateDirectionScreen.class);
					SensisAddress sAddress = response.getResults().get(pos).getPrimaryAddress();
					String address = sAddress.getAddressLine()+","
										+sAddress.getSuburb()+","
										+sAddress.getState()+"-"
										+sAddress.getPostcode();
					intent.putExtra("address",address);
					startActivity(intent);
					
				}
			});
			
		}
	}

}
