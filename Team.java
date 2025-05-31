package mod05_03;

/**
 * This class represents a Team with a unique ID, city, 
 * and name. It provides methods to retrieve these values.
 * 
 * @author angel
 */
public class Team {
	
	// Instance variables for team ID, city, and name
	private int teamId;
	private String city;
	private String name;
	
	/**
	 * Constructor to initialize a new Team object.
	 * 
	 * @param teamId The unique ID assigned to the team
	 * @param city The city where the team is located
	 * @param name The name of the team
	 */
	public Team(int teamId, String city, String name) {
		super();
		this.teamId = teamId;
		this.city = city;
		this.name = name;
	}

	/**
	 * Retrieve the unique team ID.
	 * 
	 * @return The team ID as an integer
	 */
	public int getTeamId() {
		return teamId;
	}

	/**
	 * Retrieve the city where the team is located.
	 * 
	 * @return The team city as a String
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Retrieve the name of the team.
	 * 
	 * @return The team name as a String
	 */
	public String getName() {
		return name;
	}
}
