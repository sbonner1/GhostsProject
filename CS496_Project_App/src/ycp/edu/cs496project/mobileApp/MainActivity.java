package ycp.edu.cs496project.mobileApp;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ycp.edu.cs496project.mobileApp.model.User;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

/**
 * The main menu activity. After logging in or registering, the main menu will appear with two buttons. One button will take the 
 * user directly to the game, the other button will take the user to a new activity which will be able to display a leaderboard 
 * with the highest scores gained by all users playing the game, and the user will be able to view his/her won trophies
 * 
 * @author josh coady
 *
 */
public class MainActivity extends Activity {
	
	public static String URI_IP_ADDRESS = "10.0.2.2:8081";
	
	private User user;
	
	private ArrayList<String> str_arr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		str_arr = new ArrayList<String>();
		
		Intent userInfoIntent = getIntent();
		
		//get the user info from the login/register activity
		str_arr = userInfoIntent.getStringArrayListExtra(LoginActivity.USER_INFO_MESSAGE);
		
		Log.i("main", str_arr.get(0));
		
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
		
		
		/*ArrayList<String> str_arr = new ArrayList<String>();
		str_arr.add(user.getUserName());
		str_arr.add(user.getUserPassword());
		str_arr.add(Integer.toString(user.getUserScore()));
		*/
		
		startGameIntent.putStringArrayListExtra(LoginActivity.USER_INFO_MESSAGE, str_arr);
		
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
		Intent playerInfoIntent = new Intent(this, PlayerInfoActivity.class);
		startActivity(playerInfoIntent);
	}
	
	/*
	//onClick event to go to login 
	public void onGoToLoginClick(View v){
		Intent loginIntent = new Intent(this, LoginActivity.class);
		startActivity(loginIntent);
	}
	*/

}
