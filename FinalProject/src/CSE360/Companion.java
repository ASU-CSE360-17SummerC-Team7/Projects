/**
 *
 * @author pdreiter
 * filename: Companion.java
 * creation date: 06/30/2017
 * description: 
 * Team members:
 *  - Chen Yang (cyang112@asu.edu)
 *  - Pemma Reiter (pdreiter@asu.edu)
 *  Notes from author: astah profession created the initial barebones class
 *
 */
package CSE360;

	
public class Companion implements Runnable {

	private String message;

	private String[] imageIcon;

	private int idleCounter;

	private CompanionBrain brain;
    
	public Companion(String fPath) {
		message=null;
		imageIcon=null;
		idleCounter=0;
		brain = new CompanionBrain(fPath);
	}
	
	@Override
	public void run() {

	}

}
