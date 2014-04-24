package ycp.edu.cs496project.mobileApp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	private final static int NUM_ROWS = 6;//the number of rows for the game grid
	private final static int NUM_GRID_SPACES = NUM_COLS * NUM_ROWS; //the number of positions in the game grid
	
	private GridView gameGrid; //the game grid
	//an array of resource ids for the images that will be placed into the gridView
	//Integer[] imageArray = {R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2, R.drawable.sample_3,
	//						R.drawable.sample_4, R.drawable.sample_5, R.drawable.sample_5, R.drawable.sample_6,
	//						R.drawable.sample_7};
	private OnItemClickListener gridClickListener; //the itemClickListener for clicking Images in the GridView
	private ImageArrayAdapter<Integer> imageAdapter; //the adapter to place images on the GridView
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_screen);
		
		//initialize the adapter to place images into gridView
		//imageAdapter = new ImageArrayAdapter<Integer>(this, R.layout.list_image, imageArray);
		//initialize the GridView
		initGridView(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_screen, menu);
		return true;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		//game loop
		
		
	}
	
	/**
	 * a method to initialize the gameGrid with parameter, ImageButton Adapter and OnItemClickListener
	 */
	public void initGridView(){
		gameGrid = (GridView)findViewById(R.id.gridView1);
		gameGrid.setNumColumns(NUM_COLS);
		gameGrid.setAdapter(imageAdapter);
		gameGrid.setOnItemClickListener(gridClickListener = new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
				// TODO Auto-generated method stub
				ImageView img = (ImageView) v;
				img.setImageResource(R.drawable.ic_launcher);
			}
		});
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
				image = new ImageView(getContext());
				image.setLayoutParams(new GridView.LayoutParams(85, 85));
				image.setScaleType(ImageView.ScaleType.CENTER_CROP);
				image.setPadding(8, 8, 8, 8);
			}else{
				image = (ImageView) convertView;
			}
			//set the image using an image file's resource id
			//image.setImageResource(imageArray[position]);
			return image;
		}
	}
}


