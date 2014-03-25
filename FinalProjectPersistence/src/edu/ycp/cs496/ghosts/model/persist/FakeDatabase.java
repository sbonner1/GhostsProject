package edu.ycp.cs496.ghosts.model.persist;

import edu.ycp.cs496.ghosts.model.User;

public class FakeDatabase implements IDatabase {

	@Override
	public User findUser(String userName, String password) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
