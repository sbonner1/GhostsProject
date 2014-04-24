package ycp.edu.cs496project.mobileApp.graphics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Sprite {
	private float mX;
	private float mY;
	private float mDx;
	private float mDy;
	private Bitmap mBitmap;
	private int type;
	
	public Sprite(Resources res, int imgResourceId, float x, float y, float dx, float dy) {
		//TODO 1:  Add code to the constructor to set each of the class fields based on the parameters.
		mBitmap = BitmapFactory.decodeResource(res,imgResourceId);
		type = imgResourceId;
		mX = x;
		mY = y;
		mDx = dx;
		mDy = dy;
		
	}
	
	public void update(long elapsedTime) {
		// TODO 2: Update position and check boundary collisions
		//increment the position based on the current velocity and elapsed time 
		//(divide the elapsedTime parameter by 20 for a more useable game). 
		//Also add a call to the checkBoundary() function after the position has been updated 
		//to perform collision detection if the sprite reaches the extents of the window.
//		mX = mX + mDx;// + (elapsedTime/20);
//		mY = mY + mDy;// + (elapsedTime/20);
		mX = mX + mDx*elapsedTime/20;
		mY = mY + mDy*elapsedTime/20;
		checkBoundary();
	}

	public void doDraw(Canvas canvas) {
		// TODO 3: Draw sprite bitmap
		//render the sprite bitmap using the Canvas parameter (with the drawBitmap() method)
		canvas.drawBitmap(mBitmap, mX, mY, null);
	}

	   // Collision detection
	   private void checkBoundary() {
	      // Left or right boundary
	      if (mX <= 0) {
	         mDx *= -1;
	         mX = 1;
	      } else if (mX + mBitmap.getWidth() >= Panel.mWidth) {
	         mDx *= -1;
	         mX = Panel.mWidth - mBitmap.getWidth() -10;
	      }

	      // Top or bottom boundary
	      if (mY <= 0) {
	         mDy *= -1;
	         mY = 1;
	      } else if (mY + mBitmap.getHeight() >= Panel.mHeight -1) {
	         mDy *= -1;
	         mY = Panel.mHeight - mBitmap.getHeight();
	      }
	   }
	
	// Velocity update method
	public void changeVelocity(float ddx, float ddy) {
		mDx += ddx;
		mDy += ddy;
	}
	
	// Getter methods
	
	public float getCenterX() {
		return mX + mBitmap.getWidth()/2;
	}
	
	public float getCenterY() {
		return mY + mBitmap.getHeight()/2;
	}
	
	public float getWidth() {
		return mBitmap.getWidth()/2;
	}
	
	public float getHeight() {
		return mBitmap.getHeight()/2;
	}
	
	public float getXVelocity() {
		return mDx;
	}
	
	public float getYVelocity() {
		return mDy;
	}
	
	public float getRadius() {
		return getWidth()/2;
	}

	public int getGhostType() {
		return type;
	}
		
	
}
