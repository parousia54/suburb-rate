package com.pinkdroid.model;

import android.graphics.Bitmap;

public class ImageItem {
	private int position;
	private Bitmap bitmap;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public int getPosition() {
		return position;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
