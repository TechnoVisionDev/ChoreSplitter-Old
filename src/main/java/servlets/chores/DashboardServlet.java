package servlets.chores;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import data.Database;
import data.serializables.Chore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
		// Check that user is authenticated before proceeding.
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(request.getContextPath()+"/landing.jsp");
			return;
		}
		
		// Check if user is in a group before proceeding.
		String group = (String) session.getAttribute("group");
		if (group == null) {
			request.getRequestDispatcher("/group.jsp").forward(request, response); 
			return;
		}

		// Get user data
		Database db = (Database) request.getServletContext().getAttribute("database");
		String email = (String) session.getAttribute("email");
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
		
		// Get chore list and avatars
		List<Chore> chores = db.getChores(group);
		Map<String, String> avatars = new HashMap<String, String>();
		if (chores != null) {
			for (Chore chore : chores) {
				String claimEmail = chore.getClaimed();
				if (!claimEmail.isBlank()) {
					if (!avatars.containsKey(claimEmail)) {
						String avatar = db.getUserValue(claimEmail, "avatar");
						avatars.put(claimEmail, avatar);
					}
				}
			}
		}
	
		// Send data to dashboard.jsp
		request.setAttribute("data", chores);
		request.setAttribute("avatars", avatars);
		request.getRequestDispatcher("/dashboard.jsp").forward(request, response); 
	}
}
