package ycp.edu.cs496project.mobileApp.graphics;

import ycp.edu.cs496project.mobileApp.MarbleMadness;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.widget.Toast;

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
	Long spawnElapsing = (long) 0;
	Long countdownElapsing = (long) 0;
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
	              
	              //update the panel object, shows movement of ghosts
	              mPanel.update(mElapsed);
	              
	              //Updates spawnElapsing until it reaches 0.5 seconds and then calls tryToSpawn
	                spawnElapsing += mElapsed;
	                countdownElapsing += mElapsed;
	                
	                if(mPanel.getGameOver() != true) 
	                {
		                if (spawnElapsing >= 500 && mPanel.getNumGhosts() < mPanel.getTooManyGhosts())
		                { 
		                	mPanel.tryToSpawn();
		                	spawnElapsing = (long) 0;
		                }
		                
		                if (countdownElapsing >= 1000)
		                {
		                	mPanel.updateCountdownTime(1.0);
		                	countdownElapsing = (long) 0;
		                }
	                }
	              // Render updated state//draw the panel object, and 
	              mPanel.doDraw(canvas,mElapsed);

	              // Release lock on canvas object
	              mHolder.unlockCanvasAndPost(canvas);
	           }

	           // Update start time		//Do not forget to update the start time variable with the new current time
	           mStartTime = System.currentTimeMillis();  
	    	  
	           
	          /* 
	           // Stop gameloop if game is over
	           if (mPanel.checkGameEnd() == true)
	           {
	        	   mRun = false;
	           }
	           */
	      }
	}
}
