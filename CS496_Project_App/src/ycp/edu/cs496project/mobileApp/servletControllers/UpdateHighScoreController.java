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

import android.os.AsyncTask;
import android.util.Log;
import ycp.edu.cs496project.mobileApp.json.JSON;
import ycp.edu.cs496project.mobileApp.model.User;

/**
 * a controller to update the the user's highscore data on the server, when a new, larger highscore is earned
 * 
 * @author jcoady & cfinch2
 *
 */

/*
 * mobile controller posts request to update (probably need some json to SEND the username and high score to the server)
 * servlet: post servlet will need logic to handle this action, will parse body of request and instantiate a server controller
 * server controller will then initiate the database transaction
 * database method will perform the operation
 */ 

public class UpdateHighScoreController extends AsyncTask<User, Void, Boolean>{
	
	/**
	 * takes a user object with the user's username, password and new highscore. This user is then sent to the server where it 
	 * will take the update the user's old highscore with the new highscore.
	 * 
	 * @return true if the highscore was successfully updated, false otherwise
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private boolean updateHighscore(User updateUser) throws URISyntaxException, ClientProtocolException, IOException{
		
		//Send data to server
		String uri = "http://" + ycp.edu.cs496project.mobileApp.MainActivity.URI_IP_ADDRESS + "/DatabaseApp/?action=updateUserScore";
		
		//create a StringWriter that places 
		StringWriter sw = new StringWriter();
		JSON.getObjectMapper().writeValue(sw, updateUser);
		
		//send an http POST request 
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse resp = null;
		
		//attach the user's score to the POST request using a StringEntity
		StringEntity jsonEntity = new StringEntity(sw.toString());
		jsonEntity.setContentType("application/json");
		httpPost.setEntity(jsonEntity);
		
		Log.i("update score", Integer.toString(updateUser.getUserScore()));
		
		//execute the POST request
		resp = client.execute(httpPost);
		
		//if a OK 200 response is received, return the array of integers sent via JSON
		if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			HttpEntity entity = resp.getEntity();
			return JSON.getObjectMapper().readValue(entity.getContent(), boolean.class);
		}
		
		//if an OK 200 response is not received then return null
		return false;
	}
	
	/**
	 * AsyncTask method to get access server using a non-UI thread
	 */
	
	@Override
	protected Boolean doInBackground(User... user) {
		
		try{
			return updateHighscore(user[0]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
}