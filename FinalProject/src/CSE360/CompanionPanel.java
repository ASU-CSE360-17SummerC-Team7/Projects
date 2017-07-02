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

import java.awt.Dimension;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class CompanionPanel extends JPanel implements Runnable {

	private JLayeredPane jp;
	private Team7Ghost movingCompanion;
	private Companion  staticCompanion;
	
	private boolean isMoving;
	Thread moveMe;
	
	private final int JPANEL_WIDTH=600;
	private final int JPANEL_HEIGHT=200;	
	
	public CompanionPanel(String fPath, String s) {
		Project7Global.DEBUG_MSG(0, "CompanionPanel(): start");
		//		hintButton=null;
		movingCompanion=new Team7Ghost(JPANEL_WIDTH,JPANEL_HEIGHT,fPath+"/images", false); // make ghost invisible by default
		isMoving=movingCompanion.getVisibility();
		staticCompanion = new Companion(new CompanionBrain(fPath,s),fPath, JPANEL_WIDTH,JPANEL_HEIGHT);
        moveMe = new Thread(this);
		jp=new JLayeredPane();
		jp.setPreferredSize(new Dimension(JPANEL_WIDTH,JPANEL_HEIGHT));
		jp.setSize(JPANEL_WIDTH, JPANEL_HEIGHT);
		jp.add(movingCompanion, 1);
		jp.add(staticCompanion, 0);
		this.add(jp);
//		add(staticCompanion);
		jp.setVisible(true);
		setVisible(true);
		jp.revalidate();jp.repaint();
		//staticCompanion.setVisible(true); staticCompanion.repaint();
		Project7Global.DEBUG_MSG(0, "CompanionPanel(): Added Moving Companion and Static Companion to LayeredPane");
		drawCompanion();
		moveMe.start();
		Project7Global.DEBUG_MSG(0, "CompanionPanel(): end");
	}

	public void drawCompanion() {
		Project7Global.DEBUG_MSG(0, "CompanionPanel drawCompanion(): start");
		if(isMoving) { 
			movingCompanion.makeVisible();
		    staticCompanion.setVisible(false);
		    staticCompanion.repaint();
		}
		else { 
			movingCompanion.makeInvisible();
			staticCompanion.setVisible(true);
			staticCompanion.repaint();
		}
		movingCompanion.setVisible(isMoving);
		//jp.setVisible(true);jp.repaint();
		repaint();
		Project7Global.DEBUG_MSG(0, "CompanionPanel drawCompanion(): end");
	}

	
	@Override
	public void run() {
		while(true) {
			Project7Global.DEBUG_MSG(0, "CompanionPanel: run");
			if(staticCompanion.isMoving()!=isMoving) {
				Project7Global.DEBUG_MSG(1, "CompanionPanel: run.isMoving changed state");
				isMoving=staticCompanion.isMoving();
				drawCompanion();
			} 			
			try {
				moveMe.sleep(100);
				//jp.revalidate();jp.repaint();
				Project7Global.DEBUG_MSG(0, "CompanionPanel: run() : sleep");
			} catch (InterruptedException e) {
				Project7Global.ERROR_MSG("CompanionPanel: Interrupt Exception");
				return;
			}
		}
	}
	
}
