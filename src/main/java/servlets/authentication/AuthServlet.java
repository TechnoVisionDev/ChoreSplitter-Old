package servlets.authentication;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.mongodb.MongoWriteException;

import data.Database;
import data.serializables.User;
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
	private static final String DEFAULT_AVATAR = "https://i.stack.imgur.com/34AD2.jpg";
	
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
				request.setAttribute("loginError", errorMessage);
	            request.getRequestDispatcher("/login.jsp").forward(request, response); 
			} else {
				// Create login session for user
				createSession(request.getSession(), db.getUserValue(email, "name"), email, db.getUserValue(email, "group"));
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
			
			// Check avatar validity
			String avatar = request.getParameter("avatar");
			if (!isValidImage(avatar)) {
				avatar = DEFAULT_AVATAR;
			}
			
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
					db.registerUser(new User(email, name, password, avatar));
					createSession(request.getSession(), name, email, db.getUserValue(email, "group"));
		            request.getRequestDispatcher("/group.jsp").forward(request, response);
		            return;
				} catch (MongoWriteException e) {
					errorMessage = "*That email address is already in use.";
				}
			}
			
			// Registration failed, send error message
			request.setAttribute("registerError", errorMessage);
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
            session.removeAttribute("group");
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
	
	/**
	 * Creates a new HTTP session for user and sets necessary attributes.
	 */
	private void createSession(HttpSession session, String name, String email, String code) {
		session.setAttribute("name", name);
		session.setAttribute("email", email);
		if (code != null) {
			session.setAttribute("group", code);
		}
	}
	
	/**
	 * Checks if image address url is valid.
	 * @param url image address url string.
	 * @return true if valid image, otherwise false.
	 */
	private boolean isValidImage(String url) {
        try {  
        	BufferedImage image = ImageIO.read(new URL(url));   
            return (image != null);
        } catch (IOException ignored) { }
        return false;
	}
	
}
