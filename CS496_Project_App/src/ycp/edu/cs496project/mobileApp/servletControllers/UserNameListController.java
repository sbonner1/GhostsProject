package ycp.edu.cs496project.mobileApp.servletControllers;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import ycp.edu.cs496project.mobileApp.json.JSON;
import android.util.Log;

public class UserNameListController {
	public String[] getUserNameList() throws ClientProtocolException, IOException{
		
		String tag = "UserNameList";
		
		String uri = "http://10.0.2.2:8081/DatabaseApp/?action=getUserList";
		
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
			return JSON.getObjectMapper().readValue(entity.getContent(), String[].class);
		}
		
		Log.i(tag, "Did not get array from server.");
		
		return null;
	}
}
