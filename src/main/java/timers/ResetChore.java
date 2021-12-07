package timers;

import data.Database;

/**
 * Deletes all chores on the group dashboards.
 * @author Thomas Peters
 */
public class ResetChore implements Runnable {
	
	private Database db;
	
	public ResetChore(Database db) {
		this.db = db;
	}

	@Override
	public void run() {
		db.resetDashboards();
	}

}