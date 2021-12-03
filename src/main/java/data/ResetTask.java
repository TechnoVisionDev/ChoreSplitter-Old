package data;

public class ResetTask implements Runnable {

	@Override
	public void run() {
		// the actual part that resets chores in the database 
		System.out.println("reset");
	}

}