package data;

import java.util.*;

/**
 * Comparator to organize TreeMap in
 * descending order.
 */

class Sort implements Comparator<User> {
	
	public int compare(User a, User b) {
		return a.getPoints() - b.getPoints();
	}
}

public class Group {
	public TreeMap<User, Integer> group;
	
	public chore.Dashboard chore_list = new chore.Dashboard();
	
	public String group_code;
	public String group_name;
	
	public Group(String group_code, String group_name) {
		this.group_code = group_code;
		this.group_name = group_name;
		group = new TreeMap<User, Integer>(new Sort());
	}
	
	public void setCode(String c) {
		group_code = c;
	}
	
	public String getCode() {
		return group_code;
	}
	
	public void setName(String n) {
		group_name = n;
	}
	
	public void addUser(User u) {
		group.put(u, u.getPoints());
	}
	
	public TreeMap<User, Integer> getBoard() {
		return group;
	}
	
	public User getLowest() {
		return group.lastKey();
	}
	
	public chore.Dashboard getDashboard() {
		return chore_list;
	}
}