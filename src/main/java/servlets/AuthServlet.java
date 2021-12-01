package servlets;

import java.io.IOException;
import java.util.regex.Pattern;

import com.mongodb.MongoWriteException;

import data.Database;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Handles user authentication for login and registration.
 * @author Thomas Peters
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = -5277574000023873233L;
	
	/**
	 * Retrieves sensitive data from login and registration form.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = (Database) request.getServletContext().getAttribute("database");
		
		// Login form submitted
		if (request.getParameter("login") != null) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			// Server side validation
			String errorMessage = null;
			if (!validEmail(email)) {
				errorMessage = "*You must enter a valid email address.";
			} else if (password.isBlank()) {
				errorMessage = "*You must enter a password.";
			} else if (!db.validateUser(email, password)) {
				errorMessage = "*Invalid email and password.";
			} 
			
			if (errorMessage != null) {
				request.setAttribute("loginError", "<p class=\"error-message\">" + errorMessage + "</p>");
	            request.getRequestDispatcher("/login.jsp").forward(request, response); 
			} else {
				// Create login session for user
				HttpSession session = request.getSession();
				session.setAttribute("name", db.getName(email));
				session.setAttribute("email", email);
	            request.getRequestDispatcher("/group.jsp").forward(request, response); 
			}	
		}
		
		// Registration form submitted
		else if (request.getParameter("register") != null) {
			String email = request.getParameter("email");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String confirm_pass = request.getParameter("confirm_pass");
			String terms = request.getParameter("terms");
			
			// Server side validation
			String errorMessage = null;
			if (!validEmail(email)) {
				errorMessage = "*You must enter a valid email address.";
			} else if (name.isBlank()) {
				errorMessage = "*You must enter a name.";
			} else if (password.isBlank()) {
				errorMessage = "*You must enter a password.";
			} else if (!password.equals(confirm_pass)) {
				errorMessage = "*The passwords you entered do not match.";
			} else if (terms == null) {
				errorMessage = "*You must agree to the terms of service.";
			} else {
				try { 
					// Register user to database
					db.registerUser(new User(email, name, password));
					HttpSession session = request.getSession();
					session.setAttribute("name", name);
					session.setAttribute("email", email);
		            request.getRequestDispatcher("/group.jsp").forward(request, response);
		            return;
				} catch (MongoWriteException e) {
					errorMessage = "*That email address is already in use.";
				}
			}
			
			// Registration failed, send error message
			request.setAttribute("registerError", "<p class=\"error-message\">" + errorMessage + "</p>");
	        request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
	
	/**
	 * Executes when a user logs out, closing their session.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("name") != null){
            session.removeAttribute("name");
            session.removeAttribute("email");
            request.getRequestDispatcher("/landing.jsp").forward(request, response); 
        }
	}
	
	/**
	 * Verifies that an email follows valid regex pattern.
	 */
	private boolean validEmail(String email) {
		if (email.isBlank()) { return false; }
		Pattern zipPattern = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$");
	    return zipPattern.matcher(email).matches();
	}
}
