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
	
	//public User getUser(String userName, String password);

	void replaceUser(String oldUserName, User newUser);

	void deleteUserList();

	void deleteUser(String userName);

	List<User> getUserList();

	void replaceUserList(List<User> newUserList);

	//public void addNewUser(User user, String password);
	/*
	 * Add a new user.
	 * 
	 * @param user            the user to add
	 * @param hashedPassword  the hashed password
	 * @return true if successful, false if a user with the same name already exists
	 */
	public boolean addNewUser(User user, String hashedPassword);
	
	/**
	 * Given a username, find the User object.
	 * 
	 * @param userName the username
	 * @return the User, or null if there is no such user
	 */
	public User findUser(String userName);

	public User updateUserScore(User user, int score);

	public User loginUser(String userName, String password);

	User getUser(String userName, String password);
	
}
