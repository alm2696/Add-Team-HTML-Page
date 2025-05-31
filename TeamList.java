package mod05_03;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TeamList
 * This servlet generates an HTML page that lists
 * all the teams by retrieving data from a model.
 * 
 * @author angel
 */
@WebServlet("/TeamList")
public class TeamList extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// Instance of the TeamModel used to retrieve team data
	private TeamModel model;
       
    /**
     * Default constructor.
     * Initializes a new instance of TeamList.
     * @see HttpServlet#HttpServlet()
     */
    public TeamList() {
        super();
    }
    
    /**
     * Initializes the servlet by creating a new instance 
     * of the TeamModel to interact with team data.
     * This method is called once when the servlet is loaded.
     */
    @Override
    public void init() {
    	
    	// Handle any errors during model initialization
    	try {
    		
    		// Instantiate a new TeamModel
			this.model = new TeamModel();
			
		} 
    	// Catch any exceptions and log the stack trace
    	catch (Exception e) {
    		
    		// Log the exception details
			e.printStackTrace();
			
		}
    }
    
    /**
     * Cleans up resources when the servlet is destroyed.
     * This is where the data model is closed to release resources.
     * This method is called once when the servlet is removed from memory.
     */
    @Override
    public void destroy() {
    	
    	// Handle any errors during model closure
    	try {
    		
    		// Close the model to free resources
			this.model.close();
    	}
    	// Catch any exceptions and log the stack trace
    	catch (Exception e) {
    		
    		// Log the exception details
			e.printStackTrace();
			
		}
    }

	/**
	 * Handles HTTP GET requests to display a list of teams.
	 * It retrieves the team data from the model and
	 * generates an HTML table showing team details.
	 * 
	 * @param request The HTTP request object received from the client
	 * @param response The HTTP response object sent back to the client
	 * @throws ServletException If an input or output error occurs
	 * @throws IOException If an error occurs during servlet processing
	 * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Create a new HTML page with the title "Module 5: Team List"
		HTMLPage page = new HTMLPage("Module 5: Team List");
		
		// Add a main header to the page
		page.appendBody("<h1>Team List</h1>");
		
		// Handle any potential errors during data retrieval
		try {
			
			// Retrieve the list of teams from the model
			ArrayList<Team> teamList = (ArrayList<Team>) this.model.getTeamList();
		
			// Start the HTML table structure with headers for Team ID, City, and Name
			page.appendBody("""
					<table>
						<tr>
							<th>Team ID</th>
							<th>City</th>
							<th>Name</th>
						</tr>
					""");
			
			// Loop through the list of teams and add each to the HTML table
			for (Team team : teamList) {

				// Create a row for each team with its ID, city, and name
				String row = """
						<tr>
							<td>%d</td>
							<td>%s</td>
							<td>%s</td>
						</tr>
						""".formatted(team.getTeamId(),
									   team.getCity(),
									   team.getName());
						
				// Append the row to the page body
				page.appendBody(row);
			}
			
			// End the HTML table
			page.appendBody("</table>");
			
		} 
    	// Catch any exceptions during data processing and display an error message
    	catch (Exception e) {
    		
    		// Display an error message and exception details on the page
    		page.appendBody("""
    				<h2>Error retrieving the team list</h2>
    				<p>%s</p>
    				""".formatted(e.getMessage()));
    		
    		// Log the stack trace of the exception
			e.printStackTrace();
			
		}
		
		// Send the generated HTML page back to the client
		PrintWriter netOut = response.getWriter();
		netOut.print(page.getPage());
	}
}
