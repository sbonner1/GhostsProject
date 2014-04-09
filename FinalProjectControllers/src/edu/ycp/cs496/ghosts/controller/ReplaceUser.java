package edu.ycp.cs496.ghosts.controller;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;



public class ReplaceUser {
	public void replaceUser(String oldItem, User newUser) {
		IDatabase db = DatabaseProvider.getInstance();
		db.replaceUser(oldItem, newUser);
	}
}
