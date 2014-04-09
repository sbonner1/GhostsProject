package edu.ycp.cs496.ghosts.controller;

import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;



public class DeleteUserList {
	public void deleteUserList() {
		IDatabase db = DatabaseProvider.getInstance();
		db.deleteUserList();
	}
}
