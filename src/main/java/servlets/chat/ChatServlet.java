package servlets.chat;

import java.io.IOException;

import org.bson.Document;
import data.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles group chat data.
 * @author Thomas Peters
 */
@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

	private static final long serialVersionUID = 3500300307566513848L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if user is in a group before proceeding.
		String group = (String) request.getSession(false).getAttribute("group");
		if (group == null) {
			request.getRequestDispatcher("/group.jsp").forward(request, response); 
		}

		// Get user and avatar
		Database db = (Database) request.getServletContext().getAttribute("database");
		String email = (String) request.getSession(false).getAttribute("email");
		Document user = db.getUser(email);
		
		// Send data to chat.jsp
		request.setAttribute("avatar", user.getString("avatar"));
		request.getRequestDispatcher("/chat.jsp").forward(request, response); 
	}
}
