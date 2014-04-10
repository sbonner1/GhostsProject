package edu.ycp.cs496.ghosts.model.persist;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs496.ghosts.model.User;


/**
 * @author sbonner1
 * 
 * Implementation of the {@link IDatabase} interface that stores
 * objects using in-memory data structures.  It doesn't
 * provide actual persistence, but is useful as a proof
 * of concept.
 * 
 */



public class FakeDatabase implements IDatabase {
	
	private ArrayList<User> userList;
	
	public FakeDatabase() {
		userList = new ArrayList<User>();
		
		//Preliminary add to the user list 
		userList.add(new User("Shane", "sbonner"));
		userList.add(new User("Josh", "jcoady"));
		userList.add(new User("Chris", "cflinch"));
	}
	
	@Override
	public User getUser(String userName, String Password) {
		for(User user: userList){
			if(user.getUserName() == userName){// && user.getUserPassword() == password){
				return user;
			}
		}
		return null;	
	}
	
	@Override
	public void addNewUser(User user, String password) {
		// TODO Auto-generated method stub
		userList.add(user);
		user.setUserPassword(password);
	}
	
	@Override
	public void deleteUser(String userName) {
		//check that item is in inventory
		for(User user : userList){
			//if item is present, delete it
			if(user.getUserName().equals(userName)){
				userList.remove(user);
				System.out.println(user.getUserName() + " deleted.");
				break;
			}
		}
	}
	
	@Override
	public ArrayList<User> getUserList() {
		// return a copy
		return new ArrayList<User>(userList);
	}
	
	@Override
	public void deleteUserList(){
		userList = null;
	}
	
	@Override
	public void replaceUserList(ArrayList<User> newUserList) {
		userList = newUserList;
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
