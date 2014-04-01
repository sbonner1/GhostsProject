package ycp.edu.cs496project.mobileApp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * an activity for playing the game.
 * 
 * @author josh coady
 *
 */
public class GameScreenActivity extends Activity {
	
	private final static int NUM_COLS = 4;//the number of columns for the game grid
	private final static int NUM_SPACES = NUM_COLS * NUM_COLS;
	
	private GridView gameGrid; //the game grid
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//imageAdapter = new ImageArrayAdapter<Integer>(this, R.layout.image_item, imageArray);
		
		//initialize the grid dimensions
		gameGrid = new GridView(this);
		gameGrid.setNumColumns(NUM_COLS);
		gameGrid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gameGrid.setColumnWidth(90);
		gameGrid.setVerticalSpacing(10);
		gameGrid.setHorizontalSpacing(10);
		gameGrid.setGravity(0x11);
		//gameGrid.setAdapter();
		
		
		setContentView(R.layout.activity_game_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_screen, menu);
		return true;
	}
	
	/**
	 * an implementation of an ArrayAdapter to use ImageView to populate the GridView
	 * instead of the default TextView.
	 * 
	 * @param <T>
	 */
	private class ImageArrayAdapter<T> extends ArrayAdapter<T>{

		/**
		 * constructor
		 * 
		 * @param context
		 * @param resource
		 * @param imageArray
		 */
		public ImageArrayAdapter(Context context, int resource, T[] imageArray) {
			super(context, resource, imageArray);
		}
		
		/**
		 * an overwritten implementation of the ArrayAdapter getView() method, to enable the GridView
		 * to be populated with images.
		 */
		public View getView(int position, View convertView, ViewGroup parent){
			ImageView image;
			
			if(convertView == null){
				image = new ImageView(this.getContext());
				image.setLayoutParams(new GridView.LayoutParams(85, 85));
				image.setScaleType(ImageView.ScaleType.CENTER_CROP);
				image.setPadding(8, 8, 8, 8);
			}else{
				image = (ImageView) convertView;
			}
			
			//image.setImageResource(imageArray[position]);
			return image;
		}
	}
}


