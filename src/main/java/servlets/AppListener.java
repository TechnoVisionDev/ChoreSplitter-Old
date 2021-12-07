package servlets;

import data.Database;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import timers.WeeklyResetTimer;

/**
 * ChoreSplitter main application listener for registering global objects.
 * @Author Thomas Peters
 */
@WebListener("/app")
public class AppListener implements ServletContextListener {
	
	private WeeklyResetTimer resetTimer;
	
    public void contextInitialized(ServletContextEvent event) {
    	// Application is being deployed
    	// Register "global" objects here
    	ServletContext sc = event.getServletContext();
    	Database db = new Database();
    	sc.setAttribute("database", db);
    	
    	// Start weekly reset timer 
    	resetTimer = new WeeklyResetTimer();
    	resetTimer.start(db);
    }
    
    public void contextDestroyed(ServletContextEvent event) {
    	// Application is being undeployed
    	resetTimer.stop();
    }
}
