package com.pinkdroid.networking;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageDownloader implements Worker<String, byte[]> {

	public ImageDownloader() {
		super();
	}

	@Override
	public byte[] heavyTask(String... params) throws Exception {

		NetworkEngine network = NetworkEngine.getInstance();
		HashMap<String, String> map = new HashMap<String, String>();

		byte[] data = null;

		data = network.downloadImage(params[0], null, map, -1);

		return data;
	}

}
