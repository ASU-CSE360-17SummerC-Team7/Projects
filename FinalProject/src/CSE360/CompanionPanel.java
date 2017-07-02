/**
 *
 * @author pdreiter
 * filename: CompanionPanel.java
 * creation date: 06/30/2017
 * description: 
 * Team members:
 *  - Chen Yang (cyang112@asu.edu)
 *  - Pemma Reiter (pdreiter@asu.edu)
 *  Notes from author: astah profession created the initial barebones class
 *
 */

package CSE360;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class CompanionPanel extends JPanel implements Runnable{

	private JButton hintButton;

	private String message;

	private String[] imageIcon;

	private CompanionBrain brain;
	
	private JLayeredPane jp;
	private Team7Ghost ghost;
	
	private boolean isMoving;
	private boolean brainEvent;
	
	private final int JPANEL_WIDTH=600;
	private final int JPANEL_HEIGHT=200;
    
	Thread b;

	
	
	public CompanionPanel(String fPath, String s) {
		hintButton=null;
		isMoving=false;brainEvent=false;
		brain = new CompanionBrain(fPath,s);
		message=brain.getMessage();
		imageIcon=brain.getImage();
		b = new Thread(this);
		ghost=new Team7Ghost(JPANEL_WIDTH,JPANEL_HEIGHT,fPath);
	}

	public void drawCompanion() {

	}

	public void drawMessage() {

	}

	public void drawHintButton() {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(brainEvent) {
			
			brainEvent=false;
		} else {
			brain.updateIdleCounter();
			if(brain.getIsMoving()) {}
		}
	
		
	}

	public void updateForMood() {
		brain.updateForMood(); 
		message = brain.getMessage();
		imageIcon = brain.getImage();
		
	}
	

}
