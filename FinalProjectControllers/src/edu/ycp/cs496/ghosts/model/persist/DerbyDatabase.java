package edu.ycp.cs496.ghosts.model.persist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ycp.cs496.ghosts.model.User;

public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	private static final String DB_DIRECTORY = "C:/cs496/ghosts.db";
	private static final String DB_TABLENAME = "userList";
	
	//return the user that the client is searching for
	@Override
	public User getUser(final String userName, final String password) { 
		return executeTransaction(new Transaction<User>(){

			@Override
			public User execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
		
				try {
					stmt = conn.prepareStatement("select * from " + DB_TABLENAME +" where userName = ?");
					stmt.setString(1, userName);
					//stmt.setString(2, password);
					
					resultSet = stmt.executeQuery();
					
					if(!resultSet.next()){
						//no such user
						return null;
					}
					
					User user = new User(userName, password);
					loadUser(user, resultSet, 1);
					return user;
				}finally{
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
				
			}
			
		});
	}
	//if a user wants to change their username, use this method
	@Override
	public void replaceUser(final String oldUserName, final User newUser) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = conn.prepareStatement("update " + DB_TABLENAME + " set userName = ? where userName = ?");
				stmt.setString(1, newUser.getUserName());
				stmt.setString(2, oldUserName);
				stmt.executeUpdate();
				return true;
			}
		});
	}
	
	@Override
	public User loginUser(final String userName, final String password) {
		return executeTransaction(new Transaction<User>(){

			@Override
			public User execute(Connection conn) throws SQLException {

				PreparedStatement stmt = null;
				ResultSet resultSet = null;
		
				try {
					stmt = conn.prepareStatement("select * from " + DB_TABLENAME +" where userName = ?");
					stmt.setString(1, userName);
					//stmt.setString(2, password);
					
					resultSet = stmt.executeQuery();
					
					if(!resultSet.next()){
						//no such user
						return null;
					}
					
					User user = new User(userName, password);
					//user.setUserName(userName);
					//user.setUserPassword(password);
					loadUser(user, resultSet, 1);
					return user;
				}finally{
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
				
			}
		});

		
	}
	
	//clear the database of all existing users
	@Override
	public void deleteUserList() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = conn.prepareStatement("delete from " + DB_TABLENAME);
				stmt.executeUpdate();
				return true;
			}
		});

	}
	
	//used to update the user's score in the database
	@Override
	public void updateUserScore(final User user){
		executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = conn.prepareStatement("update " + DB_TABLENAME + " set score = ? where score = ?");
				stmt.setString(1, user.getUserName());
				stmt.setInt(3, user.getUserScore());
				stmt.executeUpdate();
				return true;
			}

		});
	}
	
	//remove the user from the database
	@Override
	public void deleteUser(final String userName) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = conn.prepareStatement("delete from " + DB_TABLENAME + " where userName = ?");
				stmt.setString(1, userName);
				stmt.executeUpdate();
				return true;
			}
		});
	}
	//used for high scores/printing the entire list of users
	@Override
	public List<User> getUserList() {
		return executeTransaction(new Transaction<List<User>>() {

			@Override
			public List<User> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try{
					stmt = conn.prepareStatement("select * from " + DB_TABLENAME);
					resultSet = stmt.executeQuery();
					
					List<User> result = new ArrayList<User>();
					while (resultSet.next()) {
						User user = new User();
						loadUser(user, resultSet, 1);
						result.add(user);
					}
					return result;
				}finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
			
		});
	}

	@Override
	public void replaceUserList(List<User> newUserList) {
		//unused method
	}

	@Override
	public boolean addNewUser(final User user, final String hashedPassword) {
		return executeTransaction(new Transaction<Boolean>(){

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet generatedKeys = null;
				
				try {
					stmt = conn.prepareStatement(
							"insert into " + DB_TABLENAME + " (userName, password) values (?, ?)",
							//"insert into " + DB_TABLENAME + " (userName, password, highScore) values (?, ?, 0)"
							PreparedStatement.RETURN_GENERATED_KEYS
					);
					
					storeUserNoId(user, stmt, 1);
					
					//attempt to insert the user
					stmt.executeUpdate();
					
					//Determine the auto-generated id
					generatedKeys = stmt.getGeneratedKeys();
					if(!generatedKeys.next()) {
						throw new SQLException("Could not get auto-generated key for inserted User");
					}
					
//					user.setId(generatedKeys.getInt(1));
//					System.out.println("New user has id " + user.getId());
					
					return true;
				}finally {
					DBUtil.closeQuietly(generatedKeys);
					DBUtil.closeQuietly(stmt);
				}
				
			}
			
		});
	}

	@Override
	public User findUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}

	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:" + DB_DIRECTORY + ";create=true");
		
		// Set autocommit to false to allow multiple the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	//this is the main part of the database. the tables store all the data
	//that the client side needs
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try{
					stmt = conn.prepareStatement(
						"create table " + DB_TABLENAME + " (" + 
						" id integer primary key not null generated always as identity," +
						" userName varchar(80) unique," +
						" password varchar(80)," +
						" score integer" +
						
					/*	" integer high score" +
						" integer max red chain" +
						" integer max yellow chain" +
						" integer max green chain" + 
					*/

						")"
					);
					
					stmt.executeUpdate();
				
					return true;
				
				}finally {
					DBUtil.closeQuietly(stmt);
				}
				
			}
			
		});
	}
	
	protected void storeUserNoId(User user, PreparedStatement stmt, int index) throws SQLException {
		// Note that we are assuming that the User does not have a valid id,
		// and so are not attempting to store the (invalid) id.
		// This is the preferred approach when inserting a new row into
		// a table in which a unique id is automatically generated.
		stmt.setString(index++, user.getUserName());
		stmt.setString(index++, user.getUserPassword());
	}
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {

			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try{
					stmt = conn.prepareStatement("insert into " + DB_TABLENAME + " (userName, password) values (?,?)");
					storeUserNoId(new User("testUser", "password"), stmt, 1);
					stmt.addBatch();
					
					stmt.executeBatch();
					
					return true;
				}finally{
					DBUtil.closeQuietly(stmt);
				}
				
			}
			
		});
	}
	protected void loadUser(User user, ResultSet resultSet, int index) throws SQLException {
		user.setId(resultSet.getInt(index++));
		user.setUserName(resultSet.getString(index++));
		user.setUserPassword(resultSet.getString(index++));
	}
	public static void main(String[] args) {
		DerbyDatabase db = new DerbyDatabase();
		System.out.println("Creating tables...");
		db.createTables();
		System.out.println("Loading initial data...");
		db.loadInitialData();
		System.out.println("Done!");
	}

}
