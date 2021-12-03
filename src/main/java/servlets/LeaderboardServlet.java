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
 * Handles leaderboard for each group.
 * @author Thomas Peters
 */
@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {

	private static final long serialVersionUID = 5353638521656850770L;

	/**
	 * Displays visual leaderboard data.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if user is in a group before proceeding.
		String group = (String) request.getSession(false).getAttribute("group");
		if (group == null) {
			request.getRequestDispatcher("/group.jsp").forward(request, response); 
		}
		
		request.getRequestDispatcher("/leaderboard.jsp").forward(request, response); 
	}
}
