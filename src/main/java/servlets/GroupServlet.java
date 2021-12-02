package servlets;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import data.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles joining and creating housing groups.
 * @author Thomas Peters
 */
@WebServlet("/group")
public class GroupServlet extends HttpServlet {
	
	private static final long serialVersionUID = 112116802171108527L;
	
	private static final int CODE_SIZE = 6;
	private static final char[] ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final Random RANDOM = new Random();

	/**
	 * Creates or joins a housing group given code.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = (Database) request.getServletContext().getAttribute("database");
		String email = (String) request.getSession(false).getAttribute("email");
		String code = null;
		
		// Join existing housing group using code.
		if (request.getParameter("join-group") != null) {
			code = request.getParameter("group-code");
			if (code == null || !isValidCode(code, db)) {
				String errorMessage = "*You must enter a valid group code.";
				request.setAttribute("codeError", "<p class=\"error-message\">" + errorMessage + "</p>");
	            request.getRequestDispatcher("/group.jsp").forward(request, response); 
	            return;
			}
		}
		
		// Generate new housing group and code.
		else if (request.getParameter("create-group") != null) {
			while (true) {
				code = NanoIdUtils.randomNanoId(RANDOM, ALPHABET, CODE_SIZE);
				if (!db.isGroup(code)) { break; }
			}
		}
		
		// Add code to database and redirect to dashboard.
		if (code != null) {
			db.addUserToGroup(email, code);
			request.getSession().setAttribute("group", code);
			request.getRequestDispatcher("/dashboard").forward(request, response); 
		}
	}
	
	/**
	 * Checks if group code follows regex format and is present in database.
	 * @return True if valid code, false if invalid.
	 */
	private boolean isValidCode(String code, Database db) {
		if (code.isBlank() || code.length() != 6) { return false; }
		if (!Pattern.compile("[a-zA-Z0-9]+").matcher(code).matches()) { return false; }
		return db.isGroup(code);
	}

}
