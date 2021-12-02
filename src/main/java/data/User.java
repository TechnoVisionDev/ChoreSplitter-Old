package data;

import java.util.*;

/**
 * Represents a authenticated user with an email and password.
 * @author Thomas Peters
 */
public class User {
	
	// need a variable to keep track of group? 

	private String email;
	private String name;
	private String password;
	private int points;
	
	private LinkedList<chore.Chore> chore_list;
	
	public User(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
		points = 0;
		chore_list = new LinkedList<chore.Chore>();
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
	
	public void addChore(chore.Chore c) {
		chore_list.add(c);
	}
	
	public LinkedList<chore.Chore> get_all_chores() {
		return chore_list;
	}
	
	public LinkedList<chore.Chore> get_incomplete_chores() {
		LinkedList<chore.Chore> incomplete_list = new LinkedList<chore.Chore>();
		for( chore.Chore c : chore_list ) {
			if( c.getState() == "Incomplete" ) {
				incomplete_list.add(c);
			}
		}
		return incomplete_list;
	}
	
	public LinkedList<chore.Chore> get_inprogress_chores() {
		LinkedList<chore.Chore> inprogress_list = new LinkedList<chore.Chore>();
		for( chore.Chore c : chore_list ) {
			if( c.getState() == "In-Progress") {
				inprogress_list.add(c);
			}
		}
		return inprogress_list;
	}
	
	public LinkedList<chore.Chore> get_complete_chores() {
		LinkedList<chore.Chore> complete_list = new LinkedList<chore.Chore>();
		for( chore.Chore c : chore_list ) {
			if( c.getState() == "Complete" ) {
				complete_list.add(c);
			}
		}
		return complete_list;
	}
}
