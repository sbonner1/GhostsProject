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

import android.util.Log;
import ycp.edu.cs496project.mobileApp.json.JSON;
import ycp.edu.cs496project.mobileApp.model.User;

public class UserRegisterController {
	public boolean registerNewUser(String username, String password) throws JsonGenerationException, JsonMappingException, IOException{
		
		String tag = "register user";
		
		String uri = "http://10.0.2.2:8081/DatabaseApp/?action=addUser";
		
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
}
