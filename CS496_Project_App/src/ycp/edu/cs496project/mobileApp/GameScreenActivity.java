package ycp.edu.cs496project.mobileApp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.GridView;

public class GameScreenActivity extends Activity {
	
	private final static int NUM_COLS = 4; //the number of columns for the game grid
	
	private GridView gameGrid; //the game grid
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//initialize the grid dimensions
		gameGrid = (GridView)findViewById(R.id.gridView);
		//gameGrid.setNumColumns(NUM_COLS);
		//gameGrid.setColumnWidth(20);
		//gameGrid.setAdapter(new ImageAdapter(this));
		
		setContentView(R.layout.activity_game_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_screen, menu);
		return true;
	}
}


