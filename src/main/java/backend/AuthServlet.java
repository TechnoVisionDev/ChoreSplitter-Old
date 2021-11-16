package backend;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles user authentication for login and registration.
 * @author Thomas Peters
 * @date 11/15/2021
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	
	public void init() throws ServletException {
		// Runs when webpage loads
	}
	
	/**
	 * Retrieves sensitive data from login and registration form.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Runs when POST action is executed
	}
	
	public void destroy() {
		// Runs when servlet is destroyed
	}
}
