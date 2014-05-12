package ycp.edu.cs496project.mobileApp.servletControllers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;
import ycp.edu.cs496project.mobileApp.json.JSON;

/**
 * a controller to get highscore information from the database
 * 
 * @author josh coady
 *
 */
public class HighscoreController extends AsyncTask<int[], Void, int[]>{
	
	/**
	 * retrieves the list of highscores from the server
	 * 
	 * @return an integer array of highscores
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private int[] getLeaderboard() throws URISyntaxException, ClientProtocolException, IOException{
		
		String tag = "HighscoreController";
		
		String uri = "http://" +  ycp.edu.cs496project.mobileApp.MainActivity.URI_IP_ADDRESS + "/DatabaseApp/?action=getUserScoreList";
		
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

	/**
	 * AsyncTask method to get access server using a non-UI thread
	 */
	@Override
	protected int[] doInBackground(int[]... params) {
		
		try{
			return getLeaderboard();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
