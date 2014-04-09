package edu.ycp.cs496.ghosts.controller;

import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;



public class DeleteUser {
	public void deleteUser(String itemName) {
		IDatabase db = DatabaseProvider.getInstance();
		db.deleteUser(itemName);
	}
}
