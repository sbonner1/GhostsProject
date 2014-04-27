package ycp.edu.cs496project.mobileApp.graphics;

import java.util.ArrayList;
import java.util.Random;

import ycp.edu.cs496project.mobileApp.GhostEnums;
import ycp.edu.cs496project.mobileApp.R;
import ycp.edu.cs496project.mobileApp.R.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

public class Panel extends SurfaceView implements Callback 
{
	public static float mWidth;
	public static float mHeight;
	private float previousX;
	private float previousY;
	//final private float holeRadius = 20.0f;
	//final private float ballStopped = 0.1f;
	private int tooManyGhosts;
	private int score;
	private double countdownTime;
	
	/* TODO 1: Add fields for: Sprite (for the ball object), 
	 * a field for a thread object, 
	 * a field for a Paint object
	 * boolean flag for the current game state.
	 */
		private ViewThread mThread;
		private Paint mPaint;
		private boolean gameOver;
		//////////////
		private ArrayList<Sprite> mSpriteList = new ArrayList<Sprite>();
		int startX, startY, velocityX, velocityY;
		
	private Random generater;
		
	public Panel(Context context) 
	{
		super(context);
		
		generater = new Random(System.currentTimeMillis());

		// TODO 2: Initialize class fields
			score = 0;
			tooManyGhosts = 10;
			countdownTime = 30.0;
			
			//Register the class as the callback (using getHolder.addCallback(this);)
			getHolder().addCallback(this);
			
			//Create new thread objects & paint objects
			mThread = new ViewThread(this);
			mPaint = new Paint();

			//Set the color for the paint object to white (using the method setColor(Color.WHITE) of the paint object)
			mPaint.setColor(Color.WHITE);
			
			//Set the game flag to false.
			gameOver = false;
			
			

			//ADD CODE IN THE CONSTRUCTOR 2 PART E			
			//Add one sprite to the list (for the first ghost) starting at (0,0) (or wherever you wish) and with a random velocity between -3 and 3. Don't forget to set the field for the number of sprites.
			createGhost(GhostEnums.redGhost,0,this.getWidth(),3);
	}
	
