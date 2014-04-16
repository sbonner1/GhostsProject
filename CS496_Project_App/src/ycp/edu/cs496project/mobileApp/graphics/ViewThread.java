package ycp.edu.cs496project.mobileApp.graphics;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ViewThread extends Thread {
	// TODO 1: Add class fields
	//Add fields for a Panel object, 
	//a SurfaceHolder object, and 
	//two long variables for the start and current elapsed time.
	
	private boolean mRun = false;
	Panel mPanel;
	SurfaceHolder mHolder;
	Long mStartTime;
	Long mElapsed;
	boolean gameOver = false;
	
	public ViewThread(Panel panel) {
		// TODO: Initialize panel and holder objects
		//set the Panel field using the parameter and 
		//obtain the panel's SurfaceHolder object 
		//(using the getHolder() method of the panel)
		mPanel = panel;
		mHolder = panel.getHolder();
	}
	
	public void setRunning(boolean run) {
		mRun = run;
	}
	
	@Override
	public void run() {
		Canvas canvas = null;
		/* TODO 2: Add code to the run() method to lock the canvas object, 
		compute the current elapsed time, 
		update the panel object, 
		draw the panel object, 
		and release the lock on the canvas object. 
		Do not forget to update the start time variable with the new current time.
		*/
	
	      // Retrieve time when thread starts
	      mStartTime = System.currentTimeMillis();
	      
		// lock the canvas object, 
	      while (mRun)
	      {
	    	  canvas = mHolder.lockCanvas();
	      
	    	  if (canvas != null)
	    	  {
	    		  // Update state based on elapsed time //compute the current elapsed time, 
	              mElapsed = System.currentTimeMillis() - mStartTime;
	              //update the panel object,
	              mPanel.update(mElapsed);

	              // Render updated state//draw the panel object, and 
	              mPanel.doDraw(canvas,mElapsed);

	              // Release lock on canvas object
	              mHolder.unlockCanvasAndPost(canvas);
	           }

	           // Update start time		//Do not forget to update the start time variable with the new current time
	           mStartTime = System.currentTimeMillis();  
	      }
	     
	}
}
