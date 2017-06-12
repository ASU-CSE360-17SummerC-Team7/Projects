/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadedlayeredpane;

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


public class ThreadedLayeredPane extends JFrame {
//    TimeLabel x;
    GhostAnimationLabel y;
    /**
     * @param args the command line arguments
     */
    public ThreadedLayeredPane() {
//        GridLayout grid = new GridLayout(1,2);
//        setLayout(grid);
//        x = new TimeLabel();
        y = new GhostAnimationLabel();
//        this.add(x);
        this.add(y);
//        this.add(new AnimationLabel());
    }
    public GhostAnimationLabel getGhostAnimationLabel(){ return y;}
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        ThreadedLayeredPane u = new ThreadedLayeredPane();
        u.setTitle("Time and Image Animation");
        u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        u.setSize(500,500);
        u.setVisible(true);
        System.out.println("Starting UpdateTimeLoop thread");
//        Thread tl = new Thread(new TimeLabel.timeLoop());
        Thread ta = new Thread(new ghostAnimationLoop(u.getGhostAnimationLabel()));
//        tl.start();
        ta.start();
    }
    
    
}


class TimeLabel extends JPanel {
    private static JLabel currentTime;
    private static LocalDateTime x;
    
    public TimeLabel() {
        x = LocalDateTime.now();
        currentTime = new JLabel(x.toString());
        add(currentTime);
        setVisible(true);
    }
    public static void updateCurrentTime(){
        x = LocalDateTime.now();
        currentTime.setText(x.toString());
    }
    public static class timeLoop implements Runnable {
        @Override
        public void run(){
            while(true){
                try {
                    // only display at every new Second
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TimeLabel.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(x.toString()+"\n");
                updateCurrentTime();
            }
        }
    }
    
}

class AnimationLabel extends JPanel {
    private static JLabel animation;
    private static int inum;
    private static final String iconPath="src\\CSE360\\imagesTeam7\\image";
    public AnimationLabel() {
        inum=0;
        animation = new JLabel ("",new ImageIcon(iconPath+Integer.toString(inum)+".png","Drawing 7"),JLabel.LEFT);
        this.add(animation);
        setVisible(true);
    }
    public static void updateAnimation() throws IOException{
        if(inum>=10) inum=-1; // should only display images 0 through 10
        animation.setIcon(new ImageIcon(ImageIO.read(new File(iconPath+Integer.toString(++inum)+".png"))));
    }
    public static class animationLoop implements Runnable {
        @Override
        public void run(){
            while (true) { 
                try { 
                    //pause for 0.1 seconds
                    Thread.sleep(100);
                    System.out.println(iconPath+Integer.toString(inum)+".png");
                    try {
                        updateAnimation();
                    } catch (IOException ex) {
                        Logger.getLogger(AnimationLabel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(AnimationLabel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

class GhostAnimationLabel extends JPanel {
    private JLabel animation;
    private String dir;
    private int xg;
    private int yg;
    private static final int step = 10;    // how many pixels stepped in whatever direction
    private static final String iconPath="src\\CSE360\\imagesTeam7\\ghost_";
    JFrame pf;
    public GhostAnimationLabel() {
        
        xg=0;yg=0;
        dir="right";
        animation = new JLabel (new ImageIcon(getFullIconPath(),"Blinky"));
        this.setAlignmentX(JFrame.LEFT_ALIGNMENT);
        this.setAlignmentY(JFrame.TOP_ALIGNMENT);
        this.add(animation);
        this.setBounds(xg,yg,103,103);
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
        return (((x-step)>=(-(getMaxX()/2))) && (x+animation.getIcon().getIconWidth()+step<= (getMaxX()/2)));
    }
    public boolean isYwithinBounds(int y) { 
        return ((y>=0) && ((y+animation.getIcon().getIconHeight())+step<= getMaxY()));
    }         
    public int getImageWidth(){ return animation.getIcon().getIconWidth(); }
    public int getImageHeight(){ return animation.getIcon().getIconHeight(); }
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
            boolean runme = true;
            while (runme) { 
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
                            System.out.println("("+Integer.toString(gal.getXloc())+"+"+Integer.toString(gal.getImageWidth())+","+Integer.toString(gal.getYloc())+"+"+Integer.toString(gal.getImageHeight())+")\n => MAX: "+Integer.toString(gal.getMaxX())+","+Integer.toString(gal.getMaxY())+")");
                        } 
                        
                        gal.updateGhostAnimation();
                    } catch (IOException ex) {
                        Logger.getLogger(AnimationLabel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i=(++i)%100;moveCtr--;
                } catch (InterruptedException ex) {
                    Logger.getLogger(AnimationLabel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }