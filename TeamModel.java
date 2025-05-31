package mod05_03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the model for accessing and managing team data in a MariaDB database.
 * It allows querying the database to retrieve and insert teams.
 * The database connection is shared across instances of the class.
 * 
 * @author angel
 */
public class TeamModel {

	// Database connection information
	private String DBDriver = "com.mysql.cj.jdbc.Driver"; // MySQL JDBC Driver
	private String DBURL = "jdbc:mysql://localhost:3306/CMSC230"; // Database URL
	private String DBUser = "root"; // Database username
	private String DBPassword = "Password!!"; // Database password

	// Static variables to manage database connections and queries
	private static int counter = 0; // Keeps track of the number of model instances
	private static Connection DBConn = null; // Database connection object
	private static PreparedStatement queryTeamList = null; // PreparedStatement to query the team list
	private static PreparedStatement queryTeamInsert = null; // PreparedStatement to insert a team

	/**
	 * Constructor to initialize the model and establish the database connection if needed.
	 * If a connection does not already exist, it loads the database driver and connects to the DB.
	 * 
	 * @throws ClassNotFoundException If the database driver is not found
	 * @throws SQLException If there is an issue connecting to the database
	 */
	public TeamModel() throws ClassNotFoundException, SQLException {

		// Check if a database connection is already established
		if (TeamModel.DBConn == null) {

			// Load the MySQL JDBC driver
			Class.forName(this.DBDriver);

			// Establish a connection to the database
			TeamModel.DBConn = DriverManager.getConnection(
					this.DBURL, this.DBUser, this.DBPassword);
		}

		// Increment the instance counter to track number of open models
		TeamModel.counter++;
	}

	/**
	 * Closes the data model and cleans up open resources like database connections and queries.
	 * It decrements the instance counter and closes the connection if no more instances exist.
	 * 
	 * @throws SQLException If an error occurs while closing resources
	 */
	public void close() throws SQLException {

		// Decrement the instance count
		TeamModel.counter--;

		// Close resources if no more instances of TeamModel exist
		if (TeamModel.counter == 0) {

			// Close the prepared statements and database connection
			TeamModel.queryTeamList.close();
			TeamModel.DBConn.close();
		}
	}

	/**
	 * Retrieves the list of teams from the database.
	 * Queries the 'teams' table and returns the list of teams as Team objects.
	 * 
	 * @return A list of Team objects
	 * @throws SQLException If an error occurs during the query
	 */
	public List<Team> getTeamList() throws SQLException {

		// Create a list to store the teams
		ArrayList<Team> list = new ArrayList<>();

		// Prepare the SQL query if it has not been prepared yet
		if (TeamModel.queryTeamList == null) {

			// Prepare the query to select team ID, city, and name from the teams table
			TeamModel.queryTeamList = TeamModel.DBConn.prepareStatement(
					"SELECT teamId, city, name FROM teams");
		}

		// Execute the query and get the result set
		ResultSet results = TeamModel.queryTeamList.executeQuery();

		// Iterate through the results and create Team objects for each row
		while (results.next()) {

			// Create a new Team object using the data from the current row
			Team team = new Team(
					results.getInt("teamId"),
					results.getString("city"),
					results.getString("name"));

			// Add the team to the list
			list.add(team);
		}

		// Close the result set as it's no longer needed
		results.close();

		// Return the list of teams
		return list;
	}

	/**
	 * Inserts a new team into the database.
	 * Adds a new team to the 'teams' table using the provided Team object.
	 * 
	 * @param team The Team object to be inserted into the database
	 * @throws SQLException If an error occurs during the insert operation
	 */
	public void addTeam(Team team) throws SQLException {

		// Prepare the SQL insert query if it has not been prepared yet
		if (TeamModel.queryTeamInsert == null) {

			// Prepare the insert query with placeholders for teamId, city, and name
			TeamModel.queryTeamInsert = TeamModel.DBConn.prepareStatement(
					"INSERT INTO teams (teamId, city, name) VALUES (?, ?, ?)");
		}

		// Set the parameters for the insert query using the Team object
		TeamModel.queryTeamInsert.setInt(1, team.getTeamId());
		TeamModel.queryTeamInsert.setString(2, team.getCity());
		TeamModel.queryTeamInsert.setString(3, team.getName());

		// Execute the insert query
		TeamModel.queryTeamInsert.executeUpdate();
	}
}
