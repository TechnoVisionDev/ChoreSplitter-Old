package chore;


import  java.util.*;
import data.User;

public class Leaderboard {
	
	private SortedMap<User, Integer> ranking = new TreeMap<User, Integer>();
	private ArrayList<User> userList = new ArrayList<User>();
	
	public Leaderboard(ArrayList<User> _userList) {
		this.userList = _userList;
		for (User u : userList) {
			ranking.put(u, u.getPoints());
		}
	}
}
