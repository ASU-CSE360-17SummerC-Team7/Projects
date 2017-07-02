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

import java.awt.Dimension;
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

public class Companion extends JPanel implements Runnable {

	private String[] imageIcon;
	private int imageIndex;
	private CompanionBrain brain;
	private CompanionMood mood;
	private JLabel message;
	private JLabel icon;
	private Thread animation;
	private boolean isRunning;
    private static JLabel time;
    private String fPath;
	private JButton hintButton;
    
	    
	public Companion(CompanionBrain b, String fPath, int w, int h) {
		Project7Global.DEBUG_MSG(0, "Companion() : start");
		Project7Global.DEBUG_MSG(0, "Companion() : width ["+Integer.toString(w)+"], height ["+Integer.toString(h)+"]");
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(w,h));
		imageIndex=0;
		brain=b;
		Project7Global.DEBUG_MSG(0, "Companion() : brain.message = "+brain.getMessage());
		message=new JLabel(brain.getMessage()); 
		imageIcon = brain.getImage();
		mood = brain.getMood();

		time=new JLabel(); updateCurrentTime();
		Project7Global.DEBUG_MSG(0, "Companion() : brain.image = "+imageIcon[imageIndex]);
		try {
			icon = new JLabel(new ImageIcon(ImageIO.read(new File(fPath+"/"+imageIcon[imageIndex]))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Project7Global.ERROR_MSG("Can't find input file: "+fPath+"/"+imageIcon[imageIndex]);
			e.printStackTrace();
		}
		
		GridBagConstraints c=new GridBagConstraints(); // this section is heavily influenced by Java GridBagLayout tutorial
		c.weightx=0.333333;c.weighty=1;c.fill=GridBagConstraints.BOTH;
		c.gridx=0;c.gridy=0;
		c.anchor=GridBagConstraints.LINE_START;
		this.add(icon,c);
		c.anchor=GridBagConstraints.CENTER;c.gridx=1; 
		this.add(message,c); 
		c.anchor=GridBagConstraints.LINE_END;
		this.add(time,c);
	
		message.setPreferredSize(new Dimension(w,h));message.setVisible(true);message.setOpaque(true);
		icon.setPreferredSize(new Dimension(w,h));icon.setVisible(true);icon.setOpaque(true);
		time.setPreferredSize(new Dimension(w,h));time.setVisible(true);time.setOpaque(true);
		setVisible(true);
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
    		try {
				icon.setIcon(new ImageIcon(ImageIO.read(new File(fPath+"/"+imageIcon[imageIndex]))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} else if(imageIcon.length>1) { // should only update image if there are more than one [animated] 
        	imageIndex = (imageIndex++)%imageIcon.length;
            try {
				icon.setIcon(new ImageIcon(ImageIO.read(new File(fPath+"/"+imageIcon[imageIndex]))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
                //pause for 0.1 seconds
                animation.sleep(100);updateIdleCounter();
                i=i%100; 
            } catch (InterruptedException ex) {
            	isRunning=false;
                return; // we've been interrupted, no more thread execution
            }
        }
			
	}
}


