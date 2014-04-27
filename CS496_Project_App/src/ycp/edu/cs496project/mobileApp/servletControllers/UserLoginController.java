package ycp.edu.cs496project.mobileApp.servletControllers;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import ycp.edu.cs496project.mobileApp.json.JSON;
import ycp.edu.cs496project.mobileApp.model.User;

/**
 * a controller to log into the server by sending the user's name and password to the server. This controller will also revieve
 * a respsonse from the server in the form of a user object. 
 * 
 * @author josh coady
 *
 */
public class UserLoginController {
	/**
	 * 
	 * @param userName - the user's name
	 * @param password - user's password
	 * @return - if the user is pre-existing: the user's information. otherwise: null if the user does not currently exist
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws JSONException
	 */
	public User loginUser(String userName, String password) throws ClientProtocolException, IOException, URISyntaxException, JSONException{
		
		String tag = "loginController";
		
		//create URI
		String uri = "http://10.0.2.2:8081/DatabaseApp/" + userName + "?action=getUser";
		
		//create a JSON object with the user's login information
		JSONObject jsonUserInfo = new JSONObject();
		jsonUserInfo.put("userName", userName);
		jsonUserInfo.put("password", password);
		
		//send an http POST request
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse resp = null;
		
		//attach the user's login information to the POST request
		StringEntity jsonEntity = new StringEntity(jsonUserInfo.toString());
		jsonEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		httpPost.setEntity(jsonEntity);
		
		resp = client.execute(httpPost);
		
		Log.i(tag, resp.getStatusLine().toString());
		
		if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			HttpEntity entity = resp.getEntity();
			System.out.println("resp branch");
			Log.i(tag, "login success.");
			return JSON.getObjectMapper().readValue(entity.getContent(), User.class);
		}
		Log.i(tag, "failed to login.");
		return null; 
	}
}
