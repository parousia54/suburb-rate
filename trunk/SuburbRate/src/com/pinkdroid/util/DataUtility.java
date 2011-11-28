package com.pinkdroid.util;

import java.util.HashSet;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class DataUtility {
	
	public static List<String> removeDuplicatesfromArrayList(
            List<String> arrList) {
    HashSet<String> h = new HashSet<String>(arrList);
    arrList.clear();
    arrList.addAll(h);
    return arrList;
}
	
	public static void setSelectedItem(Spinner spinner, String item) {
		SpinnerAdapter sAdapter = spinner.getAdapter();
		if (sAdapter instanceof ArrayAdapter<?>) {
			ArrayAdapter<CharSequence> aAdapter = (ArrayAdapter<CharSequence>) sAdapter;
			for (int i = 0; i < aAdapter.getCount(); i++) {
				if (aAdapter.getItem(i).toString().equalsIgnoreCase(item)) {
					spinner.setSelection(i);
					break;
				}
			}
		}
	}

}
