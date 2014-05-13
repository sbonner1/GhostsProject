package edu.ycp.cs496.ghosts.controller;

import edu.ycp.cs496.ghosts.model.User;
import edu.ycp.cs496.ghosts.model.persist.DatabaseProvider;
import edu.ycp.cs496.ghosts.model.persist.IDatabase;

public class UpdateUserScore {
		public User updateUserScore(User user, int score) {
			
			IDatabase db = DatabaseProvider.getInstance();
			return db.updateUserScore(user, score);
		}
}
