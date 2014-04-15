package edu.ycp.cs496.ghosts.controller;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;



public class AddUser {
	public void addNewUser(User user, String password) {
		IDatabase db = DatabaseProvider.getInstance();
		db.addNewUser(user, password);
		
	}
}
