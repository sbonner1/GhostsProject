package edu.ycp.cs496.ghosts.controller;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
/**
 * 
 * @author shane
 * controller to assist with logging in a user if they already exist in the database
 *
 */
public class LoginController {
	public User findUser(String userName, String password) {
		return DatabaseProvider.getInstance().getUser(userName, password);
	}
}

	