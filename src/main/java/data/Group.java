package data;

import java.util.*;

class Sort implements Comparator<User> {
	
	public int compare(User a, User b) {
		return a.getPoints() - b.getPoints();
	}
}

public class Group {
	public TreeMap<User, Integer> group;
	
	public String code;
	
	public Group(String code) {
		this.code = code;
		group = new TreeMap<User, Integer>(new Sort());
	}
	
	public void setCode(String c) {
		code = c;
	}
	
	public String getCode() {
		return code;
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
}