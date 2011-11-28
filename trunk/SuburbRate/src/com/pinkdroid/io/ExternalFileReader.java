package com.pinkdroid.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pinkdroid.logic.Controller;
import com.pinkdroid.model.Criterion;

public class ExternalFileReader{

	private boolean finishPart1 = false;

	private StringBuffer result = null;
	private InputStream inputPart1 = null;

	private String scriptName = null;
	private final int bufferSize = 1024 * 5;

	private Controller controller;
	
	private Collection<Criterion> criteria;

	public ExternalFileReader(Controller controller) {
		this.controller = controller;
	}

	public void setInputStream(InputStream input) {
		inputPart1 = new BufferedInputStream(input);
	}

	private String readTxtPart1() {
		try {
			ByteArrayOutputStream contents = new ByteArrayOutputStream();
			int len;
			byte[] data = new byte[bufferSize];
			do {
				len = inputPart1.read(data);
				if (len > 0)
					contents.write(data, 0, len);
			} while (len == data.length);
			inputPart1.close();
			return contents.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public Collection<Criterion> getCriteria() {
		String criteriaString = readTxtPart1();
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Criterion>>(){}.getType();
		Collection<Criterion> ints2 = gson.fromJson(criteriaString, collectionType);
		return ints2;
	}

	

}
