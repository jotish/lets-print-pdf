package com.blackspruce.mupdf;


import com.artifex.mupdfdemo.MuPDFCore;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.TextView;



public class RenderThread extends Thread {
	
	Activity parent=null;
	Handler messageHandler = null;
	private String fileName;
	private static int startPageNumber;
	private static int endPageNumber;
	private MuPDFCore core;
	private int width;
	private int height;
	public String msg = "";
	private String filePath;
	static String progressMsg="Processing: Page ";
	static int currPage=1;
	 
	public RenderThread( Activity p, Handler msgHand, String file, String path, MuPDFCore c, int start, int end, int wid, int hei) {
		super("renderPDF");
		parent = p;
		messageHandler = msgHand;
		fileName = file;
		filePath = path;
		core = c;
		startPageNumber = start;
		endPageNumber = end;
		width = wid;
		height = hei;
	}
	
	private Boolean killIt =  Boolean.FALSE;
	
	public  void killIt() {
		synchronized(killIt) {
			killIt = Boolean.TRUE;
		}
	}
	
	 public synchronized void startThread() {
		 synchronized(killIt) {
			 killIt = Boolean.FALSE;
		 }
         super.start();
	 }
		    
	@Override
	public void run() {
		try {
			super.run();
			currPage= 1;
			final TextView field = (TextView) parent.findViewById(R.id.progress);
			Runnable progressUpdate = new Runnable(){
			    public void run() {
			    	field.setText(progressMsg+currPage+" of "+(endPageNumber - startPageNumber + 1) );
			    	field.invalidate();
			    }};
			    
			Bitmap b = null;
			String seqFileName = fileName.substring(0, fileName.length() - 4 );
			String fext = ".png"; 
			boolean success = true;
			
			
			for ( int i =startPageNumber; i <= endPageNumber; i++ ) {
				parent.runOnUiThread(progressUpdate);
				try {
					b = core.drawPage((i-1), width, height, 0, 0, width, height);
				} catch (OutOfMemoryError e1 ){
					System.gc();
					msg = "Ran out of memory rendering PDF." ;
					messageHandler.sendEmptyMessage(99);
					return;
				}
				synchronized ( killIt ) {
					if ( killIt.booleanValue() || Thread.interrupted()  ) {
						if (b != null ) b.recycle();
						messageHandler.sendEmptyMessage(97);
						return;
					}
				}
				success = RenderPageActivity.bitmap2PNGFile(b, seqFileName+i+fext, filePath);
				if (b != null ) b.recycle();
				b = null;
				currPage++;
				if ( ! success ) {
					msg=  "failed saving PNG file "+seqFileName+i+fext ;
					messageHandler.sendEmptyMessage(98);
					 return;
				}
			}
			
		} catch (Exception e2) {
			e2.printStackTrace();
			messageHandler.sendEmptyMessage(96);
			return;
	}
		messageHandler.sendEmptyMessage(0);
	}
	
	
	
}
