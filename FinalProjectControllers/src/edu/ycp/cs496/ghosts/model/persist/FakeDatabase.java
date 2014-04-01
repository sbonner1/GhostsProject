package edu.ycp.cs496.ghosts.model.persist;
import java.util.ArrayList;

/**
 * @author sbonner1
 * 
 * 
 * 
 */
import edu.ycp.cs496.ghosts.model.User;

/**
 * 
 * 
 * @author sbonner1
 *
 */
public class FakeDatabase implements IDatabase {
	
	private ArrayList<User> userList;
	
	public FakeDatabase() {
		userList = new ArrayList<User>();
	}
	
	@Override
	public User getUser(String userName, String password) {
		for(User user: userList){
			if(user.getUserName() == userName && user.getUserPassword() == password){
				return user;
			}
		}
		return null;	
	}
	
	

}
