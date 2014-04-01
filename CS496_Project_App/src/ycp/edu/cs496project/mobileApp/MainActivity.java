package ycp.edu.cs496project.mobileApp;

import ycp.edu.cs496project.mobileApp.servletControllers.TestController;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * an onClick method to start the game
	 * 
	 * @param v a view
	 */
	public void onPlayClick(View v){
		//a simple test to see if the button works
		Toast.makeText(MainActivity.this, " play Button test.", Toast.LENGTH_SHORT).show();
		
		//create an intent to go to the gameplay activity
		Intent startGameIntent = new Intent(this, GameScreenActivity.class);
		startActivity(startGameIntent);
		
	}
	
	/**
	 * an onClick method to see the game's highscores
	 * 
	 * @param v a view
	 */
	public void onScoreClick(View v){
		//simple test to see button works
		Toast.makeText(MainActivity.this, "score button test.", Toast.LENGTH_SHORT).show();
	}
	
	//simple test to see if database can be accessed
	public void onDBTestClick(View v){
		TestController controller = new TestController();
		
		try{
			Toast.makeText(MainActivity.this, controller.test(), Toast.LENGTH_SHORT).show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
