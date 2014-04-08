package ycp.edu.cs496project.mobileApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_info);
		
		tabIntent = new Intent().setClass(this, PlayerInfoActivity.class);
		
		tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
		
		//create the first tab
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Tab1"),
	            PlayerInfoFragment.class, null);
		
		//create the second tab
		tabHost.addTab(tabHost.newTabSpec("simple2").setIndicator("Trophies"), 
				PlayerInfoFragment.class, null);
		
		//initially select the first tab
		tabHost.setCurrentTab(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_info, menu);
		return true;
	}

}


