package timers;

import java.util.*;
import java.util.concurrent.*;

import data.Database;

/**
 * Executes ResetChore runnable every Sunday at 5 AM.
 * @author Emily Kim
 */
public class WeeklyResetTimer {
	
	/**
	 * Set to execute run() from ResetTask every Sunday at 5 am in order to reset chores.
	 * @param db MongoDB database instance.
	 */
	public void start(Database db) {
		
	    Calendar cal = Calendar.getInstance();
		
	    // HashMap to keep track of how much to delay task based on current time
	    Map<Integer, Integer> dayToDelay = new HashMap<Integer, Integer>();
	    
	    dayToDelay.put(Calendar.SUNDAY, 6);
	    dayToDelay.put(Calendar.MONDAY, 5);
	    dayToDelay.put(Calendar.TUESDAY, 4);
	    dayToDelay.put(Calendar.WEDNESDAY, 3);
	    dayToDelay.put(Calendar.THURSDAY, 2);
	    dayToDelay.put(Calendar.FRIDAY, 1);
	    dayToDelay.put(Calendar.SATURDAY, 0);
	    
	    // get current time and date
	    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	    int hour = cal.get(Calendar.HOUR_OF_DAY);
	    int delayInDays = dayToDelay.get(dayOfWeek);
	    int delayInHours = 0;
	    
	    // calculate how long to delay based on current time and date
	    if( delayInDays == 6 && hour < 5 ){
	    	delayInHours = 5 - hour;
	    }
	    else {
	    	delayInHours = delayInDays * 24 + ((24 - hour) + 5);
	    }
	    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	    
	    // Execute after calculated delay, but also
	    // 168 hours in a week (the period) after which it should execute again
	    scheduler.scheduleAtFixedRate(new ResetChore(db), delayInHours, 168, TimeUnit.HOURS);
	}
}