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
import org.json.JSONObject;

import ycp.edu.cs496project.mobileApp.json.JSON;
import ycp.edu.cs496project.mobileApp.model.User;

public class UserLoginController {
	public User loginUser(String userName, String password) throws ClientProtocolException, IOException, URISyntaxException{
		
		User user = new User(userName, password);
		
		JSONObject jsonUserInfo = new JSONObject();
		//jsonUserInfo.
		
		//create URI
		URI uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/DatabaseApp/" + userName, null, null);
		
		//send an http GET request
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		HttpResponse resp = null;
		
		resp = client.execute(httpGet);
		
		if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			HttpEntity entity = resp.getEntity();
			System.out.println("resp branch");
			return JSON.getObjectMapper().readValue(entity.getContent(), User.class);
		}
		return null; 
	}
}
