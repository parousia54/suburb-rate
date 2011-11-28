package com.pinkdroid.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pinkdroid.model.SensisCategory;
import com.pinkdroid.model.SensisContact;
import com.pinkdroid.model.SensisResult;
import com.pinkdroid.model.Suburb;
import com.pinkdroid.model.SuburbIndex;
import com.pinkdroid.model.SuburbStats;
import com.pinkdroid.networking.NetworkEngine;


public class RatingAPICaller {
	
	public final static String PLACE_API_URL = "http://www.suburbantrends.com.au/api/places/search/";
	public final static String SOCIO_ECO_URL = "http://www.suburbantrends.com.au/api/seifa/ssc/";
	public final static String SAFETY_URL    = "http://www.suburbantrends.com.au/api/icvs/ssc/";
	
	public static void updateSuburb(Suburb sb){
		
		NetworkEngine nengine = NetworkEngine.getInstance();
		byte[] bytes;
		try {
			String name = sb.getName().toLowerCase();
			name =  PLACE_API_URL+name+".json";
			System.out.println(name);
			int postCode = sb.getPostcode();
			bytes = nengine.getRaw(name,null, null, 30000);
			if (bytes != null) {
				String result = new String(bytes);
				JSONArray jsonArr = new JSONArray(result);
				if (jsonArr == null) return;
				
				JSONObject json = jsonArr.getJSONObject(0);
				if (jsonArr.length() > 1)
				{
					int i = 0;
					while(json != null)
					{
						if (json.getInt("postcode") == postCode)
							break;
						i++;
						json = jsonArr.getJSONObject(i);
					}
				}
				if (json == null || json.getInt("postcode") != postCode )
					return;
					/*
	{  ssc: "31565",
      name: "WEST END",
      post_code: "4101",
      state: "QLD",
      lga_id: "31000",
      centre: { lat: "-27.48214186", lon: "153.0059111" }
      extents: {
        ne: { lat: "-27.4742019", lon: "152.9962674" },
        sw: { lat: "-27.4900819", lon: "153.0155549"
        }
      },
      surr_subs: { 1: "31265", 2: "31353", ..., 10: "31519" },
      boundary: "http://www.suburbantrends.com.au/api/boundaries/ssc/31565.json"
    }*/
				SuburbStats stats = new SuburbStats();
				
				String ssc = json.getString("ssc");
				// The only thing we need from PLACE is SSC
				stats.setSsc(ssc);
				System.out.println("SSC:=="+ssc);
				
				updateSocioEcoId(stats,ssc);
				updateSafety(stats, ssc);
				
				// In the end, you store the stats to the object. 
				sb.setStatistics(stats);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void updateSocioEcoId(SuburbStats stats, String ssc){
		NetworkEngine nengine = NetworkEngine.getInstance();
		byte[] bytes;
		try {
			bytes = nengine.getRaw(SOCIO_ECO_URL+ssc+".json",null, null, 30000);
			if (bytes != null) {
				String result = new String(bytes);
				
				JSONObject json = new JSONObject(result);
				// Sample Object:
				// {"ssc":"31565","year":2006,
				// "adv_disadv":{"score":"1052.99","australia":{"rank":6707,"percentile":82},"state":{"rank":1609,"percentile":85}},
				// "economic":{"score":"947.04","australia":{"rank":1597,"percentile":20},"state":{"rank":326,"percentile":18}},
				// "education":{"score":"1122.42","australia":{"rank":7719,"percentile":94},"state":{"rank":1838,"percentile":97}},
				// "population":6207}
				
				
				stats.setDataYear(json.getInt("year"));
				
				JSONObject advObj = json.getJSONObject("adv_disadv");
				stats.setAdv_disAdv(getSuburbIndex(advObj));
				
				JSONObject ecoObj = json.getJSONObject("economic");
				stats.setEconomic(getSuburbIndex(ecoObj));
				
				JSONObject eduObj = json.getJSONObject("education");
				stats.setEducation(getSuburbIndex(eduObj));
				
				stats.setPopulation(json.getInt("population"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateSafety(SuburbStats stats, String ssc){
		NetworkEngine nengine = NetworkEngine.getInstance();
		byte[] bytes;
		try {
			bytes = nengine.getRaw(SAFETY_URL+ssc+".json",null, null, 30000);
			if (bytes != null) {
				String result = new String(bytes);
				
				JSONObject json = new JSONObject(result);
				// Sample Object:
				// {"ssc":"31565","year":2006,
				// "adv_disadv":{"score":"1052.99","australia":{"rank":6707,"percentile":82},"state":{"rank":1609,"percentile":85}},
				// "economic":{"score":"947.04","australia":{"rank":1597,"percentile":20},"state":{"rank":326,"percentile":18}},
				// "education":{"score":"1122.42","australia":{"rank":7719,"percentile":94},"state":{"rank":1838,"percentile":97}},
				// "population":6207}
				
				stats.setSafetyYear(json.getInt("year"));
				stats.setSafetyWalking(json.getInt("safety_walking"));
				stats.setSafetyTransport(json.getInt("safety_transport"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static SuburbIndex getSuburbIndex (JSONObject advObj)
	{
		try {
			SuburbIndex advVal = new SuburbIndex();
			if (advObj != null) 
			{	
				advVal.setScore(advObj.getDouble("score"));
			
				JSONObject aus = advObj.getJSONObject("australia");
				if (aus != null)
				{
					advVal.setAus_rank(aus.getInt("rank"));
					advVal.setAus_pct(aus.getInt("percentile"));
				}
				
				JSONObject state = advObj.getJSONObject("state");
				if (state != null)
				{
					advVal.setState_rank(state.getInt("rank"));
					advVal.setState_pct(state.getInt("percentile"));
				}
				return advVal;
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void testJSon() {
		Suburb sub = new Suburb();
		sub.setName("Collingwood");
		updateSuburb(sub);
		System.out.println(sub.getName()+", ssc:" + sub.getStatistics().getSsc());
		SuburbStats subst = sub.getStatistics();
		System.out.println("Economic Index:"+subst.getEconomic().getScore() + ", year:"+subst.getDataYear());
		System.out.println("Transport Safety:"+subst.getSafetyTransport() + ", year:"+subst.getSafetyYear());
	}

	public void testOne() {
		System.out.println("test");
		NetworkEngine nengine = NetworkEngine.getInstance();
		String url = "http://api.sensis.com.au/ob-20110511/test/search";
		Hashtable<String, String> headers = new Hashtable<String, String>();
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("key", "26zac3pbk9fdq6ane8cqk3s2");
		params.put("query", "massage");
		params.put("postcode", "3030");
		int timeout = 30000;
		try {
			byte[] bytes = nengine.getRaw(url, headers, params, timeout);
			// System.out.println("Response "+response);
			// System.out.println(response.getStatusLine().getStatusCode());
			// System.out.println(response.);
			if (bytes != null) {

				String result = new String(bytes);
				JSONObject json = new JSONObject(result);
				JSONArray nameArray = json.names();
				JSONArray valArray = json.toJSONArray(nameArray);
				System.out.println("length " + valArray.length());
				for (int i = 0; i < valArray.length(); i++) {
//					System.out
//							.println("<jsonname" + i + ">\n"
//									+ nameArray.getString(i) + "\n</jsonname"
//									+ i + ">\n" + "<jsonvalue" + i + ">\n"
//									+ valArray.getString(i) + "\n</jsonvalue"
//									+ i + ">");
				}
//				System.out.println("length " + bytes.length);
//				System.out.println("Response " + new String(bytes));
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
	}
}
