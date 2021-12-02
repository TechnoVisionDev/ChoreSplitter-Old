package servlets;

import java.io.IOException;

import data.Chore;
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
		Database db = (Database) request.getServletContext().getAttribute("database");
		String group = (String) request.getSession(false).getAttribute("group");
		
		request.setAttribute("data", db.getChores(group));
		request.getRequestDispatcher("/dashboard.jsp").forward(request, response); 
	}
}
