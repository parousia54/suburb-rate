package com.pinkdroid.view;


import com.pinkdroid.logic.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class SuburbRateScreen extends Activity implements ScreenUpdater{
	private ProgressDialog progress;
	private String dialogMessage="", dialogTitle="";
	private AlertDialog.Builder builder;
	private AlertDialog alert;	
	@Override
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		builder = new AlertDialog.Builder(this);
		progress = new ProgressDialog(this);
		progress.setCancelable(false);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		trackActivity();
	}
	
	private void trackActivity() {
		// TODO Auto-generated method stub
		Controller controller = Controller.getInstance();
		if(controller.getTracker()!= null){
			controller.getTracker().trackPageView("/"+this.getClass().getName());
			System.out.println("Page view for "+this.getClass().getName());
		}
	}

	@Override
	public void update(Object data) {
		if(progress!=null)
			if(progress.isShowing())
				progress.dismiss();
	}
	protected void displayProgress(String title, String message){
		dialogMessage=message;
		dialogTitle=title;
		showDialog(DIALOG_PROGRESS);
	}
	@Override
    protected Dialog onCreateDialog(int id)    {
        final Dialog dialog = new Dialog(this);
        switch(id){
        case DIALOG_MESSAGE:{
			builder.setMessage(dialogMessage)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle(dialogTitle)
					.setCancelable(false)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface d, int id) {
									d.cancel();
								}
							});
			alert = builder.create();
			return alert;
        }
			case DIALOG_PROGRESS:{
				progress = new ProgressDialog(this);
				progress.setCancelable(false);
				progress.setMessage(dialogMessage);
				return progress;
			}
        }
        return dialog;
    }
	@Override
	protected void onPrepareDialog(int id, Dialog dialog){
		switch(id){		 	
		 	case DIALOG_PROGRESS:{
				((ProgressDialog)dialog).setMessage(dialogMessage);
				break;
			}
		 	case DIALOG_MESSAGE:{
		 		AlertDialog alert = (AlertDialog)dialog;
		 		alert.setMessage(dialogMessage);
		 		alert.setTitle(dialogTitle);
		 		break;
		 	}
		}
	}
	@Override
	public void displayMessage(String title, String message) {
		this.dialogMessage=message;
		this.dialogTitle=title;
		this.showDialog(DIALOG_MESSAGE);
	}
}
