package servlets;

import java.io.IOException;

import data.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Deletes a chore for a group.
 * @author Thomas Peters
 */
@WebServlet("/deleteChore")
public class deleteChore extends HttpServlet {
	
	private static final long serialVersionUID = -6115731632062113819L;

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
