package mod05_03;

/**
 * This class represents an HTML page builder.
 * It provides methods to create an HTML page, add content
 * to the body, and retrieve the entire HTML structure.
 * 
 * @author angel
 */
public class HTMLPage {
	
	// Instance variables to store the page title and body content
	private String title;
	private String body;
	
	/**
	 * Main constructor to initialize the HTML page with a title.
	 * The body is initialized as an empty string.
	 * 
	 * @param title The title for the HTML page
	 */
	public HTMLPage(String title) {
		
		// Initialize instance variables
		this.title = title;
		this.body = "";  // Body starts empty and content is appended later
	}
	
	/**
	 * Adds content to the body of the HTML page.
	 * The provided content is appended to the existing body.
	 * 
	 * @param content The HTML content to append to the body
	 */
	public void appendBody(String content) {
		// Append the given content to the body
		this.body += content;
	}
	
	/**
	 * Retrieves the entire HTML page as a string.
	 * The method builds the complete HTML structure by
	 * combining the title, body, and other necessary elements.
	 * 
	 * @return The full HTML page as a string
	 */
	public String getPage() {
		
		// Build the start of the HTML page, including the DOCTYPE, opening HTML tag, and head section with the title
		String page = """
				<!DOCTYPE html>
				<html>
				<head>
					<meta charset="UTF-8">
					<title>%s</title>
				</head>
			""".formatted(this.title);
		
		// Add the body section of the HTML, inserting the current body content
		page += """
				<body>
				   %s
				</body>
			""".formatted(this.body);
		
		// Close the HTML tag to complete the page
		page += """
				</html>
				""";  // Fixed typo: changed </htm> to </html>
		
		// Return the complete HTML page as a string
		return page;
	}
}
