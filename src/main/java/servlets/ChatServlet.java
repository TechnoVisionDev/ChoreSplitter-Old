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
 * Handles group chat data
 * @author Thomas Peters
 */
@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

	private static final long serialVersionUID = 5353638521656850770L;

	/**
	 * Displays group chat previous messages
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if user is in a group before proceeding.
		String group = (String) request.getSession(false).getAttribute("group");
		if (group == null) {
			request.getRequestDispatcher("/group.jsp").forward(request, response); 
		}
		
		request.getRequestDispatcher("/chat.jsp").forward(request, response); 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if user is in a group before proceeding.
		String group = (String) request.getSession(false).getAttribute("group");
		if (group == null) {
			request.getRequestDispatcher("/group.jsp").forward(request, response); 
		}
		
		request.getRequestDispatcher("/chat.jsp").forward(request, response); 
	}
}
