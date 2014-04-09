package ycp.edu.cs496project.mobileApp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText usernameEditText;
	private EditText passwordEditText;
	
	//an error message to display if user attempts to login without typing a username or password
	private static final String empty_str_error_message = "Please enter both a username and password.";
	//an error message to display if the user incorrectly enters their username or password
	private static final String invalid_submission_message = "Incorrect username or password";
	//a message to display if a user registering selects a username that is already being used
	private static final String username_exists_message = "Username already being used.";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		usernameEditText = (EditText)findViewById(R.id.username_text);
		passwordEditText = (EditText)findViewById(R.id.password_text);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	/**
	 * 
	 * @param v
	 */
	public void onLoginClick(View v){
		
		String username = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		
		if(username.equals("") || password.equals("")){
			Toast.makeText(LoginActivity.this, empty_str_error_message, Toast.LENGTH_SHORT).show();
			return;
		}
		
		//TODO: use controller to check database if user exists and correct password was used
		
		//if user fails login, make toast message informing user
		Toast.makeText(LoginActivity.this, invalid_submission_message, Toast.LENGTH_SHORT).show();
		
		//if login succeeds, go to main activity
		
	}
	
	/**
	 * 
	 * @param v
	 */
	public void onRegisterClick(View v){
		String username = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		
		if(username.equals("") || password.equals("")){
			Toast.makeText(LoginActivity.this, empty_str_error_message, Toast.LENGTH_SHORT).show();
			return;
		}
		
		//check database to see if username is being used
		//if so, create a new user and log new user in?
		
		Toast.makeText(LoginActivity.this, invalid_submission_message, Toast.LENGTH_SHORT).show();
		
	}

}
