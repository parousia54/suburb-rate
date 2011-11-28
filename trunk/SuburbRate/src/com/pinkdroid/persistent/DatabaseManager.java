package com.pinkdroid.persistent;

import com.pinkdroid.common.ApplicationConstant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager {
    private MySQLiteHelper sqliteHelper;
    private SQLiteDatabase smapDBWrite, smapDBRead;
    
	public DatabaseManager(Context context)
    {
    	sqliteHelper  = new MySQLiteHelper(context);    
    	openWritableDB();
    	openReadableDB();
    } 
    public Cursor getValues(String table, String[] columns, String selection,
				String[] selectionArgs, String groupBy, String having, String orderBy) {
	
    	return smapDBRead.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
	}    
    public DatabaseManager openWritableDB() {
    	smapDBWrite = sqliteHelper.getWritableDatabase();
    	return this;
    }    
    public DatabaseManager openReadableDB() {
    	smapDBRead = sqliteHelper.getReadableDatabase();
    	return this;
    }    
    public void closeDB() {
    	sqliteHelper.close();
    }
    public void execSql(String sql){
    	smapDBWrite.execSQL(sql);
    }
    public long insertValues(ContentValues values, String tableName) 
    {    	
        return smapDBWrite.insert(tableName, null, values);
    }    
    public int deleteValues(String table, String whereClause, String[] whereArgs) {
    	return smapDBWrite.delete(table, whereClause, whereArgs);
    }    
    public long updateValues(ContentValues values, String tableName, String whereClause, String[] whereArgs) 
    {    
        return smapDBWrite.update(tableName, values, whereClause, whereArgs);
    }
    public Cursor executeRawSql(String sql, String[] selection){
    	return smapDBRead.rawQuery(sql, selection);
    }
    public Cursor getLastId(String tableName, String idCol) {
    	String[] columns = {"MAX(" + idCol + ")"};
    	return smapDBRead.query(tableName, columns, null, null, null, null, null);
    }
}
class MySQLiteHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NEXUS.db";
 
    MySQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("ACCESSING DATABASE............");
    }
    @Override
    public void onCreate(SQLiteDatabase db) 
    {     
    	System.out.println("CREATING DATABSE.......");
        db.execSQL(ApplicationConstant.CREATE_TABLE_SUBURB_SQL);        
        System.out.println("DATABASE HAS BEEN CREATED SUCCESSFULLY");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.setVersion(newVersion);
    } 

    
}