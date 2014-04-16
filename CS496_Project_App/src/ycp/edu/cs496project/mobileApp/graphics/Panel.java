package ycp.edu.cs496project.mobileApp.graphics;





//! Changed out two sprites for the dog sprites because they were not reachable for some reason

import java.util.ArrayList;

import ycp.edu.cs496project.mobileApp.R;
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
	final private float holeRadius = 20.0f;
	final private float ballStopped = 0.1f;
	private int tooManyGhosts;
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
		private Sprite hole;
		int startX, startY, velocityX, velocityY;
		
	public Panel(Context context) 
	{
		super(context);

		// TODO 2: Initialize class fields
		
			tooManyGhosts = 50;
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
			//Add one sprite to the list (for the ball) starting at (0,0) (or wherever you wish) and with a random velocity between -3 and 3. Don't forget to set the field for the number of sprites.
			hole = new Sprite(getResources(), R.drawable.ic_launcher, 0,0,0,0);
		
			/*
			//nextGhost = Min + (int)(Math.random() * ((Max - Min) + 1));
			int MinLoc = 0;
			int MaxLoc = 300;
			int MinVel = -3;
			int MaxVel = 3;
			startX = MinLoc + (int)(Math.random() * ((MaxLoc - MinLoc) + 1));
			startY = MinLoc + (int)(Math.random() * ((MaxLoc - MinLoc) + 1));
			velocityX = MinVel + (int)(Math.random() * ((MaxVel - MinVel) + 1));
			velocityY = MinVel + (int)(Math.random() * ((MaxVel - MinVel) + 1));
			Sprite ball = new Sprite(getResources(), R.drawable.ball_sprite, startX, startY, velocityX, velocityY);
			mSpriteList.add(ball);
			*/
			
			createRedGhost();
	}
	
	public int getRandomNum(int Min, int Max)
	{
		return (int) Min + (int)(Math.random() * ((Max - Min) + 1));
	}
	public void createRedGhost()
	{	
		int MinLoc = 0;
		int MaxLoc = this.getWidth();
		int MinVel = -3;
		int MaxVel = 3;
		startX = getRandomNum(MinLoc, MaxLoc);
		startY = getRandomNum(MinLoc, MaxLoc);
		velocityX = getRandomNum(MinVel, MaxVel);
		velocityY = getRandomNum(MinVel, MaxVel);
		Sprite redGhost = new Sprite(getResources(), R.drawable.ic_launcher , startX, startY, velocityX, velocityY);
		mSpriteList.add(redGhost);
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) 
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

	
	/* TODO 5: Add code to update() to call the update method on the ball sprite. 
	 * Be sure to do this in a thread safe manner (i.e. within a synchronized block). 
	 * Also call the checkGameEnd() method (assigning the return value to the game flag).
  */
	public void update(long elapsedTime) 
	{
		if (gameOver != true)
		{
			
			//nextGhost = Min + (int)(Math.random() * ((Max - Min) + 1));
		      //int nextGhost = 0 + (int)(Math.random() * ((100 - 0) + 1));
		      int nextGhost = getRandomNum(0, 100);
		      if (nextGhost > 95)
		      {
		    	  createRedGhost();
		      }
		      
		      
		   synchronized (mSpriteList) 
		   {
		      for (Sprite sprite : mSpriteList) 
		      {
		         sprite.update(elapsedTime);
		         if (checkGameEnd(sprite) == true)
		         {
		        	 gameOver = true;
		         }
		      }
		   }
		}
	}

	// TODO 6: Draw hole
	/*Add code to doDraw() to draw a circle in the center of the screen 
	 * for the "hole" with radius holeRadius. 
	 * Then call the draw method on the ball sprite. 
	 * Be sure to do this in a thread safe manner.
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
				//draw the ball(s) because they are sprites in the sprite list
		        sprite.doDraw(canvas);
		    }
			
			
//create a getter for gameover and call it from the view
			//Display message if game over
			if (gameOver == true)
			{
				//Toast.makeText(Panel, "You Win!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	
	
	
	
	/* TODO 7: Add code to the onTouchEvent() event handler to compute a change in velocity based on a user swipe, i.e. the further they swipe the more there will be a velocity change in that direction.*/
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    // Get touch event location
	    float currentX = event.getX();
	    float currentY = event.getY();
	    float deltaX, deltaY;

	    // Compute scale factor based on window size
	    float scalingFactor = 5.0f / ((mWidth > mHeight) ? mWidth : mHeight);

	    // Use move touch event
	    switch (event.getAction()) {
	        case MotionEvent.ACTION_MOVE:
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
	
	//Currently removes sprite with a SWIPE, not a click, not sure why	    			
		    			//draw the ball(s) because they are sprites in the sprite list
		    			if (Math.abs(distance(currentX, currentY, sprite.getCenterX(), sprite.getCenterY())) < sprite.getRadius()* 3)
		    			{
		    				toRemove.add(sprite);
		    				
		    				//TODO:implement update of highscore
		    				
		    				//sprite.changeVelocity(deltaX * scalingFactor, deltaY * scalingFactor);
		    			}
		    			//sprite.changeVelocity(deltaX * scalingFactor, deltaY * scalingFactor);
		    		}
	        		
	        		for (Sprite sprite : toRemove) {
	        			mSpriteList.remove(sprite);
	        		}
	        	}
	    }

	    // Save current x, y
	    previousX = currentX;
	    previousY = currentY;

	    return true;
	}
	
	//determine when the ball is within the area of the hole and is "stopped" 
	//(i.e. has total velocity less than ballStopped). 
	//Hint: the ball is within the area if the distance between the center of the hole and the ball is less than the radius of the hole minus the radius of the ball.
	//center of hole - center of ball < hole.radius - ball.radius
	private boolean checkGameEnd(Sprite sprite) 
	{
		// TODO 8: Check if ball is within hole region
		if (mSpriteList.size() > tooManyGhosts) 
		{
			return true;
		}
		
		return false;
	}

	private float distance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
}
