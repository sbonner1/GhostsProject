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
	private int numGhosts;	
	private int tooManyGhosts;
	private int score;
	private double countdownTime;
	private int redChain;
	private int yellowChain;
	private int greenChain;
	
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
			numGhosts = 0;
			tooManyGhosts = 30;
			countdownTime = 30.0;
			redChain = 0;
			
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
			numGhosts++;
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
		if (getGameOver() == false)
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
	
	/**
	 * Creates a random number and based on the number spawns the appropriate entity
	 * Called in ViewThread
	 */
	public void tryToSpawn()
	{
		 int nextGhost = getRandomNum(0, 10);
		 if (getGameOver() == false)
		 {
			 if (nextGhost == 0)
			 {
				 createGhost(GhostEnums.deathGhost,0,0,3);
			 }
			 
		     if (nextGhost >= 1 && nextGhost <= 5 )
		     {
		   	  	createGhost(GhostEnums.redGhost,0,0,3);	
		     }
		     
		     if (nextGhost >= 6 && nextGhost <= 8)
		     {
		   	  	createGhost(GhostEnums.yellowGhost,0,0,3);
		     }
		     
		     if (nextGhost == 9)
		     {
		   	  	createGhost(GhostEnums.greenGhost,0,0,4);
		     }
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
		
		if (ghostEnum == GhostEnums.deathGhost)
		{
			ghost = new Sprite(getResources(), R.drawable.death_ghost2, startX, startY, velocityX, velocityY);
			numGhosts++;
		}
		if (ghostEnum == GhostEnums.redGhost)
		{
			ghost = new Sprite(getResources(), R.drawable.red_ghost, startX, startY, velocityX, velocityY);
			numGhosts++;
		}
		
		if (ghostEnum == GhostEnums.yellowGhost)
		{
			ghost = new Sprite(getResources(), R.drawable.yellow_ghost, startX, startY, velocityX, velocityY);
			numGhosts++;
		}
		
		if (ghostEnum == GhostEnums.greenGhost)
		{
			ghost = new Sprite(getResources(), R.drawable.green_ghost, startX, startY, velocityX, velocityY);
			numGhosts++;
		}
		
		mSpriteList.add(ghost);
	}
	// TODO 6: Draw Canvas and Ghosts
	/*Add code to doDraw() to draw the background and Ghosts; be sure to do this in a thread safe manner.
	 *  Also add a check of the game flag displaying a message when the game is over.*/
	/**
	 * Draws the canvas, the ghosts, and the relevant scores
	 * @param canvas
	 * @param elapsed
	 */
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
			
			if (getGameOver() == true)
			{
				canvas.drawText("Game Over", 10, 30, mPaint);
			}
			canvas.drawText(getCountdownString(getCountdownTime()), 10, 10, mPaint);
			canvas.drawText("Score:" + getScoreString(getScore()), 100, 10, mPaint);
			canvas.drawText("Red Chain:" + getScoreString(getRedChain()), 100, 40, mPaint);
			canvas.drawText("Yellow Chain:" + getScoreString(getYellowChain()), 200, 40, mPaint);
			canvas.drawText("Green Chain:" + getScoreString(getGreenChain()), 300, 40, mPaint);
		}
	}
	
	/* TODO 7: Add code to the onTouchEvent() event handler to compute a change in velocity based on a user swipe, i.e. the further they swipe the more there will be a velocity change in that direction.*/
	/**
	 * Handles touchEvents and updates the sprite list(s) by removing ghosts and updating the chain values
	 */
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
		    switch (event.getAction()) 
		    {
		    	//Case tap/click
		    	case MotionEvent.ACTION_DOWN: 
	        		deltaX = getRandomNum(-3, 3);
	        		deltaY = getRandomNum(-3, 3);
		        		
	        		//when there is a move action ALL of the sprites will move
		        	synchronized(mSpriteList)
		        	{
		        		ArrayList<Sprite> toRemove = new ArrayList<Sprite>();
		        		for (Sprite sprite : mSpriteList) 
			    		{
		        			//draw the ball(s) because they are sprites in the sprite list
			    			if (Math.abs(distance(currentX, currentY, sprite.getCenterX(), sprite.getCenterY())) < sprite.getRadius()*2 && getGameOver() == false)
			    			{
			    				toRemove.add(sprite);
			    				score++;
			    				//sprite.changeVelocity(deltaX * scalingFactor, deltaY * scalingFactor);
			    			}
			    		}
		        				        		
		        		//Keep track of chain values
		        		for (Sprite sprite : toRemove) 
		        		{
		        			if (sprite.getGhostType() == R.drawable.death_ghost2)
		        			{
		        				setGameEnd();
		        			}
		        			if (sprite.getGhostType() == R.drawable.red_ghost)
		        			{
		        				redChain++;
		        				yellowChain = 0;
		        				greenChain = 0;
		        			}
		        			
		        			if (sprite.getGhostType() == R.drawable.yellow_ghost)
		        			{
		        				redChain = 0;
		        				yellowChain++;
		        				greenChain = 0;
		        			}
		        			
		        			if (sprite.getGhostType() == R.drawable.green_ghost)
		        			{
		        				redChain = 0;
		        				yellowChain = 0;
		        				greenChain++;
		        			}
		        			
		        			mSpriteList.remove(sprite);
		        			numGhosts--;
		        		}
		        	}
	        	
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
		
							if (sprite.getGhostType() == R.drawable.death_ghost2)
		        			{
			        			//draw the ball(s) because they are sprites in the sprite list
				    			if (Math.abs(distance(currentX, currentY, sprite.getCenterX(), sprite.getCenterY())) < sprite.getRadius()* 3)
				    			{
				    				toRemove.add(sprite);
				    				score++;
				    			}
				    			//sprite.changeVelocity(deltaX * scalingFactor, deltaY * scalingFactor);
		        			}
			    		}
		        		
		        		for (Sprite sprite : toRemove) {
		        			mSpriteList.remove(sprite);
		        			numGhosts--;
		        		}
		        	}
		    
	
		    }
	    }


	    // Save current x, y
	    previousX = currentX;
	    previousY = currentY;

	    return true;
	}
	
	/**Distance formula, requires (x,y) for two different points
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private float distance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	/**
	 * Decrements the timer by the double value parameter, called from ViewThread
	 * @param value
	 */
	public void updateCountdownTime(double value)
	{
		countdownTime -= value;
	}

	/**
	 * Getter for countdownTime, called from Panel in CheckGameOver and used to display value in doDraw
	 * @return
	 */
	public double getCountdownTime()
	{
		return countdownTime;
	}
	
	/**
	 * Converts doubles to strings for displaying in doDraw
	 * @param countdownTime
	 * @return
	 */
	private String getCountdownString(double countdownTime)
	{
		return String.valueOf(countdownTime);
	}
	
	/**
	 * Converts ints to strings for displaying in doDraw
	 * @param score
	 * @return
	 */
	private String getScoreString(int score)
	{
		return String.valueOf(score);
	}
	
	/**
	 * Used to update gameOver boolean if the timer reaches 0.0
	 * @return
	 */
	public boolean checkGameEnd() 
	{
		if (getCountdownTime() <= 0.0)
		{
			return true;
		}
		
		return false;
	}

	/**
	 * Used to set gameOver to true in the event a deathGhost is banished
	 */
	public void setGameEnd()
	{
		gameOver = true;
	}
	
	/**
	 * Getter for gameOver value, used in multiple locations
	 * @return
	 */
	public boolean getGameOver()
	{
		return gameOver;
	}
	
	/**
	 * Getter for score, returns an int value
	 * @return
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * Getter for redChain
	 * @return
	 */
	public int getRedChain()
	{
		return redChain;
	}
	
	/**
	 * Getter for yellowChain
	 * @return
	 */
	public int getYellowChain()
	{
		return yellowChain;
	}
	
	/** 
	 * Getter for greenChain
	 * @return
	 */
	public int getGreenChain()
	{
		return greenChain;
	}
	
	/**
	 * Getter for numGhosts, used to determine if there are too many on screen to spawn more in ViewThread
	 * @return
	 */
	public int getNumGhosts()
	{
		return numGhosts;
	}
	
	/**
	 * Getter for tooManyGhosts, used to determine if there are too many on screen to spawn more in ViewThread
	 * @return
	 */
	public int getTooManyGhosts()
	{
		return tooManyGhosts;
	}
	
	
	
	
	
}
