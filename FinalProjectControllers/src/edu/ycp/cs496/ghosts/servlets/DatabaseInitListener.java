package edu.ycp.cs496.ghosts.servlets;
/**
 * author @daveho
 * 
 * class used to initialize the database 
 * 
 */
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.FakeDatabase;

public class DatabaseInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent e) {
		// Webapp is starting
		DatabaseProvider.setInstance(new FakeDatabase()); // TODO use real database
		System.out.println("Initialized database!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// webapp is shutting down
		
	}

}
