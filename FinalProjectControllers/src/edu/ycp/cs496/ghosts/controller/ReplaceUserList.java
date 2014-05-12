package edu.ycp.cs496.ghosts.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;

/**
 * 
 * @author shane
 * Unused method, but for unusual cases where we must replace the entire userList,
 * this controller would be used.
 *
 */

public class ReplaceUserList {
	public void replaceUserList(List<User> newUserList) {
		IDatabase db = DatabaseProvider.getInstance();
		db.replaceUserList(newUserList);
	}
}
