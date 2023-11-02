package mainApp;


/**
 * Class: MainApp
 * @author Put your team name here
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * <br>Restrictions: None
 */
public class MainApp {
	EvolutionViewer evViewer = new EvolutionViewer();
	IndividualViewer indViewer = new IndividualViewer();

	public void runApp() {
		evViewer.handleDriverMain();
		evViewer.handleDriverMain();
	} // runApp

	/**
	 * ensures: runs the application
	 * @param args unused
	 */
}