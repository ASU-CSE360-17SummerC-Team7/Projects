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
import javax.swing.JPanel;

public class CompanionPanel extends JPanel implements Runnable{

	private JButton hintButton;

	private Companion companion;

	
	
	public CompanionPanel(String fPath) {
		hintButton=null;
		companion = new Companion(fPath);
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
		
	}

	

}
