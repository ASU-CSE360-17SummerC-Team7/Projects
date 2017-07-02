/**
 *
 * @author 
 * filename: 
 * creation date: 06/30/2017
 * description: 
 * Team members:
 *  - Chen Yang (cyang112@asu.edu)
 *  - Pemma Reiter (pdreiter@asu.edu)
 *  Notes from author: astah profession created the initial barebones class
 *
 */

package CSE360;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Project7 extends JFrame {
	
	private CompanionPanel companionPanel;
	private ExamPanel examPanel;

	public Project7(String fPath) {
		setLayout(new GridLayout(1,2));
		setTitle("Final Project - Team7 (Chen Yang and Pemma Reiter)");
		setSize(900,900);
		companionPanel = new CompanionPanel(fPath,"Pemma Reiter");
		add(companionPanel);
		companionPanel.setVisible(true);
		companionPanel.drawCompanion();
		
		setVisible(true);
		revalidate();
		repaint();
	}
	public static void main(String[] args){
		String fPath = null; int fIter;
        // pdreiter - some error handling for images - make sure that Team7Images path is correct, despite project include paths
		for(fIter=0;fIter<Project7Global.filePath.length;fIter++) { 
			if((new File(Project7Global.filePath[fIter])).isDirectory()) { fPath=Project7Global.filePath[fIter]; break;}
			else if(Project7Global.DEBUG) { System.out.println("Cannot find directory : "+Project7Global.filePath[fIter]); }
		}
		if(fPath==null) { System.out.println("Unable to continue: Could not resolve filepaths"); }
		else { 
		    Project7 p7 = new Project7(fPath);
		}
		
	}

}
