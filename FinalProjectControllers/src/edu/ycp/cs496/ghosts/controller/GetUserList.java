package edu.ycp.cs496.ghosts.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;
/**
 * 
 * @author shane
 * Controller to return the user list from the database
 *
 */
public class GetUserList {
	public List<User> getUserList() {
		IDatabase db = DatabaseProvider.getInstance();
		return db.getUserList();
	}
}
