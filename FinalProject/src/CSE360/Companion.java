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

import java.util.Observable;
import java.util.Observer;

public class Companion implements Runnable, Observer {

	private String message;

	private String[] imageIcon;

	private CompanionBrain brain;
	
	private boolean isMoving;
	private boolean brainEvent;
    
	Thread b;
	
	public Companion(String fPath,String s) {
		message=null;
		imageIcon=null;
		brain = new CompanionBrain(fPath,s);
		b = new Thread(this);
	}
	
	@Override
	public void run() {
		
		if(brainEvent) {
			
			brainEvent=false;
		} else {
			
		}
		

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(brain == (CompanionBrain)(arg0)) {
			message = brain.getMessage();
			imageIcon = brain.getImage();
			isMoving =brain.getIsMoving();
			brainEvent=true;
		}		
	}
	public void updateForMood() {
		brain.updateForMood(); 
		message = brain.getMessage();
		imageIcon = brain.getImage();
		
	}

}
