package ycp.edu.cs496project.mobileApp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ycp.edu.cs496project.mobileApp.servletControllers.TestController;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

/**
 * 
 * @author josh coady
 *
 */
public class MainActivity extends Activity {
	
	private String username; //a player's username
	private String password; //the player's password
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		if(username == null || password == null){
			//TODO: go to the login page
		}
		
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
	 * @throws UnknownHostException 
	 */
	public void onPlayClick(View v){
		//create an intent to go to the gameplay activity
		Intent startGameIntent = new Intent(this, MarbleMadness.class);
		startActivity(startGameIntent);
	}
	
	/**
	 * an onClick method to see the game's highscores and the user's trophies
	 * 
	 * @param v a view
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void onScoreClick(View v) throws UnknownHostException, IOException{
		//start the player info activity
		//if(InetAddress.getByName("10.0.2.2").isReachable(1000)){
			Intent playerInfoIntent = new Intent(this, PlayerInfoActivity.class);
			startActivity(playerInfoIntent);
		//}else{
			//Toast.makeText(MainActivity.this, "Server is offline", Toast.LENGTH_SHORT).show();
		//}
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
	
	public void onGoToLoginClick(View v){
		Intent loginIntent = new Intent(this, LoginActivity.class);
		startActivity(loginIntent);
	}

}
