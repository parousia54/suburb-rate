package com.pinkdroid.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pinkdroid.model.QueryResponse;
import com.pinkdroid.model.SensisCategory;
import com.pinkdroid.model.Suburb;
import com.pinkdroid.networking.NetworkEngine;

public class SensisAPICaller {

	private final static String API_URL = "http://api.sensis.com.au/ob-20110511/test/search";
	private final static int TIMEOUT = 10000;

	private final static String CATEGORYID = "categoryId";
	private static final String POSTCODE = "postcode";
	private static final String ROWS = "rows";
	private static final String CONTENT = "content";
	private static final String KEY = "key";
	private static final String[] sensisApiKeys = {"ergdteane92h6u9hycnhkuvy", "26zac3pbk9fdq6ane8cqk3s2"};
	private static int rrIndex=0;
	private static final String QUERY = "query";
	private static final String LOCATION = "location";
	
	private static final int SEARCH_LIMIT = 50;
	private static final int RETRIES = 3;

	private static SensisAPICaller instance;

	private SensisAPICaller() {
	}

	public static SensisAPICaller getInstance() {
		if (instance == null)
			instance = new SensisAPICaller();
		return instance;
	}
	
	protected static String getSensisApiKey() {
		rrIndex=(rrIndex+1)%sensisApiKeys.length;
		System.out.println("The key no. "+rrIndex+","+sensisApiKeys[rrIndex]+" is used");
		return sensisApiKeys[rrIndex];
	}
	
