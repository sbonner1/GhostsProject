package ycp.edu.cs496project.mobileApp.servletControllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import ycp.edu.cs496project.mobileApp.json.JSON;

//temporary controller to test connectivity with the database
public class TestController {
	public String test() throws URISyntaxException, ClientProtocolException, IOException{
		
		URI uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/DatabaseApp", null, null);
		
		//send an http GET request
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		HttpResponse resp = null;
		
		resp = client.execute(httpGet);
		
		if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			HttpEntity entity = resp.getEntity();
			return JSON.getObjectMapper().readValue(entity.getContent(), String.class); 
		} 
		
		return "Error connecting to database.";
	}
}
