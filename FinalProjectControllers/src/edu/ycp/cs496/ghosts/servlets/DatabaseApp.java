package edu.ycp.cs496.ghosts.servlets;
/**
 * @author sbonner1
 * This class will handle the get, post, etc. methods for the database
 * 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.ycp.cs496.ghosts.controller.AddUser;
import edu.ycp.cs496.ghosts.controller.DeleteUser;
import edu.ycp.cs496.ghosts.controller.DeleteUserList;
import edu.ycp.cs496.ghosts.controller.GetUserController;
import edu.ycp.cs496.ghosts.controller.GetUserList;
import edu.ycp.cs496.ghosts.controller.ReplaceUser;
import edu.ycp.cs496.ghosts.controller.ReplaceUserList;
import edu.ycp.cs496.ghosts.model.User;
//import org.eclipse.jetty.util.ajax.JSON;
import edu.ycp.cs496.ghosts.model.json.JSON;

public class DatabaseApp extends HttpServlet{
		private static final long serialVersionUID = 1L;
		
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String pathInfo = req.getPathInfo();
			if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
				//retrieve inventory from database
				GetUserList getController = new GetUserList();
				List<User> userList = getController.getUserList();
				//print inventory to user's terminal
				JSON.getObjectMapper().writeValue(resp.getWriter(), userList);
			}else{
				// Get the user name
				if (pathInfo.startsWith("/")) {
					pathInfo = pathInfo.substring(1);
				}
				
				GetUserController controller = new GetUserController(); //**FIX THIS SO WE CAN GET PASSWORD INFO TOO**
				User user = controller.getUser(pathInfo);				//GetUserController and several other classes
																		//will require changes if the user parameter
																		//is changed back to include its password
				if (user == null) {
					// No such item, so return a NOT FOUND response
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					resp.setContentType("text/plain");
					resp.getWriter().println("No such item: " + pathInfo);
					return;
				}
				
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				JSON.getObjectMapper().writeValue(resp.getWriter(), user);
			}
		}
		@Override
		protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, JsonGenerationException, JsonMappingException, IOException{
			String pathInfo = req.getPathInfo(); 
			
			 // check the uri substring to see if there is an item to be added to the database.
			 // if there is no single item, replace the entire inventory 
			
			if(pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")){
				ArrayList<User> newUserList = JSON.getObjectMapper().readValue(req.getReader(), new TypeReference<List<User>>(){});
				
				//update database with the controller
				ReplaceUserList controller = new ReplaceUserList();
				controller.replaceUserList(newUserList);
				
				//print the updated list to the user
				GetUserList getController = new GetUserList();
				newUserList = getController.getUserList();

				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");

				resp.getWriter().println("Inventory Replaced."); 
				JSON.getObjectMapper().writeValue(resp.getWriter(), newUserList);
			}else{
				//get the item name
				if(pathInfo.startsWith("/")){
					pathInfo = pathInfo.substring(1);
				}
				User newUser = JSON.getObjectMapper().readValue(req.getReader(),  User.class);
				//update the database with the replacement item
				ReplaceUser controller = new ReplaceUser();
				controller.replaceUser(pathInfo, newUser);

				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				resp.getWriter().println(pathInfo + " has been replaced with: " + newUser.getUserName());
			}
		}
		
		@Override 
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, JsonGenerationException, JsonMappingException, IOException{
			String pathInfo = req.getPathInfo(); //the path
			//check to see that there is no pathname after "/inventory"
			if(pathInfo != null){
				resp.getWriter().println("Post unsuccessful, new item not added to list");
				return;
			}

			User newUser = JSON.getObjectMapper().readValue(req.getReader(), User.class);
			AddUser controller = new AddUser(); //********WILL ALSO NEED TO CHANGE THIS TO INCLUDE PASSWORD******
			controller.addNewUser(newUser);

			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			JSON.getObjectMapper().writeValue(resp.getWriter(), newUser);
		}
		@Override 
		protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, JsonGenerationException, JsonMappingException, IOException{
			String pathInfo = req.getPathInfo(); //the path
			//if there is an item name then delete it
			//else, if there is no item name, delete the whole database
			if(pathInfo != null){
				// Get the item name
				if(pathInfo.startsWith("/")) {
					pathInfo = pathInfo.substring(1);
				}
				//delete the item from inventory
				DeleteUser controller = new DeleteUser();
				controller.deleteUser(pathInfo);
				
				//a controller to get the inventory after an item is deleted
				GetUserList responseController = new GetUserList();
				
				//retrieve inventory and show the update
				List<User> inventory = responseController.getUserList();
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				JSON.getObjectMapper().writeValue(resp.getWriter(), inventory);
			}else{
				
				//delete the entire inventory
				DeleteUserList controller = new DeleteUserList();
				controller.deleteUserList();
				resp.getWriter().println("Inventory Deleted.");
			}
		}
}
