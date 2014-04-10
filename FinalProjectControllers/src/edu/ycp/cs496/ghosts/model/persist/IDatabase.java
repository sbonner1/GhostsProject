package edu.ycp.cs496.ghosts.model.persist;

import java.util.ArrayList;
import java.util.List;





/**
 * @author sbonner1
 * Class to hold the methods for the IDatabase that the FakeDatabase, and later real database,
 * will use.
 * 
 */
import edu.ycp.cs496.ghosts.model.User;

public interface IDatabase {
	
	public User getUser(String userName, String password);

	void replaceUser(String oldUserName, User newUser);

	void deleteUserList();

	void deleteUser(String userName);

	ArrayList<User> getUserList();

	void replaceUserList(ArrayList<User> newUserList);

	public void addNewUser(User user, String password);
	
	
}
