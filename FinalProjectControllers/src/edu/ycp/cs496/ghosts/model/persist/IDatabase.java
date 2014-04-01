package edu.ycp.cs496.ghosts.model.persist;

/**
 * @author sbonner1
 * 
 * 
 */
import edu.ycp.cs496.ghosts.model.User;

public interface IDatabase {
	public User getUser(String userName, String password);
	
	
}
