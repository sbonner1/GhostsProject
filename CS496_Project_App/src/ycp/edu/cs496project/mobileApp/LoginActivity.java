package ycp.edu.cs496project.mobileApp;

import ycp.edu.cs496project.mobileApp.model.User;
import ycp.edu.cs496project.mobileApp.servletControllers.UserLoginController;
import ycp.edu.cs496project.mobileApp.servletControllers.UserRegisterController;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity will handle logging in as well as registering of users into the server. 
 * 
 * @author josh coady
 *
 */
public class LoginActivity extends Activity {
	
	String loginTag = "login test";
	String registerTag = "register test";
	
	//an error message to display if user attempts to login without typing a username or password
	private static final String empty_str_error_message = "Please enter both a username and password.";
	//an error message to display if the user incorrectly enters their username or password
	private static final String invalid_submission_message = "Incorrect username or password";
	//a message to display if a user registering selects a username that is already being used
	private static final String username_exists_message = "Username already being used.";
	
	private TextView userLabel; //text label for the username textbox
	private TextView passwordLabel; //text label for the password textbox
	private EditText usernameText; //textbox to enter the username
	private EditText passwordText; //textbox to enter the user's password
	private Button button; //a button to either log in or register a new user
	
	private User user;
	
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
	 *  a method to set the view when a user is logging in 
	 */
	public void setLoginView(){
		//the layout for the user to log in
		LinearLayout loginLayout = new LinearLayout(this);
		loginLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		//a label for the username textbox
		userLabel = new TextView(this);
		userLabel.setText("Username:");
		userLabel.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		//the username textbox
		usernameText = new EditText(this);
		usernameText.setInputType(0x00000001); //set inputType to normal text
		usernameText.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		//a label for the password textbox
		passwordLabel = new TextView(this);
		passwordLabel.setText("Password:");
		passwordLabel.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		//the password texbox
		passwordText = new EditText(this);
		passwordText.setInputType(0x00000081); //set inputType text password type
		passwordText.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		// Add a login button
		button = new Button(this);
		button.setText("Login");
		button.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		
		setLoginButtonListener();
		
		//add widgets to view
		loginLayout.addView(userLabel);
		loginLayout.addView(usernameText);
		loginLayout.addView(passwordLabel);
		loginLayout.addView(passwordText);
		loginLayout.addView(button);
		
		setContentView(loginLayout, llp);
	}
	
	/**
	 * a method to set the UI for registering a new user. 
	 */
	public void setRegisterNewUserView(){
		//the layout for registering a user
		LinearLayout registerLayout = new LinearLayout(this);
		registerLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		//a label for the username textbox
		userLabel = new TextView(this);
		userLabel.setText("Username:");
		userLabel.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		//the username textbox
		usernameText = new EditText(this);
		usernameText.setInputType(0x00000001); //set inputType to normal text
		usernameText.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		//a label for the password textbox
		passwordLabel = new TextView(this);
		passwordLabel.setText("Password:");
		passwordLabel.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		//the password texbox
		passwordText = new EditText(this);
		passwordText.setInputType(0x00000081); //set inputType text password type
		passwordText.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		// Add a login button
		button = new Button(this);
		button.setText("Register");
		button.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		
		setRegisterClickListener();
		
		//add widgets to the view
		registerLayout.addView(userLabel);
		registerLayout.addView(usernameText);
		registerLayout.addView(passwordLabel);
		registerLayout.addView(passwordText);
		registerLayout.addView(button);

		setContentView(registerLayout, llp);
	}
	
	/**
	 * a method to initialize the OnclickListener for the button when a user logs in
	 */
	public void setLoginButtonListener(){
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usernameText.getText().toString();
				String password = passwordText.getText().toString();
				
				UserLoginController controller = new UserLoginController();
				
				try{
					//get the user's information from the server, if the user exists
					user = controller.loginUser(username, password);
					
					//if no such user currently exists, then send a Toast with the message that either
					//the username or password entered may be incorrect.
					//if the user does exist and the information is retrieved from the server, then go directly to the 
					//main menu (MainActivity).
					if(user == null){
						Toast.makeText(LoginActivity.this, invalid_submission_message, Toast.LENGTH_SHORT).show();
						Log.i(loginTag, "null user object");
					}else{
						Log.i(loginTag, user.getUserName());
					}
				}catch(Exception e){
					e.printStackTrace();
				}				
			}
		});
	}
	
	/**
	 * a method to set the onClickListener for the button when using the Register user view
	 */
	public void setRegisterClickListener(){
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usernameText.getText().toString();
				String password = passwordText.getText().toString();
				
				try{
					UserRegisterController registerController = new UserRegisterController();
					boolean registered = registerController.registerNewUser(username, password);
					if(registered == true){
						Toast.makeText(LoginActivity.this, "register success", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(LoginActivity.this, username_exists_message, Toast.LENGTH_SHORT).show();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * sets the layout UI for a pre-existing user to log in to the server
	 * 
	 * @param v a view
	 */
	public void onLoginViewClick(View v){
		setLoginView();
	}
	
	/**
	 * sets the layou UI for registering a user to the server
	 * 
	 * @param v a view
	 */
	public void onRegisterViewClick(View v){
		setRegisterNewUserView();
	}
}
