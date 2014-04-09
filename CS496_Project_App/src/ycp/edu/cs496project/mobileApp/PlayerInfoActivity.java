package ycp.edu.cs496project.mobileApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

/**
 * An Activity for the user to view their highscore and trophy information from the database. The user will be able to
 * use tabs to switch between viewing the highscore and trophies stored on the server-side database.  
 * 
 * @author josh coady
 *
 */
public class PlayerInfoActivity extends FragmentActivity{	
	
	private FragmentTabHost tabHost;
	private Intent tabIntent;
	private ListFragment highScoreFragment;
	private ListFragment trophyFragment;
	private FragmentTransaction tabSwitch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_info);
		
		tabIntent = new Intent().setClass(this, PlayerInfoActivity.class);
		
		tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
		
		
		
		//create the first tab
		tabHost.addTab(tabHost.newTabSpec("simple1").setIndicator("Highscore"),
	            ListFragment.class, null);
		
		//create the second tab
		tabHost.addTab(tabHost.newTabSpec("simple2").setIndicator("Trophies"), 
				ListFragment.class, null);
		
		tabHost.setOnTabChangedListener(null);
		
		//tabHost.setOnClickListener(tabListener);
		
		
		//initially select the first tab
		tabHost.setCurrentTab(1);
		
		//fragment = new PlayerInfoFragment();
		highScoreFragment = new ListFragment();
		trophyFragment = new ListFragment();
		
		
		String[] strArr = new String[3];
		for(int i = 0; i < strArr.length; i++){
			strArr[i] = "score";
		}
		
		String[] arr = new String[3];
		for(int i = 0; i < arr.length; i++){
			arr[i] = "trophy";
		}
		 
		ArrayAdapter<String> highScoreAdapter = new ArrayAdapter<String>(this, R.layout.tab_list_item, strArr);
		ArrayAdapter<String> trophyAdapter = new ArrayAdapter<String>(this, R.layout.tab_list_item, arr);
		
		highScoreFragment.setListAdapter(highScoreAdapter);
		trophyFragment.setListAdapter(trophyAdapter);
		
		
		
		getSupportFragmentManager().beginTransaction().add(R.id.realtabcontent, highScoreFragment).commit();
		
		tabSwitch = getSupportFragmentManager().beginTransaction();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_info, menu);
		return true;
	}
	
	private class TabListener implements OnTabChangeListener {

		@Override
		public void onTabChanged(String arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

}


