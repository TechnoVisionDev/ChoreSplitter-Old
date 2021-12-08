package servlets.chores;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import data.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles user settings form.
 * @author Thomas Peters
 */
@WebServlet("/settings")
public class SettingsServlet extends HttpServlet {

	private static final long serialVersionUID = -4572860296619322898L;

	/**
	 * Validates settings form and executes changes in MongoDB.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String email = (String) request.getSession(false).getAttribute("email");
		Database db = (Database) request.getServletContext().getAttribute("database");
		
		if (null != request.getParameter("save-settings")) {
			String avatar = request.getParameter("avatar");
			String name = request.getParameter("name");
			
			if (null != avatar && !avatar.isBlank()) {
				if (isValidImage(avatar)) {
					db.setUserString(email, "avatar", avatar);
				} else {
					request.setAttribute("settingsError", "The image address URL provided was invalid!");
				}
			}
			if (null != name && !name.isBlank()) {
				db.setUserString(email, "name", name);
			}
		}
		
		else if (null != request.getParameter("leave-group")) {
			
		}
		
		request.getRequestDispatcher("/settings.jsp").forward(request, response); 
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
