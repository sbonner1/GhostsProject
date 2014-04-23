package ycp.edu.cs496project.mobileApp;

import ycp.edu.cs496project.mobileApp.servletControllers.HighscoreController;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.TabHost.OnTabChangeListener;

/**
 * An Activity for the user to view their highscore and trophy information from the database. The user will be able to
 * use tabs to switch between viewing the highscore and trophies stored on the server-side database.  
 * 
 * @author josh coady
 *
 */
public class PlayerInfoActivity extends FragmentActivity{	
	
	String logTag = "PlayerInfo";
	
	private FragmentTabHost tabHost; //the tab host for the fragments
	private ListFragment highScoreFragment; //a fragment to display the leaderboard
	private ListFragment trophyFragment; //a fragment to display the players' trophies
	
	private ArrayAdapter<String> highScoreAdapter; //the adapter for highscore data
	private ArrayAdapter<String> trophyAdapter; //the adapter for trophy data
	
	private int[] leaderboardArr; //an array to store the leaderboard data as Integers once it is received from the server
	private String[] scoreStr; // the array of leaderboard scores once they are converted from Integers to Strings
	private String[] trophyArr; //an array to store the player's trophies once they are received from the server
	
	private TabListener tabListener; //an OnTabChangeListener to switch fragments on tab click
	
	//tab ids
	private static final String highScoreId = "highScore_tab"; //a string id for the tab that displays the leaderboard
	private static final String trophyId = "trophy_tab"; //a string id for the tab that displays the player's trophies
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_info);
		
		///////////////////////////////////////////////////
		
		trophyArr = new String[3];
		for(int i = 0; i < trophyArr.length; i++){
			trophyArr[i] = "trophy";
		}
		
		///////////////////////////////////////////////////
		
		//get the leaderboard from the database
		HighscoreController scoreController = new HighscoreController();
		
		try {
			leaderboardArr = scoreController.getLeaderboard();
			
			//if the user is unable to retrieve the leaderboard from the server, then initialize
			//an array of empty strings so the activity does not crash, otherwise, display the leaderboard from the server.
			if(leaderboardArr == null){
				scoreStr = new String[3];
				scoreStr[0] = "null";
				scoreStr[1] = "null";
				scoreStr[2] = "null";
				Log.i(logTag, "empty leaderboard.");
			}else{
				scoreStr = new String[leaderboardArr.length];
				for(int i = 0; i < scoreStr.length; i++){
					scoreStr[i] = String.valueOf(leaderboardArr[i]);
				}
				Log.i(logTag, "int array converted to String arr");
			}
			
			highScoreAdapter = new ArrayAdapter<String>(this, R.layout.tab_list_item, scoreStr);
			Log.i(logTag, "highscore adapter initialized.");
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		/*
		 * use controllers to get the user's trophy data
		 */
		
		//initialize the tabHost
		initTabHost();
		
		//initialize the fragments
		highScoreFragment = new ListFragment();
		trophyFragment = new ListFragment();
		 
		//initialize the adapter and trophy data
		trophyAdapter = new ArrayAdapter<String>(this, R.layout.tab_list_item, trophyArr);
		
		//set the adapters to their respective fragments
		highScoreFragment.setListAdapter(highScoreAdapter);
		trophyFragment.setListAdapter(trophyAdapter);
		
		//set the leaderboard tab and fragment as the initial tab and fragment when this activity is created
		getSupportFragmentManager().beginTransaction().add(R.id.realtabcontent, highScoreFragment).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_info, menu);
		return true;
	}
	
	/**
	 * a method to initialize the tab host
	 */
	public void initTabHost(){
		//initialize and setup tabHost
		tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
		//create the first tab
		tabHost.addTab(tabHost.newTabSpec(highScoreId).setIndicator("Highscore"), ListFragment.class, null);
		//create the second tab
		tabHost.addTab(tabHost.newTabSpec(trophyId).setIndicator("Trophies"), ListFragment.class, null);
		//initialize a TabListener
		tabListener = new TabListener();
		tabHost.setOnTabChangedListener(tabListener);
		//initially select the first tab
		tabHost.setCurrentTab(0);
	}
	
	/**
	 * An implementation of the OnTabChangeListener interface. This implementation replaces the fragment currently shown
	 * in the view with the fragment associated with the tab being clicked. 
	 * 
	 * @author josh coady
	 *
	 */
	private class TabListener implements OnTabChangeListener {
		@Override
		public void onTabChanged(String tabId) {
			//if the highscore tab is chosen, show the highscore fragment
			//if the trophy tab is chosen, show the trophy fragment
			if(tabId.equals(highScoreId)){
				getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, highScoreFragment).commit();
			}else{
				getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, trophyFragment).commit();
			}
			
		}
	}
}


