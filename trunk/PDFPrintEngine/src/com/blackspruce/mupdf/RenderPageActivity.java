package com.blackspruce.mupdf;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import com.artifex.mupdfdemo.MuPDFCore;
import com.artifex.mupdfdemo.OutlineActivityData;


public class RenderPageActivity extends Activity {
	private MuPDFCore    core;
	private String mFileName;
	static public String SDCARD = Environment.getExternalStorageDirectory().getPath();
	
	int startPageNumber = -1, endPageNumber = -1 ; 
	
	RenderThread myThread = null;
	
	 @Override
	public void onBackPressed() {
		 TextView field = (TextView) findViewById(R.id.progress);
		 if ( myThread !=  null ) {
			 myThread.killIt();
			 if ( field != null ) {
				 field.setText("Cancellation pending..." );
				 field.invalidate();
			 }
		 }
	 }
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
		Intent intent = getIntent();
	
		Uri uri = null;
	
		String filePath = null, fileName = null;
		int width = -1, height = -1;
	
		if (intent.getAction().equalsIgnoreCase(Intent.ACTION_SEND) ) {
        	Iterator<String> iter = intent.getExtras().keySet().iterator();
        
	        String key = "";
	        while ( iter.hasNext() ) {
	        	key = (String) iter.next();
	        	// Log.d( TAG, "s="+key); 
	        	if (key != null && key.equalsIgnoreCase(Intent.EXTRA_STREAM) ) {
	        		uri = (Uri) intent.getExtras().get(key);
	        	} else if ( key != null && key.equalsIgnoreCase("startPageNumber") ) {
	        		startPageNumber = intent.getExtras().getInt("startPageNumber");
	        	} else if ( key != null && key.equalsIgnoreCase("endPageNumber") ) {
		        	endPageNumber = intent.getExtras().getInt("endPageNumber");
	        	} else if ( key != null && key.equalsIgnoreCase("filePath") ) {
	        		filePath = intent.getExtras().getString("filePath");
	        	} else if ( key != null && key.equalsIgnoreCase("fileName") ) {
	        		fileName = intent.getExtras().getString("fileName");
	        	} else if ( key != null && key.equalsIgnoreCase("width") ) {
	        		width = intent.getExtras().getInt("width");
	        	}else if ( key != null && key.equalsIgnoreCase("height") ) {
	        		height = intent.getExtras().getInt("height");
	        	}
	        }
        } else {
        	// should never happen
        	errorFinish( "Cannot print that way; use SEND " );
        	 return;
        }
		
		if ( uri == null || filePath == null || fileName == null || 
				width == -1 || height == -1 ) {
			// failure
			errorFinish( "missing Extras from intent;  src uri, fileName, filPath, width, height.");
			 return;
		}
		if ( ! ".PNG".equalsIgnoreCase(fileName.substring(fileName.length() - 4 ))) {
			// FIXME could do JPG as well
			errorFinish( "Can only generate PNG files; fileName should end with \".png\" ");
			 return;
		}
		
	System.out.println( "the URI is "+uri.toString());
	
		if (uri.toString().startsWith("content://")) {
			// FIXME convert to local file uri and or load cloud files as local 
			errorFinish( "Need a file URI; given URI was "+uri.toString() );
			 return;
		}
		core = openFile(Uri.decode(uri.getEncodedPath()));
		if (core != null && core.needsPassword()) {
			// FIXME requestPassword(savedInstanceState);
			errorFinish( "Cannot handle password protection on PDF" );
			return;
		}
			
		int numPages = core.countPages();
		if (numPages < 1  ) {
			errorFinish( "Empty or corrupt PDF document" );
			return;
		}
			
		if ( endPageNumber > numPages || endPageNumber < 1 ) endPageNumber = numPages;
		if ( startPageNumber < 1 || startPageNumber > endPageNumber ) startPageNumber = 1;
		
		RenderHandler messageHandler = new RenderHandler(this) ;
		myThread = new RenderThread(this, messageHandler, fileName, filePath, core,
										startPageNumber, endPageNumber, width, height);
		myThread.startThread();
		
		} catch (Exception e) {
			// fail
		    errorFinish( "Exception in RenderPageActivity: "+e.toString() );
			
		}
	}
	
	public void errorFinish(String msg) {
	    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		 setResult(RESULT_CANCELED);
		 finish();
	}
	
	 // Use PNGs as JPEGS are subject to terrible dithering in Android
   static boolean bitmap2PNGFile( Bitmap b, String fileName, String path) {
        FileOutputStream fos = null;
        if ( b == null ) return false;
        try {
        			
        	File file = new File(path);
        	file.mkdirs(); // If the directory already exists, this method does not
        	file = new File(path+fileName);
        	if ( file.exists() && file.canWrite() ) file.delete();
        	
        	fos = new FileOutputStream( file );
        	if ( fos != null )
        	{
                b.compress(Bitmap.CompressFormat.PNG, 80, fos);
                fos.close();
        	}
        } catch( Exception e )
        {
        		e.printStackTrace();
        		return false;
        }
        return true;
    }
    
	
	private MuPDFCore openFile(String path)
	{
		int lastSlashPos = path.lastIndexOf('/');
		mFileName = new String(lastSlashPos == -1
					? path
					: path.substring(lastSlashPos+1));
		System.out.println("Trying to open "+path);
		try
		{
			core = new MuPDFCore(path);
			// New file: drop the old outline data
			OutlineActivityData.set(null);
		}
		catch (Exception e)
		{
			System.out.println(e);
			return null;
		}
		return core;
	}

	public void finishedThread(int  handlerWhat) {
		
		if ( handlerWhat == 0 ) {
			// 100% Success
			setResult(Activity.RESULT_OK, 
				    new Intent().putExtra("startPageNumber", startPageNumber).
				    				putExtra("endPageNumber", endPageNumber));
			finish();
		} else if ( handlerWhat == 99 ) errorFinish("Ran out of memory rendering PDF.");
		else if (handlerWhat == 98 ) errorFinish("Failed to save PNG page file on SDCARD.");
		else if (handlerWhat == 97 ) errorFinish("Cancelled Rendering of PDF.");
		else  errorFinish("Unknown failure rendering PDF.");
		
	}
}
