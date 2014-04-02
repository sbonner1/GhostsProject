package ycp.edu.cs496project.mobileApp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * an activity for playing the game.
 * 
 * @author josh coady
 *
 */
public class GameScreenActivity extends Activity {
	
	private final static int NUM_COLS = 4;//the number of columns for the game grid
	private final static int NUM_SPACES = NUM_COLS * NUM_COLS;
	
	//private GridView gameGrid; //the game grid
	//Integer[] imageArray = new Integer[1];
	
	ImageButton imgButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//imageArray[0] = R.drawable.sample_0;
		//ImageArrayAdapter<Integer> imageAdapter = new ImageArrayAdapter<Integer>(this, R.layout.list_image, imageArray);
		
		//gameGrid = (GridView)findViewById(R.id.gridView1);
		
		//initialize the grid dimensions
		/*
		gameGrid = new GridView(this);
		gameGrid.setNumColumns(NUM_COLS);
		gameGrid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gameGrid.setColumnWidth(90);
		gameGrid.setVerticalSpacing(10);
		gameGrid.setHorizontalSpacing(10);
		//gameGrid.setGravity(0x11);
		gameGrid.setAdapter(imageAdapter);
		*/
		
		
		setContentView(R.layout.activity_game_screen);
		
		imgButton = (ImageButton)findViewById(R.id.imageButton);
		
		if(imgButton == null){
			Toast.makeText(GameScreenActivity.this, "null", Toast.LENGTH_SHORT).show();
		}
		
		
		imgButton.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				imgButton.setImageResource(R.drawable.sample_4);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_screen, menu);
		return true;
	}
	
	
	public void onImageClick(View v){
		
	}
	
	
	
	/*public void createOnImageButtonClick(){
		
		imgButton.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//if(imgButton. == R.drawable.sample_4){
					//imgButton.setImageResource(R.drawable.sample_5);
				//}else{
					//imgButton.setImageResource(R.drawable.sample_4);
				//}
				
			}
			
		});
		
	}*/
	
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
				image = new ImageView(getContext());
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


