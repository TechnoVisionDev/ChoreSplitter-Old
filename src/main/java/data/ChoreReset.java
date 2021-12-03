package data;

import java.util.*;
import java.util.concurrent.*;

public class ChoreReset {

	public ChoreReset(){
	    this.startScheduler();
	}
	
	/*
	 * Set to execute run() from ResetTask every Sunday at 5 am
	 * in order to reset reset leaderboard
	 */
	private void startScheduler() {
		
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
	    
	    // get current time and date to calculate how long to delay
	    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	    int hour = cal.get(Calendar.HOUR_OF_DAY);
	    int delayInDays = dayToDelay.get(dayOfWeek);
	    int delayInHours = 0;
	    
	    // it is Sunday, but before 5 am
	    if( delayInDays == 6 && hour < 5 ){
	    	delayInHours = 5 - hour;
	    }
	    else {
	    	delayInHours = delayInDays * 24 + ((24 - hour) + 5);
	    }
	    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	    
	    // 168 hours in a week (the period) after which it should execute again
	    scheduler.scheduleAtFixedRate(new ResetTask(), delayInHours, 168, TimeUnit.HOURS);
	}
}