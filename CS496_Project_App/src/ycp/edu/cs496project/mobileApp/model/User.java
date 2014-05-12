package ycp.edu.cs496project.mobileApp.model;

//import ycp.edu.cs496project.mobileApp.model.User;

/**
* @author sbonner1
*
* * This class is the basic model construct for the user. 
*/


public class User implements Cloneable {
	private String userName;
	//private String passwordHash;
	private String password;
	private int id;
	private int score;
	private int highScore;
	private int redChain;
	private int yellowChain;
	private int greenChain;
	
	public User() {
		this.userName = "";
		this.password = "";
		this.highScore = 0;
		this.redChain = 0;
		this.yellowChain = 0;
		this.greenChain = 0;
	}

	
	public User(String userName, String password){//, int highScore, int redChain, int yellowChain, int greenChain) {
		//access database to pull name/password info
		this.userName = userName;
		this.password = password;
		
		//this.highScore = 0;
		//this.redChain = 0;
		//this.yellowChain = 0;
		//this.greenChain = 0;
		
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
	//set the id for the database
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	/*public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	*/
	
	public void setUserScore(int score){
		this.score = score;
	}
	
	public int getUserScore(){
		return score;
	}
	
	@Override
	public User clone() {
		try {
			User dup = (User) super.clone();
			return dup;
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException("This can't happen", e);
		}
	}
}
