package edu.ycp.cs496.ghosts.controller;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;
/**
 * 
 * @author shane
 * controller to assist with logging in a user if they already exist in the database
 *
 */
public class LoginController {
	public User loginUser(String userName, String password) {
		GetUserController controller = new GetUserController();
		User tempUser = controller.getUser(userName);
		String tempPassword = tempUser.getUserPassword();
		
		if(BCrypt.checkpw(password, tempPassword)){
			IDatabase db = DatabaseProvider.getInstance();
			return db.loginUser(userName, password);
		}
		
		return null;
		
		//return DatabaseProvider.getInstance().getUser(userName, password);
	}
}

	