	/**
	 * Creates a random number between the integer range of Min and Max
	 * @param Min
	 * @param Max
	 * @return
	 */
	public int getRandomNum(int Min, int Max)
	{
		//return (int) Min + (int)(Math.random() * ((Max - Min) + 1));
		return generater.nextInt((Max-Min)+1) + Min;
	}
	
	
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		mWidth = width;
		mHeight = height;
	}

	//TODO 3: Add code to surfaceCreated() to create and start the rendering thread.
	public void surfaceCreated(SurfaceHolder holder) {
		// Create and start new thread
		   if (!mThread.isAlive()) {
		      mThread = new ViewThread(this);
		      mThread.setRunning(true);
		      mThread.start();
		   }
		}

	//TODO 4: Add code to surfaceDestroyed() to stop the rendering thread.
	public void surfaceDestroyed(SurfaceHolder holder) {
		   // Stop thread
		   if (mThread.isAlive()) {
		      mThread.setRunning(false);
		   }
		}

	
	/* TODO 5: Add code to update() to call the update method on the ghost sprite. 
	 * Be sure to do this in a thread safe manner (i.e. within a synchronized block). 
	 * Also call the checkGameEnd() method (assigning the return value to the game flag).
  */
	public void update(long elapsedTime) 
	{
		if (gameOver != true)
		{
		   synchronized (mSpriteList) 
		   {
		      for (Sprite sprite : mSpriteList) 
		      {
		    	 //show movement
		         sprite.update(elapsedTime);
		         if (checkGameEnd() == true)
		         {
		        	 gameOver = true;
		         }
		      }
		   }
		}
	}
	
	/*
	 * Creates a random number and based on the number spawns the appropriate entity
	 */
	public void tryToSpawn()
	{
		 int nextGhost = getRandomNum(0, 1000);
	     if (nextGhost > 950)
	     {
	   	  createGhost(GhostEnums.redGhost,0,0,3);
	     }
	     
	     if (nextGhost > 925 && nextGhost < 950)
	     {
	   	  createGhost(GhostEnums.yellowGhost,0,0,3);
	     }
	     
	     if (nextGhost > 900 && nextGhost < 925)
	     {
	   	  createGhost(GhostEnums.greenGhost,0,0,4);
	     }
	}
	/**
	 * Creates a ghost based on the ghostEnum type and randomly sets the initial location and velocity based on the ranges passed in as parameters 
	 * @param ghostEnum
	 * @param MinLoc
	 * @param MinVel
	 * @param MaxVel
	 */
	
	public void createGhost(GhostEnums ghostEnum, int MinLoc, int MinVel, int MaxVel)
	{	
		//int MinLoc = minLoc;
		int MaxLoc = this.getWidth();
		//int MinVel = minVel;
		//int MaxVel = maxVel;
		startX = getRandomNum(MinLoc, MaxLoc);
		startY = getRandomNum(MinLoc, MaxLoc);
		velocityX = getRandomNum(MinVel, MaxVel);
		velocityY = getRandomNum(MinVel, MaxVel);
		
		Sprite ghost = null;
		
		if (ghostEnum == GhostEnums.redGhost)
		{
			ghost = new Sprite(getResources(), R.drawable.red_ghost, startX, startY, velocityX, velocityY);
		}
		
		if (ghostEnum == GhostEnums.yellowGhost)
		{
			ghost = new Sprite(getResources(), R.drawable.yellow_ghost, startX, startY, velocityX, velocityY);
		}
		
		if (ghostEnum == GhostEnums.greenGhost)
		{
			ghost = new Sprite(getResources(), R.drawable.green_ghost, startX, startY, velocityX, velocityY);
		}
		
		mSpriteList.add(ghost);
	}
	// TODO 6: Draw Canvas and Ghosts
	/*Add code to doDraw() to draw the background and Ghosts; be sure to do this in a thread safe manner.
	 *  Also add a check of the game flag displaying a message when the game is over.*/
	public void doDraw(Canvas canvas, long elapsed) 
	{
		canvas.drawColor(Color.BLACK);
		//Draw ball (thread safe)
		synchronized (mSpriteList) 
		{
			//hole.doDraw(canvas);
			for (Sprite sprite : mSpriteList) 
		    {
				//draw the ghosts(s) because they are sprites in the sprite list
		        sprite.doDraw(canvas);
		    }
			
			if (checkGameEnd() == true)
			{
				canvas.drawText("Game Over", 10, 30, mPaint);
			}
			canvas.drawText(getCountdownString(getCountdownTime()), 10, 10, mPaint);
			canvas.drawText(getScoreString(getScore()), 100, 10, mPaint);
		}
	}
	
	/* TODO 7: Add code to the onTouchEvent() event handler to compute a change in velocity based on a user swipe, i.e. the further they swipe the more there will be a velocity change in that direction.*/
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
	//public boolean onClickEvent (MotionEvent event){
	    // Get touch event location
	    float currentX = event.getX();
	    float currentY = event.getY();
	    float deltaX, deltaY;

	    // Compute scale factor based on window size
	    float scalingFactor = 5.0f / ((mWidth > mHeight) ? mWidth : mHeight);

	    if (checkGameEnd() != true)
	    {
		    // Use move touch event
		    switch (event.getAction()) 
		    {
		     	//case MotionEvent.ACTION_MOVE:
		    	case MotionEvent.ACTION_DOWN: 
		            // Modify velocities according to movement
		            //deltaX = currentX - previousX;
		            //deltaY = currentY - previousY;
		        		//Updated
		        	//nextGhost = Min + (int)(Math.random() * ((Max - Min) + 1));
		        		deltaX = getRandomNum(-3, 3);
		        		deltaY = getRandomNum(-3, 3);
		        		
	            //when there is a move action ALL of the sprites will move
		            // Update ball velocities
		        	synchronized(mSpriteList)
		        	{
		        		ArrayList<Sprite> toRemove = new ArrayList<Sprite>();
		        		for (Sprite sprite : mSpriteList) 
			    		{
		        			//draw the ball(s) because they are sprites in the sprite list
			    			if (Math.abs(distance(currentX, currentY, sprite.getCenterX(), sprite.getCenterY())) < sprite.getRadius()*2)
			    			{
			    				toRemove.add(sprite);
			    				score++;
			    				//sprite.changeVelocity(deltaX * scalingFactor, deltaY * scalingFactor);
			    			}
			    		}
		        		
		        		for (Sprite sprite : toRemove) {
		        			mSpriteList.remove(sprite);
		        		}
		        	}
	/*	        	
		     	case MotionEvent.ACTION_MOVE:
		           		deltaX = getRandomNum(-3, 3);
		        		deltaY = getRandomNum(-3, 3);
		        		
	            //when there is a move action ALL of the sprites will move
		            // Update ball velocities
		        	synchronized(mSpriteList)
		        	{
		        		ArrayList<Sprite> toRemove = new ArrayList<Sprite>();
		        		for (Sprite sprite : mSpriteList) 
			    		{
		
	//?						if (sprite.getGhostType() < 3)
		        			{
			        			//draw the ball(s) because they are sprites in the sprite list
				    			if (Math.abs(distance(currentX, currentY, sprite.getCenterX(), sprite.getCenterY())) < sprite.getRadius()* 3)
				    			{
				    				toRemove.add(sprite);
				    				score++;
				    				//sprite.changeVelocity(deltaX * scalingFactor, deltaY * scalingFactor);
				    			}
				    			//sprite.changeVelocity(deltaX * scalingFactor, deltaY * scalingFactor);
		        			}
			    		}
		        		
		        		for (Sprite sprite : toRemove) {
		        			mSpriteList.remove(sprite);
		        		}
		        	}
		    }
	*/
		    }
	    }


	    // Save current x, y
	    previousX = currentX;
	    previousY = currentY;

	    return true;
	}
	
	private float distance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	public void updateCountdownTime(double value)
	{
		countdownTime -= value;
	}
	
	public double getCountdownTime()
	{
		return countdownTime;
	}
	
	private String getCountdownString(double countdownTime)
	{
		return String.valueOf(countdownTime);
	}
	
	private String getScoreString(int score)
	{
		return String.valueOf(score);
	}
	
	public boolean checkGameEnd() 
	{
		// TODO 8: Check if ball is within hole region
		//if (mSpriteList.size() > tooManyGhosts)
		if (getCountdownTime() <= 0.0)
		{
			return true;
		}
		
		return false;
	}

	public int getScore()
	{
		return score;
	}
	
	
}
