package edu.ycp.cs496.ghosts.model.persist;

import edu.ycp.cs496.ghosts.model.User;

public interface IDatabase {
	public User findUser(String userName, String password);
}
