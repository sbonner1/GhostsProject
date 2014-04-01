package ycp.edu.cs496project.mobileApp.servletControllers;

public class TestController {
	public String test(){
		
		/*
		URI uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/DatabaseApp", null, null);
		
		//send an http GET request
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		HttpResponse resp = null;
		
		resp = client.execute(httpGet);
		
		if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			HttpEntity entity = resp.getEntity();
			return 
		} 
		*/
		// Return null if invalid response
		
		return "Error with test.";
	}
}
