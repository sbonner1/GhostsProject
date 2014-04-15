package ycp.edu.cs496project.mobileApp.model;
/**
 * @author sbonner1
 *
 * * This class is the basic model construct for the user. 
 */
public class User {
	private String userName;
	private String passwordHash;
	private String password;
	
	private int score;
	
	public User(String userName, String password) {
		//access database to pull name/password info
		score = 0;
		
	}
	//getter and setter methods for this class
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserPassword(String password){
		this.password = password;
	}
	
	public String getUserPassword(){
		return password;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setUserScore(int score){
		this.score = score;
	}
	
	public int getUserScore(){
		return score;
	}
}
