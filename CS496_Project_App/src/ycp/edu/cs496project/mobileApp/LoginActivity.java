package ycp.edu.cs496project.mobileApp;

import ycp.edu.cs496project.mobileApp.model.User;
import ycp.edu.cs496project.mobileApp.servletControllers.UserLoginController;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author josh coady
 *
 */
public class LoginActivity extends Activity {
	
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
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	/**
	 * 
	 */
	public void setLoginView(){
		//the layout for the user to log in
		LinearLayout loginLayout = new LinearLayout(this);
		loginLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		//a label for the username textbox
		TextView userLabel = new TextView(this);
		userLabel.setText("Username:");
		userLabel.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		//the username textbox
		final EditText usernameText = new EditText(this);
		usernameText.setInputType(0x00000001); //set inputType to normal text
		usernameText.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		//a label for the password textbox
		TextView passwordLabel = new TextView(this);
		passwordLabel.setText("Password:");
		passwordLabel.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		//the password texbox
		final EditText passwordText = new EditText(this);
		passwordText.setInputType(0x00000081); //set inputType text password type
		passwordText.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		// Add a login button
		Button loginButton = new Button(this);
		loginButton.setText("Login");
		loginButton.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usernameText.getText().toString();
				String password = passwordText.getText().toString();
				
				UserLoginController controller = new UserLoginController();
				
				try{
					User user = controller.loginUser(username, password);
					
					if(user != null){
						Toast.makeText(LoginActivity.this, user.getUserName(), Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(LoginActivity.this, "unable to retrieve data", Toast.LENGTH_SHORT).show();
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		//add widgets to view
		loginLayout.addView(userLabel);
		loginLayout.addView(usernameText);
		loginLayout.addView(passwordLabel);
		loginLayout.addView(passwordText);
		loginLayout.addView(loginButton);
		
		setContentView(loginLayout, llp);
	}
	
	/**
	 * 
	 */
	public void setRegisterNewUserView(){
		//the layout for registering a user
		LinearLayout registerLayout = new LinearLayout(this);
		registerLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		//a label for the username textbox
		TextView userLabel = new TextView(this);
		userLabel.setText("Username:");
		userLabel.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		//the username textbox
		final EditText usernameText = new EditText(this);
		usernameText.setInputType(0x00000001); //set inputType to normal text
		usernameText.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		//a label for the password textbox
		TextView passwordLabel = new TextView(this);
		passwordLabel.setText("Password:");
		passwordLabel.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		//the password texbox
		final EditText passwordText = new EditText(this);
		passwordText.setInputType(0x00000081); //set inputType text password type
		passwordText.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		// Add a login button
		Button registerButton = new Button(this);
		registerButton.setText("Register");
		registerButton.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		
		//set the onClickListener for the register button
		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usernameText.getText().toString();
				String password = passwordText.getText().toString();
				//TODO: register user with database
			}
		});
		
		//add widgets to the view
		registerLayout.addView(userLabel);
		registerLayout.addView(usernameText);
		registerLayout.addView(passwordLabel);
		registerLayout.addView(passwordText);
		registerLayout.addView(registerButton);

		setContentView(registerLayout, llp);
	}

	/**
	 * 
	 * @param v
	 */
	public void onLoginViewClick(View v){
		setLoginView();
	}
	
	/**
	 * 
	 * @param v
	 */
	public void onRegisterViewClick(View v){
		setRegisterNewUserView();
	}
}
