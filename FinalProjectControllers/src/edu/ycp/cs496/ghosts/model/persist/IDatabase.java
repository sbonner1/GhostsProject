package edu.ycp.cs496.ghosts.model.persist;

/**
 * @author sbonner1
 * Class to hold the methods for the IDatabase that the FakeDatabase, and later real database,
 * will use.
 * 
 */
import edu.ycp.cs496.ghosts.model.User;

public interface IDatabase {
	
	public User getUser(String userName, String password);
	
	
}
