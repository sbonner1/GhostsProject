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
 * a controller to get the top X number of highscores the database, currently gets ALL of them, needs reworked to limit X
 * 
 * @author josh coady & cflinch2
 *
 */

/*
 * mobile controller posts request to update (probably need some json to SEND the username and high score to the server)
 * servlet: post servlet will need logic to handle this action, will parse body of request and instantiate a server controller
 * server controller will then initiate the database transaction
 * database method will perform the operation
 */ 

public class UpdateHighScoreController extends AsyncTask<Boolean, Void, Boolean>{
	
	/**
	 * retrieves the list of highscores from the server
	 * 
	 * @return an integer array of highscores
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private boolean getLeaderboard(String userName, String password, int currentScore) throws URISyntaxException, ClientProtocolException, IOException{
		
		//Send data to server
		String uri = "http://10.0.2.2:8081/DatabaseApp/?action=updateUserScore";
		
		//Initialize user object
		User userScore = new User(userName, password);
		userScore.setUserScore(currentScore);
		
		//create a StringWriter that places 
		StringWriter sw = new StringWriter();
		JSON.getObjectMapper().writeValue(sw, userScore);
		
		//send an http POST request 
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse resp = null;
		
		//attach the user's score to the POST request using a StringEntity
		StringEntity jsonEntity = new StringEntity(sw.toString());
		jsonEntity.setContentType("application/json");
		httpPost.setEntity(jsonEntity);
		
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
	protected Boolean doInBackground(Boolean... params) {
		
		try{
			//return getLeaderboard(params[0], params[1], params[2]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
}