	/*http://api.sensis.com.au/ob-20110511/test/search?location=-37.81125,144.9656&key=26zac3pbk9fdq6ane8cqk3s2*/
	public int searchPostCodeByLocation(double latitude, double longtitude) {
		// ?categoryId=14532&categoryId=41718
		// http://api.sensis.com.au/ob-20110511/test/search?key=26zac3pbk9fdq6ane8cqk3s2&query=schools&rows=1&categoryId=21237&postcode=3000&state=VIC&content=shortDescriptor

		NetworkEngine nengine = NetworkEngine.getInstance();
		Hashtable<String, String> headers = new Hashtable<String, String>();
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		
		parameters.add(new BasicNameValuePair(KEY, getSensisApiKey()));
		parameters.add(new BasicNameValuePair(LOCATION, ""+latitude+","+longtitude));
		try {

			byte[] bytes = null;

			for (int i = 0; i < RETRIES && bytes == null; i++)
				bytes = nengine.getRawPair(API_URL, headers, parameters,
					TIMEOUT);

			if (bytes != null) {
				String result = new String(bytes);
				JSONObject json = new JSONObject(result);
				JSONArray array = json.getJSONArray("results");
				
				System.out.println("111");
				if (array.length() == 0) return 0;

				System.out.println("222");
				for (int i = 0; i < array.length(); i++)
				{
					System.out.println("333");
					JSONObject row = array.getJSONObject(i);
					if (row == null)
						return 0;

					System.out.println("444");
					JSONObject prime = row.getJSONObject("primaryAddress");
					if (prime == null) 
						continue;

					System.out.println("555");
					return prime.getInt("postcode");
				}
				return 0;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// http://api.sensis.
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public int searchBusinessesCountInSuburbByCategories(Suburb suburb,
			LinkedList<SensisCategory> categories, boolean wholeState) {
		// ?categoryId=14532&categoryId=41718
		// http://api.sensis.com.au/ob-20110511/test/search?key=26zac3pbk9fdq6ane8cqk3s2&query=schools&rows=1&categoryId=21237&postcode=3000&state=VIC&content=shortDescriptor

		NetworkEngine nengine = NetworkEngine.getInstance();
		Hashtable<String, String> headers = new Hashtable<String, String>();
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		
		for (SensisCategory category : categories) {
			parameters
					.add(new BasicNameValuePair(CATEGORYID, category.getId()));
		}

		if (!wholeState)
			parameters.add(new BasicNameValuePair(POSTCODE, String
				.valueOf(suburb.getPostcode())));
		parameters.add(new BasicNameValuePair(ROWS, "1"));
//		parameters.add(new BasicNameValuePair(CONTENT, "shortDescriptor"));
		parameters.add(new BasicNameValuePair(KEY, getSensisApiKey()));
		parameters.add(new BasicNameValuePair(QUERY, suburb.getState()));
		try {

			byte[] bytes = null;

			for (int i = 0; i < RETRIES && bytes == null; i++)
				bytes = nengine.getRawPair(API_URL, headers, parameters,
					TIMEOUT);

			if (bytes != null) {

				String result = new String(bytes);
				JSONObject json = new JSONObject(result);
				return json.getInt("totalResults");
		
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// http://api.sensis.
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<String> searchBusinessImagesInSuburbByCategories(Suburb suburb) {
		ArrayList<String> imagesURL = new ArrayList<String>();
		
		NetworkEngine nengine = NetworkEngine.getInstance();
		Hashtable<String, String> headers = new Hashtable<String, String>();
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		
		parameters.add(new BasicNameValuePair(POSTCODE, String
				.valueOf(suburb.getPostcode())));
		parameters.add(new BasicNameValuePair(ROWS, "10"));
		parameters.add(new BasicNameValuePair(CONTENT, "imageGallery"));
		parameters.add(new BasicNameValuePair(KEY, getSensisApiKey()));
		parameters.add(new BasicNameValuePair(QUERY, suburb.getState()));
		try {

			byte[] bytes = nengine.getRawPair(API_URL, headers, parameters,
					TIMEOUT);


			
			if (bytes != null) {

				String result = new String(bytes);
				JSONObject json = new JSONObject(result);
				JSONArray array = json.getJSONArray("results");
				if (array.length() == 0) return null;
				int id = 0;
				
				for (int i = 0; i < array.length(); i++)
				{
					JSONObject row = array.getJSONObject(i);
					if (row == null || id >= 10)
						return imagesURL;
					JSONArray gallery = row.getJSONArray("imageGallery");
					if (gallery == null) 
						continue;
					for (int j = 0; j < gallery.length(); j++)
					{
						JSONObject obj = gallery.getJSONObject(j);
						if (obj == null) break;
						String thumb = obj.getString("thumbnailUrl");
						if (thumb != null) 
						{
							imagesURL.add(thumb);
							id++;
							if(id >= 10)
								return imagesURL;
						}
					}
				}
				return imagesURL;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// http://api.sensis.
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imagesURL;
	}
	
	public QueryResponse searchBusinessesInSuburbByCategories(Suburb suburb,
			Collection<SensisCategory> categories) {
		// ?categoryId=14532&categoryId=41718G
		// http://api.sensis.com.au/ob-20110511/test/search?key=26zac3pbk9fdq6ane8cqk3s2&query=schools&rows=1&categoryId=21237&postcode=3000&state=VIC&content=shortDescriptor

		NetworkEngine nengine = NetworkEngine.getInstance();
		Hashtable<String, String> headers = new Hashtable<String, String>();
		ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		
		for (SensisCategory category : categories) {
			parameters
					.add(new BasicNameValuePair(CATEGORYID, category.getId()));
		}

		parameters.add(new BasicNameValuePair(POSTCODE, String.valueOf(suburb
				.getPostcode())));
		parameters.add(new BasicNameValuePair(ROWS, String
				.valueOf(SEARCH_LIMIT)));
//		parameters.add(new BasicNameValuePair(CONTENT, "mediumDescriptor"));
		parameters.add(new BasicNameValuePair(KEY, getSensisApiKey()));
		parameters.add(new BasicNameValuePair(QUERY, suburb.getState()));
		try {

			byte[] bytes = null;
			for (int i = 0; i < RETRIES && bytes == null; i++)
				bytes = nengine.getRawPair(API_URL, headers, parameters,
					TIMEOUT);

			if (bytes != null) {
				String result = new String(bytes);
				Gson gson = new Gson();
				return gson.fromJson(result, QueryResponse.class);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// http://api.sensis.
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
