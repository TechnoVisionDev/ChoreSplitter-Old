package servlets.chores;

import java.io.IOException;

import data.Database;
import data.serializables.Chore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles dashboard chores for group
 * @author Thomas Peters
 */
@WebServlet("/chore")
public class ChoreServlet extends HttpServlet {
	
	private static final long serialVersionUID = 5353638521656850770L;

	/**
	 * Adds a chore to the group dashboard.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String name = request.getParameter("chore-name");
			String desc = request.getParameter("chore-description");
			int points = Integer.parseInt(request.getParameter("chore-points"));
			int time = Integer.parseInt(request.getParameter("chore-time"));
			
			if (name == null || name.isBlank() || desc == null || desc.isBlank()) { throw new Exception(); }
			if (points < 1 || time < 1 || points > 100 || time > 365) { throw new Exception(); }
			
			Chore chore = new Chore(name, desc, points, time);
			Database db = (Database) request.getServletContext().getAttribute("database");
			String group = (String) request.getSession(false).getAttribute("group");
			
			db.addChore(group, chore);
			response.sendRedirect(request.getContextPath()+"/dashboard");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("choreError", "true");
			request.getRequestDispatcher("/addChore.jsp").forward(request, response); 
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = (Database) request.getServletContext().getAttribute("database");
		String group = (String) request.getSession(false).getAttribute("group");
		String email = (String) request.getSession(false).getAttribute("email");
		
		if (null != request.getParameter("claim")) {
			int index = Integer.parseInt(request.getParameter("claim"));
			db.claimChore(email, group, index);
		} else if (null != request.getParameter("finish")) {
			int index = Integer.parseInt(request.getParameter("finish"));
			db.completeChore(group, index);
		} else if (null != request.getParameter("delete")) {
			int index = Integer.parseInt(request.getParameter("delete"));
			db.deleteChore(group, index);
		} else if (null != request.getParameter("unclaim")) {
			int index = Integer.parseInt(request.getParameter("unclaim"));
			db.unclaimChore(email, group, index);
		}
		
		response.sendRedirect(request.getContextPath() + "/dashboard");
	}
}
