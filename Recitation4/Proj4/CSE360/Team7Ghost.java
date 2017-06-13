/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSE360;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author pdreiter
 */


public class Team7Ghost extends JFrame {
    GhostAnimationLabel y;
    /**
     * @param args the command line arguments
     */
    public Team7Ghost() {
        y = new GhostAnimationLabel();
        this.add(y);
    }
    public GhostAnimationLabel getGhostAnimationLabel(){ return y;}
    
}

class GhostAnimationLabel extends JPanel {
    private JLabel animation;
    private String dir;
    private int xg;
    private int yg;
    private static final int step = 10;    // how many pixels stepped in whatever direction
    private static final String iconPath="CSE360\\imagesTeam7\\ghost_";
    JFrame pf;
    public GhostAnimationLabel() {
        
        xg=0;yg=0;
        dir="right";
        animation = new JLabel ("",new ImageIcon(getFullIconPath(),"Blinky"),JLabel.CENTER);
        this.add(animation);
        this.setBounds(xg,yg,1024,1024);
        setVisible(true);
    }
    public void setDirection(int a){ 
        switch(a) { 
            case 0: 
                dir = "up";
                break;
            case 1: 
                dir = "down";
                break;
            case 2: 
                dir = "left";
                break;
            default: 
                dir = "right";
                break;
        }
    }
    public boolean moveGhost() {
        switch(dir) { 
            case "up": 
                return moveGhostUp();
            case "down":
                return moveGhostDown();
            case "left":
                return moveGhostLeft();
            case "right":
                return moveGhostRight();
            default: 
                return moveGhostRight();
        }
    }
    public boolean moveGhostRight() { 
        if(isXwithinBounds(xg+step)) { updateGhostCoordinates(xg+step,yg); return true;}
        else return false;
    }
    public boolean moveGhostLeft() { 
        if(isXwithinBounds(xg-step)) { updateGhostCoordinates(xg-step,yg); return true;}
        else return false;
    }
    public boolean moveGhostUp() { 
        if(isYwithinBounds(yg-step)) { updateGhostCoordinates(xg,yg-step); return true;}
        else return false;
    }
    public boolean moveGhostDown() { 
        if(isYwithinBounds(yg+step)) { updateGhostCoordinates(xg,yg+step); return true;}
        else return false;
    }
    public void updateGhostAnimation() throws IOException{
        animation.setIcon(new ImageIcon(ImageIO.read(new File(getFullIconPath()))));
    }
    public String getFullIconPath(){ return iconPath+dir+".png";}

    public int getMaxX() { 
        JFrame upperFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        return upperFrame.getSize().width;
    }
    public int getMaxY() { 
        JFrame upperFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        return upperFrame.getSize().height;
    }

    public boolean isXwithinBounds(int x) { 
        return ((x>=0) && (x+animation.getIcon().getIconWidth()<= getMaxX()));
    }
    public boolean isYwithinBounds(int y) { 
        JFrame upperFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int max_y=upperFrame.getSize().height;
        return ((y>=0) && ((y+animation.getIcon().getIconHeight())<= getMaxY()));
    }         
    public void updateGhostCoordinates(int x,int y) {
        xg=x;yg=y;
        setLocation(xg,yg);
    }
    public void updateDirection(int x, int y) {
        if(xg>x){dir="left";}
        else if(yg<y){dir="down";}
        else if(yg>y) { dir="up"; }
        else { dir="right";}
    }
    public String getDirection(){ return dir; }
    public int getXloc() { return xg; }
    public int getYloc() { return yg; }
}

    class ghostAnimationLoop implements Runnable {
        private final GhostAnimationLabel gal;
        public ghostAnimationLoop(GhostAnimationLabel g){
            gal=g;
        }
        @Override
        public void run(){
            int i=0;
            int moveCtr=0;

            while (true) { 
                try { 
                    if(moveCtr<=0) { 
                        moveCtr = 30; //(int) (Math.random()%40); 
                        gal.setDirection((int) (Math.floor(Math.random()*101))%4);
                    }
                    //pause for 0.1 seconds
                    Thread.sleep(100);
                    System.out.println(gal.getFullIconPath());
                    try {
                        boolean validMove = gal.moveGhost(); 
                        // if it's not a valid move within boundaries of the frame, then don't move, but regenerate random direction on next loop
                        if(validMove==false){ 
                            moveCtr=0; 
                            System.out.println("("+Integer.toString(gal.getXloc())+","+Integer.toString(gal.getYloc())+")\n => MAX: "+Integer.toString(gal.getMaxX())+","+Integer.toString(gal.getMaxY())+")");
                        } 
                        
                        gal.updateGhostAnimation();
                    } catch (IOException ex) {
                        Logger.getLogger(GhostAnimationLabel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i=(++i)%100;moveCtr--;
                } catch (InterruptedException ex) {
                    Logger.getLogger(GhostAnimationLabel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }