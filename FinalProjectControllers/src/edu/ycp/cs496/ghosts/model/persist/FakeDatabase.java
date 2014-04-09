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
		
		userList.add(new User("Shane", "sbonner"));
		userList.add(new User("Josh", "jcoady"));
		userList.add(new User("Chris", "cflinch"));
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
	
	@Override
	public void deleteUserList(){
		userList = null;
	}
	
	
	
	@Override
	public void replaceUser(String oldUserName, User newUser) {
		//check to see if the inventory contains the old item
		for(User user : userList){
			//if the old item is in the inventory, then replace it
			if(user.getUserName().equals(oldUserName)){
				user.setUserName(newUser.getUserName());
				user.setUserPassword(newUser.getUserPassword());
				break;
			}
		}
	}
	

}
