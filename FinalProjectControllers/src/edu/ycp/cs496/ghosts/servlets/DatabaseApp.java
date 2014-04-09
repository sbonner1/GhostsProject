package edu.ycp.cs496.ghosts.servlets;
/**
 * @author sbonner1
 * This class will handle the get, post, etc. methods for the database
 * 
 */

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.eclipse.jetty.util.ajax.JSON;
import edu.ycp.cs496.ghosts.model.json.JSON;

public class DatabaseApp extends HttpServlet{
		private static final long serialVersionUID = 1L;
		
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String pathInfo = req.getPathInfo();
			if(pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")){
				//retrieve from the database
				
				
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				JSON.getObjectMapper().writeValue(resp.getWriter(), "Error");
			
			}else if(pathInfo.startsWith("/")){
				
			
			
			}else{

				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				JSON.getObjectMapper().writeValue(resp.getWriter(), "It's alive!!");
				
			}
		}
		
}
