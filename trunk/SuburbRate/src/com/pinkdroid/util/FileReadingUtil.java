package com.pinkdroid.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.pinkdroid.persistent.DatabaseAccessHelper;
import com.pinkdroid.persistent.DatabaseManager;

import android.content.Context;
import android.content.res.AssetManager;

public class FileReadingUtil {
	private static InputStream readSuburbDataFromFile(Context context, String filePath){
		InputStream is=null;
		AssetManager assetManager = context.getAssets();
		try {
            is = assetManager.open(filePath);
        } catch (IOException e) {
        	e.printStackTrace();
            return null;
        }		
		return is;
	}
	
	public static void insertSuburbsToDb(Context context, String filePath, DatabaseManager dbManager){
		InputStream is = readSuburbDataFromFile(context, filePath);
		if(is!=null){
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			try{
				String suburb=null;
				while((suburb= r.readLine())!=null){
					insertSuburdToDb(suburb, dbManager);
				}
			}
			catch(Exception e){
				e.printStackTrace();				
			}
		}
	}
	private static boolean insertSuburdToDb(String suburb, DatabaseManager dbManager){
//		System.out.println("SUBURB IS: "+suburb);
		String[] suburbProperties = suburb.split(",");
		if(suburbProperties.length==3){
			DatabaseAccessHelper.updateSuburbData(dbManager, suburbProperties[0], suburbProperties[1], suburbProperties[2]);
			return true;
		}
		else{
			return false;
		}
	}
}
