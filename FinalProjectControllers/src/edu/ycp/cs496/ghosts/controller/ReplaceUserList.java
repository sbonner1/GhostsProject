package edu.ycp.cs496.ghosts.controller;

import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;



public class ReplaceUserList {
	public void replaceUserList(ArrayList<User> newUserList) {
		IDatabase db = DatabaseProvider.getInstance();
		db.replaceUserList(newUserList);
	}
}
