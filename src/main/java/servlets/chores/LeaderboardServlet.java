package servlets.chores;

import java.io.IOException;
import java.util.List;

import data.Database;
import data.serializables.User;
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
			return;
		}
		
		// Sort group by points as send to leaderboard.jsp
		Database db = (Database) request.getServletContext().getAttribute("database");
		List<User> users = db.getGroup(group);
		request.setAttribute("data", users);
		request.setAttribute("shame", users.get(users.size()-1));
		request.getRequestDispatcher("/leaderboard.jsp").forward(request, response); 
	}
}
