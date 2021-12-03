package servlets;

import java.io.IOException;

import data.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Claims a chore for a user
 * @author Thomas Peters
 */
@WebServlet("/claimChore")
public class ClaimChore extends HttpServlet {
	
	private static final long serialVersionUID = -6753822670854720666L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int index = Integer.parseInt(request.getParameter("index"));
			Database db = (Database) request.getServletContext().getAttribute("database");
			String group = (String) request.getSession(false).getAttribute("group");

		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/dashboard").forward(request, response); 
	}
}
