package ycp.edu.cs496project.mobileApp;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;

/**
 * An Activity for the user to view their highscore and trophy information from the database. The user will be able to
 * use tabs to switch between viewing the highscore and trophies stored on the server-side database.  
 * 
 * @author user
 *
 */
@SuppressWarnings("deprecation")
public class PlayerInfoActivity extends TabActivity {

	TabHost tabhost; //the TabHost for this activity
	Intent tabIntent; //an intent to set a view to each tab
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_info);
		
		tabhost = getTabHost();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_info, menu);
		return true;
	}

}
