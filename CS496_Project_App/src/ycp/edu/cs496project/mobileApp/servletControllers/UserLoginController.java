package ycp.edu.cs496project.mobileApp.servletControllers;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import android.os.AsyncTask;
import android.util.Log;
import ycp.edu.cs496project.mobileApp.json.JSON;
import ycp.edu.cs496project.mobileApp.model.User;

/**
 * a controller to log into the server by sending the user's name and password to the server. This controller will also receive
 * a response from the server in the form of a user object. 
 * 
 * @author josh coady
 *
 */
public class UserLoginController extends AsyncTask<String, Void, User>{
	String tag = "loginController";
	
	/**
	 * a controller to see if a username and password relates to a user registered on the database, 
	 * if such a user exists, then that user's information will be sent to the client
	 * 
	 * @param userName - the user's name
	 * @param password - user's password
	 * @return - if the user is pre-existing: the user's information. otherwise: null if the user does not currently exist
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws JSONException
	 */
	private User loginUser(String userName, String password) throws ClientProtocolException, IOException, URISyntaxException, JSONException{
		
		//create URI
		String uri = "http://" + ycp.edu.cs496project.mobileApp.MainActivity.URI_IP_ADDRESS + "/DatabaseApp/" + userName + "?action=getUser";
		
		//create a StringWriter that places 
		StringWriter sw = new StringWriter();
		JSON.getObjectMapper().writeValue(sw, password);
		
		//send an http POST request
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse resp = null;
		
		//attach the user's password to the POST request using a StringEntity
		StringEntity jsonEntity = new StringEntity(sw.toString());
		jsonEntity.setContentType("application/json");
		httpPost.setEntity(jsonEntity);
		
		Log.i(tag, "password: " + sw.toString());
		
		resp = client.execute(httpPost);
		
		Log.i(tag, resp.getStatusLine().toString());
		
		if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			HttpEntity entity = resp.getEntity();
			Log.i(tag, "retrieved a user from server");
			return JSON.getObjectMapper().readValue(entity.getContent(), User.class);
		}
		Log.i(tag, "failed to login.");
		return null; 
	}

	/**
	 * AsyncTask method to use login controller on a non-UI thread
	 */
	@Override
	protected User doInBackground(String... params) {
		
		try{
			Log.i(tag, loginUser(params[0], params[1]).getUserName());
			return loginUser(params[0], params[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
