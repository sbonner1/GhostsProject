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

import ycp.edu.cs496project.mobileApp.json.JSON;

/**
 * a controller to get highscore information from the database
 * 
 * @author josh coady
 *
 */
public class HighscoreController {
	public String[] getLeaderboard() throws URISyntaxException, ClientProtocolException, IOException{
		
		URI uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/DatabaseApp/Josh", "?action=getUserScore", null);
		
		//send an http GET request
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse resp = null;
		
		resp = client.execute(httpPost);
		
		if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			HttpEntity entity = resp.getEntity();
			System.out.println("resp branch");
			return JSON.getObjectMapper().readValue(entity.getContent(), String[].class);
		}
		
		return null;
	}
}
