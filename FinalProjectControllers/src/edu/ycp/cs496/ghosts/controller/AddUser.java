package edu.ycp.cs496.ghosts.controller;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;

/**
 * 
 * @author shane
 * controller to assist with adding a new user when they register to play the game
 *
 */

public class AddUser {
	public boolean addNewUser(User user, String password) {
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		
		IDatabase db = DatabaseProvider.getInstance();
		return db.addNewUser(user, hashedPassword);
		
	}
}
