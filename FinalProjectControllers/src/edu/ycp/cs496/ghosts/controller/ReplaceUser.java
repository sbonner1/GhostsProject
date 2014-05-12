package edu.ycp.cs496.ghosts.controller;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;
/**
 * 
 * @author shane
 * controller to be used if a user wants to change their username
 *
 */


public class ReplaceUser {
	public void replaceUser(String oldUser, User newUser) {
		IDatabase db = DatabaseProvider.getInstance();
		db.replaceUser(oldUser, newUser);
	}
}
