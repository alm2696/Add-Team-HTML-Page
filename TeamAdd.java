package mod05_03;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TeamAdd
 * Handles the insertion of a new team into the database.
 * 
 * @author angel
 */
@WebServlet("/TeamAdd")
public class TeamAdd extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// Model used to handle team data
	private TeamModel model;
       
    /**
     * Default constructor
     * Initializes the servlet
     */
    public TeamAdd() {
        super();
    }
    
    /**
     * Initialize the servlet, creating the TeamModel
     * object that interacts with the database
     */
    @Override
    public void init() {
    	
    	// Handle potential initialization errors
    	try {
    		// Create an instance of the model for database interaction
			this.model = new TeamModel();
		} catch (Exception e) {
    		// Print the stack trace in case of an exception
			e.printStackTrace();
		}
    }
    
    /**
     * Clean up resources when the servlet is destroyed
     * Closes the data model to release database resources
     */
    @Override
    public void destroy() {
    	
    	// Handle potential closing errors
    	try {
    		// Close the TeamModel to release resources
			this.model.close();
    	} catch (Exception e) {
    		// Print the stack trace in case of an exception
			e.printStackTrace();
		}
    }    

	/**
	 * Handle POST requests to add a new team to the database
	 * @param request  The HTTP request containing the form data
	 * @param response The HTTP response to be sent back
	 * @throws ServletException If a servlet error occurs
	 * @throws IOException If an input/output error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Handle potential SQL exceptions and input parsing
		try {
			
			// Parse the form input values
			int teamId = Integer.parseInt( request.getParameter("teamId") );
			String city = request.getParameter("city");
			String name = request.getParameter("name");
			
			// Create a new Team object with the input data
			Team team = new Team(teamId, city, name);

			// Use the model to add the new team to the database
			this.model.addTeam(team);
			
			// Redirect the user to the TeamList page after successful insertion
			response.sendRedirect("TeamList");
			
		} catch (SQLException e) {
			// Send an error response if a SQL exception occurs
			response.sendError(404, e.getMessage());
			
			// Print the stack trace for debugging purposes
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// Handle potential number formatting issues (e.g., invalid teamId input)
			response.sendError(400, "Invalid input for Team ID. Please enter a valid number.");
			
			// Print the stack trace for debugging purposes
			e.printStackTrace();
		}
	}
}
