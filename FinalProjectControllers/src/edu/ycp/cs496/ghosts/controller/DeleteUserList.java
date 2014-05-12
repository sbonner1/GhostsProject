package edu.ycp.cs496.ghosts.controller;

import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;
/**
 * 
 * @author shane
 * controller to delete the userList from the database
 *
 */


public class DeleteUserList {
	public void deleteUserList() {
		IDatabase db = DatabaseProvider.getInstance();
		db.deleteUserList();
	}
}
