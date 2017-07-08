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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Companion extends JPanel implements Runnable {

	private static final int IMG_SCALE_Y = 200;
	private static final int IMG_SCALE_X = 200;
	private String[] imageIcon;
	private int imageIndex;
	private CompanionBrain brain;
	private CompanionMood mood;
	private JTextArea message;
	private JLabel icon;
	private Thread animation;
	private boolean isRunning;
    private static JTextArea time;
    private String fPath;
	private JButton hintButton;
    
	    
	public Companion(CompanionBrain b, String fPath_input, int w, int h) {
		this.setBackground(Color.white);
		Project7Global.DEBUG_MSG(0, "Companion() : start");
		Project7Global.DEBUG_MSG(0, "Companion() : width ["+Integer.toString(w)+"], height ["+Integer.toString(h)+"]");
		fPath=fPath_input;
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(w,h));
		this.setSize(w,h);
		imageIndex=0;
		brain=b;
		Project7Global.DEBUG_MSG(0, "Companion() : brain.message = "+brain.getMessage());
		message=new JTextArea(brain.getMessage()); message.setFont(new Font("Verdana", Font.BOLD, 30)); message.setLineWrap(true);
		imageIcon = brain.getImage(); 
		mood = brain.getMood();
		
		time=new JTextArea(); updateCurrentTime(); time.setLineWrap(true); time.setFont(new Font("Verdana",Font.BOLD,20));
		Project7Global.DEBUG_MSG(0, "Companion() : brain.image = "+imageIcon[imageIndex]);
		icon = new JLabel(new ImageIcon((new ImageIcon(fPath+"/"+imageIcon[imageIndex]).getImage().getScaledInstance(IMG_SCALE_X, IMG_SCALE_Y,
		            java.awt.Image.SCALE_SMOOTH)),"Meh"));
		icon.setBackground(Color.WHITE);
		GridBagConstraints c=new GridBagConstraints(); // this section is heavily influenced by Java GridBagLayout tutorial
		c.weightx=0.25;c.weighty=1;c.fill=GridBagConstraints.BOTH;
		c.gridx=0;c.gridy=0;
		c.anchor=GridBagConstraints.LINE_START;
		this.add(icon,c);
		c.anchor=GridBagConstraints.LINE_END;
		c.gridx=1; c.gridheight=1;c.gridwidth=2; 
		this.add(message,c);
//		c.anchor=GridBagConstraints.LINE_END;
		c.gridx=0;c.gridy=3;c.gridheight=1;c.gridwidth=3;
		this.add(time,c);
	
		message.setPreferredSize(new Dimension(w,h));message.setVisible(true);message.setOpaque(true);
		icon.setPreferredSize(new Dimension(w,h));icon.setVisible(true);icon.setOpaque(true);
		time.setPreferredSize(new Dimension(w,h));time.setVisible(true);time.setOpaque(true);
		setVisible(true);
		revalidate();
		repaint(); 
		startCompanionThread();
		Project7Global.DEBUG_MSG(0, "Companion() : end");
	}
	public int getIdleCounter() { return brain.getIdleCounter(); } 
    public boolean isMoving() { 
    	return brain.getIsMoving();
    }
    public static void updateCurrentTime(){
        time.setText(LocalDateTime.now().toString());
    } 
	
	public void updateForMood(boolean updateMessage) {
		//reset index if mood changed
		brain.updateForMood();
		boolean moodChanged=(mood!=brain.getMood()); mood=brain.getMood();
		if(moodChanged || updateMessage) { message.setText(brain.getMessage()); }
		imageIcon = brain.getImage();

        updateAnimation(moodChanged);
	}
	
    public void updateAnimation(boolean moodChanged) {
    	// if mood changed, then reset imageIndex and setIcon to appropriate image
    	if(moodChanged) { 
    		imageIndex=0;
    		icon.setIcon(new ImageIcon((new ImageIcon(fPath+"/"+imageIcon[imageIndex]).getImage().getScaledInstance(IMG_SCALE_X, IMG_SCALE_Y,
			        java.awt.Image.SCALE_SMOOTH)),"Meh"));
    	} else if(imageIcon.length>1) { // should only update image if there are more than one [animated] 
        	imageIndex = (++imageIndex)%imageIcon.length;
            Project7Global.DEBUG_MSG(0,"getting new image from "+fPath+"/"+imageIcon[imageIndex]+" (index : "+Integer.toString(imageIndex)+")");
			icon.setIcon(new ImageIcon((new ImageIcon(fPath+"/"+imageIcon[imageIndex]).getImage().getScaledInstance(IMG_SCALE_X, IMG_SCALE_Y,
			        java.awt.Image.SCALE_SMOOTH)),"Meh"));
    	}
    }
    private void startCompanionThread() {
     	isRunning=true;
    	if(animation == null) { 
	        setVisible(true);   
	        animation = new Thread(this);
	        animation.start();
    	}        
    }
    // private helper function: stopGhostMovement
    // manages stopping the ghost thread to ensure that the thread operates correctly
    private void stopCompanionThread() {
    	isRunning=false;
    	if(animation != null) { 
	        setVisible(false); 
	        animation.interrupt();
	        while(animation.isInterrupted()==true){} // wait until thread has completely been interrupted
	        animation=null; System.gc(); // then delete thread and clean upS
    	}
    }
    
    private void updateIdleCounter() { brain.updateIdleCounter(); }

	@Override
	public void run() {
		Project7Global.DEBUG_MSG(0, "Companion: run()");
		int i=0;
        while (isRunning) { 
    		Project7Global.DEBUG_MSG(0, "Companion: run() : Iteration i = "+Integer.toString(i));
            try { 
                if(animation.interrupted()) { return; }
                // if the other animation is moving across the screen, this should not be visible
        		Project7Global.DEBUG_MSG(0, "Companion: run() : Is Brain Moving "+Boolean.toString(brain.getIsMoving()));
                icon.setVisible(!brain.getIsMoving());
                updateForMood((++i%20) == 0); // force message update every 2 seconds
                updateCurrentTime();
                repaint();
                //pause for 1 second
                animation.sleep(1000);updateIdleCounter();
                i=i%100; 
            } catch (InterruptedException ex) {
            	isRunning=false;
                return; // we've been interrupted, no more thread execution
            }
        }	
	}
	public void killThread() {
		this.stopCompanionThread();
	}
}


