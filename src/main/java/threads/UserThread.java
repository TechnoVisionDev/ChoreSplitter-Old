package threads;

import java.util.*;
import chore.Chore;
import data.User;

public class UserThread extends Thread {
	
	private ArrayList<User> userList = new ArrayList<User>();
	private User user;
	private Chore chore;
	private String state = "";
	private LinkedList<Chore> completed_chores = new LinkedList<Chore>();
	
	public UserThread(User _user, Chore _chore) {
		this.user = _user;
		this.chore = _chore;
		userList.add(_user);
		this.start();
	}
	
	public void run() {
		while (true) {
			state = chore.getState();
			if (state.equals("Complete")) {
				completed_chores.add(chore);
				user.addPoints(chore.getPoints());

				break;
			}
		}
	}
	
}
