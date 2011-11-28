package com.pinkdroid.persistent;

import com.pinkdroid.common.ApplicationConstant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

public class DatabaseAccessHelper {
	
	public static void updateSuburbData(DatabaseManager dbManager, String suburbData){
		dbManager.execSql(suburbData);		
	}
	
	public static void updateSuburbData(DatabaseManager dbManager,String postcode, String suburb, String state){
		ContentValues values = new ContentValues();	
		values.put("postcode", postcode);
		values.put("areaname", suburb);
		values.put("statename", state);
		dbManager.insertValues(values, ApplicationConstant.TABLE_SUBURB_NAME);
	}
	public static boolean cleanDatabase(DatabaseManager dbMananger){
		try{
			dbMananger.deleteValues(ApplicationConstant.TABLE_SUBURB_NAME, null, null);
			dbMananger.deleteValues(ApplicationConstant.TABLE_SQLITE_SEQUENCE, null, null);
			System.out.println("CURRENT DATABASE HAS BEEN CLEANED................");

			return true;
		}
		catch(SQLException sqlE){
			sqlE.printStackTrace();
			return false;
		}
	}
	
	public static Cursor getMatchingSuburbSearch(DatabaseManager dbManager, String constraint){
		Cursor cursor = null;
		String queryString =  "SELECT _id, postcode,areaname, statename FROM "+ApplicationConstant.TABLE_SUBURB_NAME;
		if (constraint != null) {
            
            constraint = constraint.trim() + "%";
            queryString += " WHERE (areaname LIKE ?) OR (postcode LIKE ?)";
        }
        String params[] = { constraint, constraint };

        if (constraint == null) {	           
            params = null;
        }
        try {
            cursor = dbManager.executeRawSql(queryString, params);
            return cursor;
           
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
		
	}
}
