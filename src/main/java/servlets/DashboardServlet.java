package servlets;

import java.io.IOException;

import org.bson.Document;

import data.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles dashboard chores for group
 * @author Thomas Peters
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 5353638521656850770L;

	/**
	 * Displays chores to dashboard from database
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if user is in a group before proceeding.
		String group = (String) request.getSession(false).getAttribute("group");
		if (group == null) {
			request.getRequestDispatcher("/group.jsp").forward(request, response); 
		}
		
		// Get user and chore data
		Database db = (Database) request.getServletContext().getAttribute("database");
		String email = (String) request.getSession(false).getAttribute("email");
		Document user = db.getUser(email);
		if (user != null) {
			request.setAttribute("name", user.getString("name"));
			request.setAttribute("points", user.getInteger("points"));
			request.setAttribute("avatar", user.getString("avatar"));
		} else {
			request.setAttribute("name", "Username");
			request.setAttribute("points", 0);
			request.setAttribute("avatar", "https://i.stack.imgur.com/34AD2.jpg");
		}
		
		// Send data to dashboard.jsp
		request.setAttribute("data", db.getChores(group));
		request.getRequestDispatcher("/dashboard.jsp").forward(request, response); 
	}
}
