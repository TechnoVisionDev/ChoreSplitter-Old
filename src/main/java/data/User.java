package data;

/**
 * Represents a authenticated user with an email and password.
 * @author Thomas Peters
 */
public class User {

	private String email;
	private String name;
	private String password;
	private int points;
	private String avatar;
	
	public User(String email, String name, String password, String avatar) {
		this.email = email;
		this.name = name;
		this.password = password;
		points = 0;
		this.avatar= avatar;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void addPoints(int p) {
		points += p;
	}
	
	public String getAvatar() {
		return avatar;
	}
}
