package threads;

import java.util.*;
import chore.Chore;

public class UserThread extends Thread {
	
	private Chore chore;
	private String state = "";
	private LinkedList<Chore> completed_chores = new LinkedList<Chore>();
	
	public UserThread(Chore chore) {
		this.chore = chore;
	}
	
	public void run() {
		while (true) {
			state = chore.getState();
			if (state.equals("Complete")) {
				completed_chores.add(chore);
				
				break;
			}
		}
	}
	
}
