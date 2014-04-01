package edu.ycp.cs496.ghosts.controller;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;

public class LoginController {
	public User findUser(String userName, String password) {
		return DatabaseProvider.getInstance().getUser(userName, password);
	}
}
