package com.blackspruce.mupdf;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

public class RenderHandler  extends Handler {

	RenderPageActivity parent = null;
	public RenderHandler( Activity a) {

		parent = (RenderPageActivity) a;
	}
	
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		parent.finishedThread(msg.what);
	}
}

