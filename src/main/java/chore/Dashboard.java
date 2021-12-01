package chore;
import java.util.*;

public class Dashboard {
	
	private LinkedList<Chore> all_chores = new LinkedList<Chore>();
	
	public Dashboard() {
		
	}
	
	
	public void addChore(Chore chore) {
		all_chores.add(chore);
	}
	
	public void deleteChore(Chore chore) {
		all_chores.remove(chore);
	}
	
	public void claimChore(Chore chore) {
		int index = all_chores.indexOf(chore);
		all_chores.get(index).setState("In-progress");
	}
	
	public void completedChore(Chore chore) {
		int index = all_chores.indexOf(chore);
		all_chores.get(index).setState("Complete");
	}
}
