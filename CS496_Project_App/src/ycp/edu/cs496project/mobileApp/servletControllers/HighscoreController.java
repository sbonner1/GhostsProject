package ycp.edu.cs496project.mobileApp.servletControllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import ycp.edu.cs496project.mobileApp.json.JSON;

/**
 * a controller to get highscore information from the database
 * 
 * @author josh coady
 *
 */
public class HighscoreController {
	public int[] getLeaderboard() throws URISyntaxException, ClientProtocolException, IOException{
		
		String tag = "HighscoreController";
		
		//URI uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/DatabaseApp/", "?action=getUserScoreList", null);
		String uri = "http://10.0.2.2:8081/DatabaseApp/?action=getUserScoreList";
		
		//send an http POST request 
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse resp = null;
		
		//execute the POST request
		resp = client.execute(httpPost);
		
		Log.i(tag, resp.getStatusLine().toString());
		
		//if a OK 200 response is received, return the array of integers sent via JSON
		if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			HttpEntity entity = resp.getEntity();
			Log.i(tag, "array retrieved from server.");
			return JSON.getObjectMapper().readValue(entity.getContent(), int[].class);
		}
		
		Log.i(tag, "Did not get array from server.");
		//if an OK 200 response is not received then return null
		return null;
	}
}
