package edu.ycp.cs496.ghosts.servlets;
/**
 * @author sbonner1
 * This class is the servlet for accessing the UserList database
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
import edu.ycp.cs496.ghosts.controller.UpdateUserScore;
import edu.ycp.cs496.ghosts.model.User;
//import org.eclipse.jetty.util.ajax.JSON;
import edu.ycp.cs496.ghosts.model.json.JSON;

public class DatabaseApp extends HttpServlet{
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String action = req.getParameter("action");
			if (action != null) {
				req.setAttribute("action", action);
			}
		}
		
		@Override
		protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, JsonGenerationException, JsonMappingException, IOException{
			String pathInfo = req.getPathInfo(); 
			
			 // check the uri substring to see if there is an item to be added to the database.
			 // if there is no single item, replace the entire inventory 
			
			if(pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")){
				List<User> newUserList = JSON.getObjectMapper().readValue(req.getReader(), new TypeReference<List<User>>(){});
				
				//update database with the controller
				ReplaceUserList controller = new ReplaceUserList();
				controller.replaceUserList(newUserList);
				
				//print the updated list to the user
				GetUserList getController = new GetUserList();
				newUserList = getController.getUserList();

				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");

				resp.getWriter().println("User Replaced."); 
				JSON.getObjectMapper().writeValue(resp.getWriter(), newUserList);
			}else{
				//get the user name
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
			String action = req.getParameter("action");
			
			//check to see that there is no pathname after 
			if(pathInfo == null){
				resp.getWriter().println("Post unsuccessful");
				return;
			}
			
			if(action.equals("loginUser")){
				
			}
			
			if(action.equals("updateUserScore")){
				User newUser = JSON.getObjectMapper().readValue(req.getReader(), User.class);
				int score = newUser.getUserScore();
			
				UpdateUserScore controller = new UpdateUserScore();
				controller.updateUserScore(newUser, score);
				//boolean success = addController.addNewUser(newUser, password);
				
				
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				JSON.getObjectMapper().writeValue(resp.getWriter(), newUser);
				
			}
			if(action.equals("getUserList")){
				//retrieve inventory from database
				GetUserList getController = new GetUserList();
				List<User> userList = getController.getUserList();
				//print userList to user's terminal
				String[] userNameList = new String[userList.size()]; //return the list of user names for the scoreboard as
																	 //an array of strings to be displayed
				int count = 0;
				for(User user : userList){
					String userName = user.getUserName();
					userNameList[count] = userName;
					count++;
				}
				
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				JSON.getObjectMapper().writeValue(resp.getWriter(), userNameList);
			}
			
			if(action.equals("getUserScoreList")){
				GetUserList getController = new GetUserList();
				List<User> userList = getController.getUserList();
				
				int[] scoreList = new int[userList.size()]; //return the list of scores for the scoreboard as an array of integers
				
				int count = 0; //count variable to iterate through the list
				for(User user : userList){
					int userScore = user.getUserScore();
					scoreList[count] = userScore;
					count++;
				}
				
				JSON.getObjectMapper().writeValue(resp.getWriter(), scoreList);
			}
			if(action.equals("getUserScore")){
				if (pathInfo.startsWith("/")) {
					pathInfo = pathInfo.substring(1);
				}
				
				String password = JSON.getObjectMapper().readValue(req.getReader(), String.class);
				GetUserController getController = new GetUserController();
				User user = getController.getUser(pathInfo, password);
				if (user == null) {
					// No such item, so return a NOT FOUND response
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					resp.setContentType("text/plain");
					resp.getWriter().println("No such user: " + pathInfo);
					return;
				}
				
				int userScore = user.getUserScore();
				
				JSON.getObjectMapper().writeValue(resp.getWriter(), userScore);
				
			}
			if(action.equals("getUser")){
				// Get the user name
				if (pathInfo.startsWith("/")) {
					pathInfo = pathInfo.substring(1);
				}
				
				String password = JSON.getObjectMapper().readValue(req.getReader(), String.class);
				
				GetUserController controller = new GetUserController(); 
				User user = controller.getUser(pathInfo,password);		
				System.out.println("accessed database");
				if (user == null) {
					// No such item, so return a NOT FOUND response
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					resp.setContentType("text/plain");
					resp.getWriter().println("No such user: " + pathInfo);
					return;
				}
				System.out.println(user.getUserName());
				
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				JSON.getObjectMapper().writeValue(resp.getWriter(), user);
				
			}
			if(action.equals("addUser")){
					User newUser = JSON.getObjectMapper().readValue(req.getReader(), User.class);
					String password = newUser.getUserPassword();
					/*
					GetUserList responseController = new GetUserList();
					
					ArrayList<User> userList = responseController.getUserList();

				for(User users: userList){
					if(users.getUserName() == newUser.getUserName()){
						resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
						resp.setContentType("text/plain");
						resp.getWriter().println("User " + pathInfo + "already exists");
						return;
					}
				}
				*/
				
				AddUser addController = new AddUser(); 
				boolean success = addController.addNewUser(newUser, password);
				
				if (success) {
					resp.setStatus(HttpServletResponse.SC_OK);
					resp.setContentType("application/json");
					JSON.getObjectMapper().writeValue(resp.getWriter(), newUser);
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					resp.setContentType("text/plain");
					resp.getWriter().println("User " + pathInfo + "already exists");
				}
			}
		}
		
		
		@Override 
		protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, JsonGenerationException, JsonMappingException, IOException{
			String pathInfo = req.getPathInfo(); 
			String action = req.getParameter("action");
			//if there is an item name then delete it
			//else, if there is no item name, delete the whole database
			if(action.equals("deleteUser")){
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
				List<User> userList = responseController.getUserList();
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
	 			JSON.getObjectMapper().writeValue(resp.getWriter(), userList);
			}
			if(action.equals("deleteUserList")){
				
				//delete the entire list
				DeleteUserList controller = new DeleteUserList();
				controller.deleteUserList();
				resp.getWriter().println("User List Deleted.");
			}
		}
}
