package edu.ycp.cs496.ghosts.controller;

import java.sql.SQLException;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;
import edu.ycp.cs496.ghosts.model.persist.PersistenceException;

/**
 * 
 * @author shane
 * controller to assist with adding a new user when they register to play the game
 * Makes use of the application BCrypt to encrypt passwords
 *
 */

public class AddUser 
{
	public boolean addNewUser(User user, String password) 
	{
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		
		IDatabase db = DatabaseProvider.getInstance();
		try 
		{
			return db.addNewUser(user, hashedPassword);
		} catch (PersistenceException e) 
		{
			if (e.getCause() instanceof SQLException) 
			{
				SQLException sqlEx = (SQLException) e.getCause();
				// TODO: check whether it's a uniqueness constraint violation
				System.out.println("SQLState is " + sqlEx.getSQLState());
				return false;
			}
			
			throw e;
		}
	}
}
