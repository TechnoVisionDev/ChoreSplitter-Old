package data;

import java.util.*;

class Sort implements Comparator<User> {
	
	public int compare(User a, User b) {
		return a.getPoints() - b.getPoints();
	}
}

public class Leaderboard {
	
	public TreeMap<User, Integer> board;
	
	public Leaderboard() {
		board = new TreeMap<User, Integer>(new Sort());
	}
	
	public void addUser(User u) {
		board.put(u, u.getPoints());
	}
	
	public TreeMap<User, Integer> getBoard() {
		return board;
	}
	
	public User getLowest() {
		return board.lastKey();
	}
	
}

