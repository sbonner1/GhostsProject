package ycp.edu.cs496project.mobileApp.servletControllers;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import android.os.AsyncTask;
import android.util.Log;
import ycp.edu.cs496project.mobileApp.json.JSON;
import ycp.edu.cs496project.mobileApp.model.User;

/**
 * A controller that creates a User object from the username and password and sends a user object to the server
 * to register a new user
 * 
 * @author josh coady
 *
 */
public class UserRegisterController extends AsyncTask<String, Void, Boolean>{
	
	/**
	 * controller method that makes a POST request sending the requested username and password to create a new user
	 * 
	 * @param username a user's username
	 * @param password the user's password
	 * @return true if the new user was registered, false otherwise
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private boolean registerNewUser(String username, String password) throws JsonGenerationException, JsonMappingException, IOException{
		
		String tag = "register user";
		
		String uri = "http://" +  ycp.edu.cs496project.mobileApp.MainActivity.URI_IP_ADDRESS + "/DatabaseApp/?action=addUser";
		
		User newUser = new User(username, password);
		
		//create a StringWriter that places 
		StringWriter sw = new StringWriter();
		JSON.getObjectMapper().writeValue(sw, newUser);
		
		//send an http POST request
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse resp = null;
		
		//attach the user's password to the POST request using a StringEntity
		StringEntity jsonEntity = new StringEntity(sw.toString());
		jsonEntity.setContentType("application/json");
		httpPost.setEntity(jsonEntity);
		
		Log.i(tag, sw.toString());
		
		resp = client.execute(httpPost);
		
		Log.i(tag, resp.getStatusLine().toString());
		
		if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			Log.i(tag, "contacted server");
			return true;
		}
		Log.i(tag, "failed to connect to server");
		return false;
	}

	/**
	 * AysncTask method to register a new user via a thread separate from the UI thread
	 */
	@Override
	protected Boolean doInBackground(String... params) {
		try{
			return registerNewUser(params[0], params[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
