package com.pinkdroid.common;

public class ApplicationConstant {
	public static final String TABLE_SUBURB_NAME = "suburb";
	public static final String TABLE_SQLITE_SEQUENCE = "sqlite_sequence";

	public static final String CREATE_TABLE_SUBURB_SQL = "create table "
			+ TABLE_SUBURB_NAME + "("
			+ "_id integer primary key autoincrement,"
			+ "   postcode  text not null," 
			+ "   areaname   text not null,"
			+ "   statename  text not null)";

}
