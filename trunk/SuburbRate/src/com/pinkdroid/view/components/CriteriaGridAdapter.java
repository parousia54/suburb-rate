package com.pinkdroid.view.components;

import java.util.Collection;

import com.pinkdroid.logic.Controller;
import com.pinkdroid.model.Criterion;
import com.pinkdroid.view.SuburbRateCriteriaScreen;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CriteriaGridAdapter extends BaseAdapter {

	SuburbRateCriteriaScreen suburbRateCriteriaScreen;

	public CriteriaGridAdapter(SuburbRateCriteriaScreen suburbRateCriteriaScreen) {
		this.suburbRateCriteriaScreen = suburbRateCriteriaScreen;
	}

	@Override
	public int getCount() {
		Controller controller = Controller.getInstance();
		Collection<Criterion> criteria = controller.getCriteria();
		return criteria.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Controller controller = Controller.getInstance();
		Collection<Criterion> criteria = controller.getCriteria();

		View view = suburbRateCriteriaScreen
				.createViewFromCriterion((Criterion) criteria.toArray()[position]);

		return view;
	}

}
